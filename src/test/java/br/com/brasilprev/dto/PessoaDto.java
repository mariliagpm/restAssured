package br.com.brasilprev.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PessoaDto {
	@SerializedName("pessoa")

	private Long codigo;

	private String nome;

	private String cpf;

	private List<EnderecoDto> enderecos;

	private List<TelefoneDto> telefones;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public void setCpf(String nome) {
		this.cpf = nome;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setEnderecos(List<EnderecoDto> enderecos) {
		this.enderecos = enderecos;
	}

	public List<EnderecoDto> getEnderecos() {
		return enderecos;
	}

	
	public void setTelefone(List<TelefoneDto> telefones) {
		this.telefones = telefones;
	}

	public List<TelefoneDto> getTelefone() {
		return telefones;
	}
	
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	
	

}