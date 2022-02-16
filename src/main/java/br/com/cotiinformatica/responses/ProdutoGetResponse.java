package br.com.cotiinformatica.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoGetResponse {
		
	private Integer idProduto;
	private String nome;
	private Double preco;
	private Integer quantidade;
	private String descricao;
	private Double total;
	
}
