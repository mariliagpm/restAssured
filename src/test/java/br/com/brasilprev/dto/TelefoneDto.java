package br.com.brasilprev.dto;

public class TelefoneDto {

	public Long codigo;

	public String ddd;

	public String numero;
	
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	
	public void setDDD(String ddd) {
		this.ddd = ddd;
	}

	public String getDDD() {
		return ddd;
	}
	
	
	public void setNumeroTelefone(String numero) {
		this.numero = numero;
	}

	public String getNumeroTelefone() {
		return numero;
	}
}