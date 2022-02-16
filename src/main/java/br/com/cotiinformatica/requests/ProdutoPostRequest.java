package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoPostRequest {
	
	private String nome;
	private Double preco;
	private Integer quantidade;
	private String descricao;
	
}
