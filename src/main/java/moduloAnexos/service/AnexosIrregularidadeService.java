package moduloAnexos.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AnexosIrregularidadeService {

	public void salvar(List<MultipartFile> anexosIrregularidade, String token);
}
