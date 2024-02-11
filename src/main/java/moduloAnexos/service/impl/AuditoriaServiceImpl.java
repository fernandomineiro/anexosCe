package moduloAnexos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import moduloAnexos.rabbitmq.produtores.Produtor;
import moduloAnexos.rabbitmq.produtores.mensagens.AuditoriaMsgProdutor;
import moduloAnexos.service.AuditoriaService;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

	@Value("${rabbitmq.direct.produtorAuditoria.nomeExchange}")
	private String exchangeAuditoria;
	@Value("${rabbitmq.direct.produtorAuditoria.rota}")
	private String rotaAuditoria;
	@Autowired
	Produtor produtor;
	
	
	@Override
	public void gerarAuditoria(Long chave, String dadosAntes, String dadosDepois, Long idEntidade, String rotina,
			Long idUsuario) {
		 String dadosAntesJson;
	     String dadosDepoisJson;
		if (!dadosAntes.equals(dadosDepois)) {
			if (dadosDepois.isEmpty()) {
				dadosAntesJson = dadosAntes;
				dadosDepoisJson = dadosDepois;
			} else if (dadosAntes.isEmpty()) {
				dadosAntesJson = dadosAntes;
				dadosDepoisJson="{\"Id\":\""+ chave+"\"}";
			} else {
				List<String> listaDadosAntesDadosDepois = this.filtrarJson(dadosAntes, dadosDepois);
				dadosAntesJson = listaDadosAntesDadosDepois.get(0);
				dadosDepoisJson = listaDadosAntesDadosDepois.get(1);
			}
			
			AuditoriaMsgProdutor auditoriaMsgProdutor = new AuditoriaMsgProdutor(dadosAntesJson, dadosDepoisJson, chave,
					idEntidade, rotina, idUsuario);
			produtor.envioMensagem(exchangeAuditoria, "direct", rotaAuditoria, auditoriaMsgProdutor);
		}
	}

	private List<String> filtrarJson(String dadosAntes, String dadosDepois) {
		dadosAntes = dadosAntes.replaceAll("\\{", "").replaceAll("\\\"}", "");
		dadosDepois = dadosDepois.replaceAll("\\{", "").replaceAll("\\\"}", "");

		String listDadosAntes[] = dadosAntes.split("\",");
		String listDadosDepois[] = dadosDepois.split("\",");

		Map<String, String> mapDadosAntes = new TreeMap<>();
		Map<String, String> mapDadosDepois = new TreeMap<>();

		for (int indice = 0; indice < listDadosAntes.length; indice++) {
			String campo[] = listDadosAntes[indice].split(":");
			mapDadosAntes.put(campo[0], campo[1]);
		}
		for (int indice = 0; indice < listDadosDepois.length; indice++) {
			String campo[] = listDadosDepois[indice].split(":");
			mapDadosDepois.put(campo[0], campo[1]);
		}
		Set<String> listDados = mapDadosAntes.keySet();
		String dadosAntesNovo = "{";
		String dadosDepoisNovo = "{";
		for (String dado : listDados) {

			if (!mapDadosAntes.get(dado).equals(mapDadosDepois.get(dado))) {
				dadosAntesNovo = dadosAntesNovo + dado + ":" + mapDadosAntes.get(dado) + "\",";
				dadosDepoisNovo = dadosDepoisNovo + dado + ":" + mapDadosDepois.get(dado) + "\",";
			}

		}
		List <String> retorno = new ArrayList<String>();
		retorno.add(dadosAntesNovo.substring(0, dadosAntesNovo.length() - 1) + "}");
		retorno.add(dadosDepoisNovo.substring(0, dadosDepoisNovo.length() - 1) + "}");
		return retorno;
	}
}
