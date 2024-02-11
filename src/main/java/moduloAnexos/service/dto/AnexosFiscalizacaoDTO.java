    package moduloAnexos.service.dto;

	import java.time.LocalDateTime;

	import com.fasterxml.jackson.annotation.JsonIgnore;

	import moduloAnexos.util.ConvertObjectToJsonCesan;
	import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

	public class AnexosFiscalizacaoDTO {

		private Long id;
		private Integer idFiscalizacoesSS;

		@JsonCesanNotSerializable
		private LocalDateTime dataHoraInclusao;
		private Integer cdAtendimento;
		private String refAtendimento;
		private Short seqSS;
		private String descricao;
		private String usuario;
		private String nomeArquivo;
		@JsonIgnore
		private Integer tamanhoArquivo;
		@JsonIgnore
		private byte[] arquivo;
		@JsonCesanNotSerializable
		private Integer totalArquivo;
		private String data;
		private String acessoRestrito;
		@JsonCesanNotSerializable
		private boolean anexoAntigo;
		
		
	    public AnexosFiscalizacaoDTO() {
			
		}
		public AnexosFiscalizacaoDTO(Integer tamanhoArquivo) {
			this.tamanhoArquivo = tamanhoArquivo;
		}
		
		

		public AnexosFiscalizacaoDTO(Long id, Integer idFiscalizacoesSS, LocalDateTime dataHoraInclusao,Integer cdAtendimento,Integer refAtendimento,Short seqSS, String descricao, String usuario, String nomeArquivo, Integer tamanhoArquivo,String acessoRestrito) {
			this.id = id;
			this.idFiscalizacoesSS = idFiscalizacoesSS;
			this.dataHoraInclusao = dataHoraInclusao;
			this.cdAtendimento=cdAtendimento;
			this.refAtendimento=refAtendimento.toString();
			this.seqSS=seqSS;
			this.descricao = descricao;
			this.usuario = usuario;
			this.nomeArquivo = nomeArquivo;
			this.tamanhoArquivo = tamanhoArquivo;
			this.acessoRestrito = acessoRestrito;
		}
		
		public AnexosFiscalizacaoDTO(Long id, Integer idFiscalizacoesSS, LocalDateTime dataHoraInclusao, String descricao, String usuario, String nomeArquivo,String acessoRestrito) {
			this.id = id;
			this.idFiscalizacoesSS = idFiscalizacoesSS;
			this.dataHoraInclusao = dataHoraInclusao;
			this.descricao = descricao;
			this.usuario = usuario;
			this.nomeArquivo = nomeArquivo;
			this.acessoRestrito = acessoRestrito;
		}
		public Integer getIdFiscalizacoesSS() {
			return idFiscalizacoesSS;
		}
		public void setIdFiscalizacoesSS(Integer idFiscalizacoesSS) {
			this.idFiscalizacoesSS = idFiscalizacoesSS;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public LocalDateTime getDataHoraInclusao() {
			return dataHoraInclusao;
		}
		public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
			this.dataHoraInclusao = dataHoraInclusao;
		}
		public Integer getCdAtendimento() {
			return cdAtendimento;
		}
		public void setCdAtendimento(Integer cdAtendimento) {
			this.cdAtendimento = cdAtendimento;
		}
		public String getRefAtendimento() {
			return refAtendimento;
		}
		public void setRefAtendimento(String refAtendimento) {
			this.refAtendimento = refAtendimento;
		}
		public Short getSeqSS() {
			return seqSS;
		}
		public void setSeqSS(Short seqSS) {
			this.seqSS = seqSS;
		}
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getNomeArquivo() {
			return nomeArquivo;
		}
		public void setNomeArquivo(String nomeArquivo) {
			this.nomeArquivo = nomeArquivo;
		}
		public Integer getTamanhoArquivo() {
			return tamanhoArquivo;
		}
		public void setTamanhoArquivo(Integer tamanhoArquivo) {
			this.tamanhoArquivo = tamanhoArquivo;
		}
		public Integer getTotalArquivo() {
			return totalArquivo;
		}
		public void setTotalArquivo(Integer totalArquivo) {
			this.totalArquivo = totalArquivo;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getAcessoRestrito() {
			return acessoRestrito;
		}
		public void setAcessoRestrito(String acessoRestrito) {
			this.acessoRestrito = acessoRestrito;
		}
		
		public boolean isAnexoAntigo() {
			return anexoAntigo;
		}
		public void setAnexoAntigo(boolean anexoAntigo) {
			this.anexoAntigo = anexoAntigo;
		}
		public byte[] getArquivo() {
			return arquivo;
		}
		public void setArquivo(byte[] arquivo) {
			this.arquivo = arquivo;
		}
		public String toJson() {

			return ConvertObjectToJsonCesan.execute(this);
		}
		
	}
