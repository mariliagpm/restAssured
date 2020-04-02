package br.com.brasilprev.restAssured.Testes;

import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;

import br.com.brasilprev.dto.PessoaDto;
import br.com.brasilprev.helpers.FileHelper;

public class TestesAPI {

	public TestesAPI() {
		baseURI = "http://localhost:8080/pessoas/";
	}

	@Test
	/* Chama o serviço pelo metodo POST */
	public void testCriarUsuario() { //Testando a criação do usuário 
		String pessoa = new FileHelper().lerArquivo("pessoa.json");
		JsonPath path = given().contentType("application/json;;charset=UTF-8").body(pessoa).expect()
                .statusCode(201).when().post("/").andReturn().jsonPath();
		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);
	
		
		String nome =   path.getString("nome");
		String cpf  =   path.getString("cpf");
		ArrayList<Object> telefones  =   path.get("telefones");
		ArrayList<Object> enderecos   =   path.get("enderecos");
		assertEquals("["+pessoaDto.getNome()+"]", nome);
		assertEquals("["+pessoaDto.getCpf()+"]", cpf);
		assertEquals(telefones.size(),pessoaDto.getTelefone().size());
		assertEquals(enderecos.size(),pessoaDto.getEnderecos().size());
	}
	
	

	@Test
	/* Chama o serviço pelo metodo POST */
	public void testCriarUsuarioCpfRepetido() { //Testando a criação do usuário passando um cpf já cadastrado na base
		String pessoa =new FileHelper().lerArquivo("pessoa.json");
		given().contentType("application/json;;charset=UTF-8").body(pessoa).when().post("/").then().statusCode(400)
				.assertThat().body("erro", equalTo("Já existe pessoa cadastrada com o CPF 37442789803"));
	}

	@Test
	/* Chama o serviço pelo metodo POST */
	//Testando a criação do usuário passando um telefone já cadastrado na base		
	public void testCriarUsuarioTelefoneRepetido() {
	
		String pessoa = new FileHelper().lerArquivo("procurarPessoa.json");
		
		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);
			given().contentType("application/json;charset=UTF-8").body(pessoa).when().post("/").then().statusCode(400)
				.assertThat().body("erro", equalTo("Já existe pessoa cadastrada com o Telefone ("+pessoaDto.getTelefone().get(0).getDDD()+")"+pessoaDto.getTelefone().get(0).getNumeroTelefone()));
	}

	@Test
	/* Chama o serviço pelo metodo GET */
	//Consultando usuário , passando ddd e telefone que já são cadastrados na base
	public void testConsultaUsuario() {
	
		String pessoa = new FileHelper().lerArquivo("pessoa.json");
		Gson gson = new Gson();
		PessoaDto pessoaArquivo = gson.fromJson(pessoa, PessoaDto.class);
		JsonPath path =(JsonPath) given().when().get("/"+pessoaArquivo.getTelefone().get(0).getDDD()+"/"+pessoaArquivo.getTelefone().get(0).getNumeroTelefone()).andReturn().jsonPath();
		PessoaDto pessoaRetorno = gson.fromJson(path.prettify(), PessoaDto.class);
		assertEquals(pessoaArquivo.getNome(), pessoaRetorno.getNome());
		assertEquals(pessoaArquivo.getCpf(), pessoaRetorno.getCpf());
		assertEquals(pessoaArquivo.getTelefone().size(),pessoaRetorno.getTelefone().size());
		assertEquals(pessoaArquivo.getEnderecos().size(),pessoaRetorno.getEnderecos().size());
		
	}

	@Test
	/* Chama o serviço pelo metodo GET */
	public void testConsultaTelefoneInexistente() {
		//Consultando usuário , passando ddd e telefone que não são cadastrados na base
		given().contentType("application/json;charset=UTF-8").when().get("/11/985388881").then().assertThat().body("erro",
				equalTo("Não existe pessoa com o telefone (11)985388881")).statusCode(404);

	}

	@Test
	/* Chama o serviço pelo metodo GET */
	public void testFiltrarPessoa() {
	
		  
		String pessoa = new FileHelper().lerArquivo("pessoa.json");
		JsonPath path = (JsonPath) given().contentType("application/json;charset=UTF-8").body(pessoa).expect()
                .statusCode(200).when().post("filtrar")
				.andReturn().jsonPath();
		
		Gson gson = new Gson();
		PessoaDto pessoaDto = gson.fromJson(pessoa, PessoaDto.class);
	
		
		String nome =   path.getString("nome");
		String cpf  =   path.getString("cpf");
		ArrayList<Object> telefones  =   path.get("telefones");
		ArrayList<Object> enderecos   =   path.get("enderecos");
		assertEquals("["+pessoaDto.getNome()+"]", nome);
		assertEquals("["+pessoaDto.getCpf()+"]", cpf);
		assertEquals(telefones.size(),pessoaDto.getTelefone().size());
		assertEquals(enderecos.size(),pessoaDto.getEnderecos().size());
		
	}

	@Test
	/* Chama o serviço pelo metodo GET */
	public void testFiltrarPessoaFiltroErrado() {
		
		String pessoa = new FileHelper().lerArquivo("pessoaError.json");
		given().contentType("application/json;charset=UTF-8").body(pessoa).when().post("filtrar").then()
				.contentType("application/json").statusCode(200).assertThat().body("", Matchers.hasSize(0));

	}

}
