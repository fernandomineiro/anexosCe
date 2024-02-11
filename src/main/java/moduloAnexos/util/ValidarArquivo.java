package moduloAnexos.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import moduloAnexos.excecoes.ExcecaoRegraNegocio;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;

public class ValidarArquivo {

	public static void validarTotalArquivo(int tamanhoArquivos) {
		int tamanhoArquivosMaximo = 1024 * 1024 * 25 * 10;
		if (tamanhoArquivos > tamanhoArquivosMaximo)
			throw new ExcecaoRegraNegocio("Não foi possível adicionar este arquivo. Ultrapassou o limite  250MB");
	}

	public static void validarExtensaoArquivo(String nomeArquivo, byte[] arquivo) {
		int indice = nomeArquivo.lastIndexOf(".");
		String extensaoArquivo = nomeArquivo.substring(indice + 1, nomeArquivo.length());
		if (extensaoArquivo.equalsIgnoreCase("zip"))
			abrirAquivoZip(arquivo);
		if (buscarExtensoesInvalidas().stream().anyMatch(e -> e.equalsIgnoreCase(extensaoArquivo)))
			throw new MsgGenericaPersonalizadaException("Arquivo inválido");
	}

	private static List<String> buscarExtensoesInvalidas() {
		List<String> listExtensao = new ArrayList<>();
		listExtensao.add("sql");
		listExtensao.add("exe");
		listExtensao.add("js");
		listExtensao.add("bin");
		listExtensao.add("sh");
		listExtensao.add("bat");
		listExtensao.add("json");
		listExtensao.add("xml");
		listExtensao.add("rdl");
		listExtensao.add("msi");
		listExtensao.add("cmd");
		listExtensao.add("ws");
		listExtensao.add("scr");
		listExtensao.add("vbs");

		return listExtensao;
	}

	public static void validarTamanhoArquivo(int tamanhoArquivo) {
		int tamanhoArquivoMaximo = 1024 * 25000;
		if (tamanhoArquivo > tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Arquivo muito grande.");
		}

	}


	private static void abrirAquivoZip(byte[] arquivo) {
		try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(arquivo),Charset.forName("Cp437"))) {

			ZipEntry entry = zipIn.getNextEntry();
			while ((entry = zipIn.getNextEntry()) != null) {
				int indice = entry.getName().lastIndexOf(".");
				if (indice != -1) {
					String extensaoArquivo = entry.getName().substring(indice + 1, entry.getName().length());
					if (buscarExtensoesInvalidas().stream().anyMatch(e -> e.equalsIgnoreCase(extensaoArquivo))) {
						throw new MsgGenericaPersonalizadaException("Arquivo inválido");
					}
				}
			}

		} catch (IOException e) {
			throw new MsgGenericaPersonalizadaException("Erro ao salvar arquivo");

		}
	}
}
