#language: pt
Funcionalidade: Incluindo um usuario

  Esquema do Cenario: Testar a criacao de um usuario
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
    Quando faco a requisicao para criar o usuario
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta de criacao de usuario
    Exemplos: 
      | codigo   |    nomeArquivo		  	|
      |      201 |	pessoa.json	  |

    Esquema do Cenario: Testar a criacao de um usuario com um cpf ja registrado
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
    Quando faco a requisicao para criar o usuario
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta de criacao de usuario com cpf ja registrado
    Exemplos: 
     | codigo   |    nomeArquivo		  	|
     |      400 |	pessoa.json	          |
    
    
    Esquema do Cenario: Testar a criacao de um usuario com um telefone ja registrado
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
   Quando faco a requisicao para criar o usuario
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta de criacao de usuario um telefone ja registrado
    Exemplos: 
      | codigo   |    nomeArquivo					 |
     	|      400 |	procurarPessoa.json    |
     	
     	
      
    Esquema do Cenario: Testar a busca de um usuario pelo ddd + numero de telefone
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
   Quando faco a requisicao para consultar um usuario
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta da consulta de um usuario
    Exemplos: 
      | codigo   |    nomeArquivo					 |
     	|      200 |	pessoa.json    				 |
     	
       
    Esquema do Cenario: Testar a busca de um usuario pelo ddd + numero de telefone errado
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
   Quando faco a requisicao para consultar um usuario com telefone errado
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta da consulta de um usuario com telefone errado
    Exemplos: 
      | codigo   |    nomeArquivo					 |
     	|     404  |	pessoa.json    				 |	
     	
     	
     	       
    Esquema do Cenario: Testar a filtragem de um usuario
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
   Quando faco a requisicao para filtrar um usuario
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta da consulta com filtro de um usuario
    Exemplos: 
      | codigo   |    nomeArquivo					 |
     	|     200  |	pessoa.json    				 |	
     	
     	
     	Esquema do Cenario: Testar a filtragem de um usuario
    Dado que eu fiz a leitura do meu arquivo json com o nome "<nomeArquivo>" 
   Quando faco a requisicao para filtrar um usuario
    Entao o codigo de retorno deve ser <codigo>
    E verifico o body da reposta da consulta com filtro errado
    Exemplos: 
      | codigo   |    nomeArquivo					       |
     	|     200  |	pessoaError.json    				 |	
     	
     	
     	
     	