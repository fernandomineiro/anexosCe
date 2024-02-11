package moduloAnexos.service.validacoes;

import moduloAnexos.excecoes.MsgGenericaParametrizadaEnum;
import moduloAnexos.excecoes.MsgGenericaParametrizadaException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ValidarArquivoAgencia {

    public static void validarTotalArquivo(int tamanhoArquivos, int limiteMegabytes) {
        int tamanhoArquivosMaximo=1024*1024*limiteMegabytes;
        if(tamanhoArquivos>tamanhoArquivosMaximo) {
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.TAMANHO_ARQUIVO_INVALIDO).setStatusCode(422);
        }
    }

    public static void validarExtensaoArquivo(String nomeArquivo,byte[] arquivo, List<String> extensoesInvalidas) {
        int indice=nomeArquivo.lastIndexOf(".");
        String extensaoArquivo=nomeArquivo.substring(indice+1, nomeArquivo.length());
        if(extensaoArquivo.equalsIgnoreCase("zip"))
            abrirAquivoZip(arquivo);
        if(extensoesInvalidas.stream().anyMatch(e->e.equalsIgnoreCase(extensaoArquivo)))
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.TIPO_ARQUIVO_INVALIDO).setStatusCode(422);
    }

    public static List<String> buscarExtensoesInvalidas() {
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

    public static List<String> buscarExtensoesInvalidasFoto() {
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
        listExtensao.add("txt");
        listExtensao.add("pdf");
        listExtensao.add("ppt");
        listExtensao.add("pptx");
        listExtensao.add("doc");
        listExtensao.add("docx");
        listExtensao.add("cdr");
        listExtensao.add("oxt");
        listExtensao.add("cmd");
		listExtensao.add("ws");
		listExtensao.add("scr");
		listExtensao.add("vbs");
		
        return listExtensao;
    }



    public static void validarTamanhoArquivo(int tamanhoArquivo, int tamanhoMaximoArquivo) {
        int tamanhoArquivoMaximo=1024*tamanhoMaximoArquivo;
        if(tamanhoArquivo>tamanhoArquivoMaximo) {
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.ARQUIVO_MUITO_GRANDE).setStatusCode(422);
        }

    }

    private static void abrirAquivoZip(byte[] arquivo) {
        try( ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(arquivo))){
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                int indice=entry.getName().lastIndexOf(".");
                if(indice!=-1) {
                    String extensaoArquivo=entry.getName().substring(indice+1, entry.getName().length());
                    if(buscarExtensoesInvalidas().stream().anyMatch(e->e.equalsIgnoreCase(extensaoArquivo))) {
                        throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.TIPO_ARQUIVO_INVALIDO).setStatusCode(422);
                    }
                }
            }

        } catch (IOException e) {
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.MENSAGEM_ERRO_VALIDACAO_SS).setStatusCode(400);
        }
    }
}
