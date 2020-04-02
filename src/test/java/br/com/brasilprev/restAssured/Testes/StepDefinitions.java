package br.com.brasilprev.restAssured.Testes;

import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.hamcrest.Matchers;

import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;

import br.com.brasilprev.dto.PessoaDto;
import br.com.brasilprev.helpers.FileHelper;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class StepDefinitions {

	private Response response;
	private ValidatableResponse json;
	private RequestSpecification request;

	private String pessoa = "";
	private JsonPath path = null;

	public StepDefinitions() {
		baseURI = "http://localhost:8080/pessoas/";
	}

	@Dado("^que eu fiz a leitura do meu arquivo json com o nome \"([^\"]*)\"$")
	public void que_eu_fiz_a_leitura_do_meu_arquivo_json_com_o_nome(String nomeArquivo) throws Throwable {
		pessoa = new FileHelper().lerArquivo(nomeArquivo);
	}

	@Quando("^faco a requisicao para criar o usuario$")
	public void faco_a_requisicao_para_criar_o_usuario() throws Throwable {
		response = given().contentType("application/json;charset=UTF-8").body(pessoa).when().post("/");
	}

	@Entao("^o codigo de retorno deve ser (\\d+)$")
	public void o_codigo_de_retorno_deve_ser(int codigo) throws Throwable {
		response.then().statusCode(codigo);
	}

	@E("^verifico o body da reposta de criacao de usuario$")
	public void verifico_o_body_da_reposta_de_criacao() throws Throwable {
		path = response.andReturn().jsonPath();
		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);

		String nome = path.getString("nome");
		String cpf = path.getString("cpf");
		ArrayList<Object> telefones = path.get("telefones");
		ArrayList<Object> enderecos = path.get("enderecos");
		assertEquals(pessoaDto.getNome(), nome);
		assertEquals(pessoaDto.getCpf(), cpf);
		assertEquals(telefones.size(), pessoaDto.getTelefone().size());
		assertEquals(enderecos.size(), pessoaDto.getEnderecos().size());
	}

	@Entao("^verifico o body da reposta de criacao de usuario com cpf ja registrado$")
	public void verifico_o_body_da_reposta_de_criacao_de_usuario_com_cpf_ja_registrado() throws Throwable {
		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);
		response.then().assertThat().body("erro",
				equalTo("Já existe pessoa cadastrada com o CPF " + pessoaDto.getCpf() + ""));
	}

	@Entao("^verifico o body da reposta de criacao de usuario um telefone ja registrado$")
	public void verifico_o_body_da_reposta_de_criacao_de_usuario_um_telefone_ja_registrado() throws Throwable {

		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);
		response.then().assertThat().body("erro", equalTo("Já existe pessoa cadastrada com o Telefone ("
				+ pessoaDto.getTelefone().get(0).getDDD() + ")" + pessoaDto.getTelefone().get(0).getNumeroTelefone()));

	}

	@Quando("^faco a requisicao para consultar um usuario$")
	public void faco_a_requisicao_para_consultar_um_usuario() throws Throwable {
		Gson gson = new Gson();
		PessoaDto pessoaArquivo = gson.fromJson(pessoa, PessoaDto.class);
		response = given().when().get("/" + pessoaArquivo.getTelefone().get(0).getDDD() + "/"
				+ pessoaArquivo.getTelefone().get(0).getNumeroTelefone());

	}

	@Entao("^verifico o body da reposta da consulta de um usuario$")
	public void verifico_o_body_da_reposta_da_consulta_de_um_usu_rio() throws Throwable {
		path = response.andReturn().jsonPath();
		Gson gson = new Gson();
		PessoaDto pessoaArquivo = gson.fromJson(pessoa, PessoaDto.class);
		PessoaDto pessoaRetorno = gson.fromJson(path.prettify(), PessoaDto.class);
		assertEquals(pessoaArquivo.getNome(), pessoaRetorno.getNome());
		assertEquals(pessoaArquivo.getCpf(), pessoaRetorno.getCpf());
		assertEquals(pessoaArquivo.getTelefone().size(), pessoaRetorno.getTelefone().size());
		assertEquals(pessoaArquivo.getEnderecos().size(), pessoaRetorno.getEnderecos().size());
	}

	@Quando("^faco a requisicao para consultar um usuario com telefone errado$")
	public void faco_a_requisicao_para_consultar_um_usuario_com_telefone_errado() throws Throwable {
		response = given().contentType("application/json;charset=UTF-8").when().get("/11/985388881");
	}

	@Entao("^verifico o body da reposta da consulta de um usuario com telefone errado$")
	public void verifico_o_body_da_reposta_da_consulta_de_um_usuario_com_telefone_errado() throws Throwable {
		response.then().assertThat().body("erro", equalTo("Não existe pessoa com o telefone (11)985388881"));
	}

	@Quando("^faco a requisicao para filtrar um usuario$")
	public void faco_a_requisicao_para_filtrar_um_usuario() throws Throwable {
		response = given().contentType("application/json;charset=UTF-8").body(pessoa).when().post("filtrar");
	}

	@Entao("^verifico o body da reposta da consulta com filtro de um usuario$")
	public void verifico_o_body_da_reposta_da_consulta_com_filtro_de_um_usuario() throws Throwable {
		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);
		path = response.andReturn().jsonPath();
		String nome = path.getString("nome");
		String cpf = path.getString("cpf");
		ArrayList<Object> telefones = path.get("telefones");
		ArrayList<Object> enderecos = path.get("enderecos");
		assertEquals("[" + pessoaDto.getNome() + "]", nome);
		assertEquals("[" + pessoaDto.getCpf() + "]", cpf);
		assertEquals(telefones.size(), pessoaDto.getTelefone().size());
		assertEquals(enderecos.size(), pessoaDto.getEnderecos().size());
	}

	@Entao("^verifico o body da reposta da consulta com filtro errado$")
	public void verifico_o_body_da_reposta_da_consulta_com_filtro_errado() throws Throwable {
		response.then().assertThat().body("", Matchers.hasSize(0));
	}
}
