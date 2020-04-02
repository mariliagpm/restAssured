package br.com.brasilprev.helpers;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class FileHelper {

	public  String lerArquivo(String fileName) {
		String conteudoArquivo = "";
		try {
			URL urlArquivo = this.getClass().getClassLoader().getResource(fileName);  
	       	FileReader arq = new FileReader(urlArquivo.getPath());
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine(); // lê a primeira linha
			conteudoArquivo = conteudoArquivo + linha;
			// a variável "linha" recebe o valor "null" quando o processo
			// de repetição atingir o final do arquivo texto
			while (linha != null) {
				System.out.printf("%s\n", linha);

				linha = lerArq.readLine(); // lê da segunda até a última linha
				if (linha != null)
					conteudoArquivo = conteudoArquivo + linha;
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return conteudoArquivo;
	}
}
