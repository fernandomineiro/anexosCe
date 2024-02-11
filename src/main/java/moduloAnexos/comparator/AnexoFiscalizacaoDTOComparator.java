package moduloAnexos.comparator;
import java.util.Comparator;
import moduloAnexos.service.dto.AnexosFiscalizacaoListDTO;

public class AnexoFiscalizacaoDTOComparator implements Comparator<AnexosFiscalizacaoListDTO> {

	private final String campo;
	private final String ordem;
	
	public AnexoFiscalizacaoDTOComparator(String campo, String ordem) {
		this.campo = campo;
		this.ordem = ordem;
	}
	
	@Override
	public int compare(AnexosFiscalizacaoListDTO o1, AnexosFiscalizacaoListDTO o2) {

		int comp;
		if (campo.equals("")) {
			comp = o2.getDataHoraInclusao().compareTo(o2.getDataHoraInclusao());
		
			return comp;
		}
		if (campo.equals("dataHoraInclusao")) {
			if (ordem.equalsIgnoreCase("desc")) {
				comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				return comp;
			} else {
				comp = o1.getDataHoraInclusao().compareTo(o2.getDataHoraInclusao());

				return comp;
			}

		}
		if (campo.equals("nomeArquivo")) {
			if (ordem.equalsIgnoreCase("desc")) {
				comp = o2.getNomeArquivo().compareTo(o1.getNomeArquivo());
				if (comp == 0) {
					comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				}
				return comp;
			} else {
				comp = o1.getNomeArquivo().compareTo(o2.getNomeArquivo());
				
				if (comp == 0) {
					comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				}

				return comp;
			}

		}
		if (campo.equals("descricao")) {
			if (ordem.equalsIgnoreCase("desc")) {
				comp = o2.getDescricao().compareTo(o1.getDescricao());
				if (comp == 0) {
					comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				}
				return comp;
			} else {
				comp = o1.getDescricao().compareTo(o2.getDescricao());
				
				if (comp == 0) {
					comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				}

				return comp;
			}

		}
		if (campo.equals("usuario")) {
			if (ordem.equalsIgnoreCase("desc")) {
				comp = o2.getUsuario().compareTo(o1.getUsuario());
				if (comp == 0) {
					comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				}
				return comp;
			} else {
				comp = o1.getUsuario().compareTo(o2.getUsuario());
				
				if (comp == 0) {
					comp = o2.getDataHoraInclusao().compareTo(o1.getDataHoraInclusao());
				}

				return comp;
			}

		}
		
		return 0;
	}

}
