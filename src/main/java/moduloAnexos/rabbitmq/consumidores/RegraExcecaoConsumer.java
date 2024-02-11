package moduloAnexos.rabbitmq.consumidores;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;
import moduloAnexos.rabbitmq.EndPoint;
import moduloAnexos.rabbitmq.produtores.mensagens.AcessoGrupoRegraExcecaoMsgProdutor;
import moduloAnexos.repository.RegraUsuarioAnexosRepository;
import moduloAnexos.util.Constants;
import moduloAnexos.util.LogErro;




@Component
@Scope("prototype")
public class RegraExcecaoConsumer implements Runnable, Consumer {

	@Autowired
	private EndPoint endPoint;

	private Channel channel;

	private Gson gson;

	@Autowired
	private RegraUsuarioAnexosRepository regraUsuarioAnexosRepository;

	@Value("${rabbitmq.direct.produtorRegraExcecao.nomeFila}")
	private String nomeFila;
	@Value("${rabbitmq.direct.produtorRegraExcecao.nomeRota}")
	private String nomeRota;
	@Value("${rabbitmq.direct.produtorRegraExcecao.nomeExchange}")
	private String nomeExchange;

	@Autowired
	LogErro logErro;

	public RegraExcecaoConsumer() throws IOException {
		this.gson = new Gson();
	}

	public void criarFila() throws Exception {
		this.channel = endPoint.criarFila(nomeExchange, "fanout", nomeFila, nomeRota, null);
	}

	public void run() {
		try {
			channel.basicConsume(nomeFila, false, this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Chama esse método quando existe uma mensagem não lida na fila.
	@Override
	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body)
			throws IOException {

		try {
			String message = new String(body, "UTF-8");

			AcessoGrupoRegraExcecaoMsgProdutor msg = gson.fromJson(message, AcessoGrupoRegraExcecaoMsgProdutor.class);
			
			for (Long idUsuario : msg.getUsuarios()) {
				
				RegraUsuarioAnexos regra = new RegraUsuarioAnexos(new IdRegraUsuarioAnexos(msg.getIdRegra().intValue(), idUsuario.intValue()));
				
				if (msg.getAcao().equals(Constants.CRUD_CREATE)) {
					regraUsuarioAnexosRepository.save(regra);
				}
				
				if (msg.getAcao().equals(Constants.CRUD_DELETE)) {
					regraUsuarioAnexosRepository.delete(regra);
				}
			}

			channel.basicAck(env.getDeliveryTag(), false);
		} catch (Exception e) {
			logErro.logErro("Erro ao salvar Regra Exceção", e.getMessage());
		}
	}

	@Override
	public void handleConsumeOk(String consumerTag) {

	}

	@Override
	public void handleCancelOk(String consumerTag) {

	}

	@Override
	public void handleCancel(String consumerTag) throws IOException {

	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

	}

	@Override
	public void handleRecoverOk(String consumerTag) {

	}

}
