package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.IProdutoRepository;
import br.com.cotiinformatica.requests.ProdutoPostRequest;
import br.com.cotiinformatica.requests.ProdutoPutRequest;
import br.com.cotiinformatica.responses.ProdutoGetResponse;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class ProdutoController {

	@Autowired
	private IProdutoRepository produtoRepository;
	
	private static final String ENDPOINT = "/api/produtos";
	
	@CrossOrigin
	@ApiOperation("Serviço para cadastro de produto.")
	@PostMapping(value = ENDPOINT)
	public ResponseEntity<String> cadastrarProduto(@RequestBody ProdutoPostRequest request) {
		
		try {
			
		Produto produto = new Produto();
		
		produto.setNome(request.getNome());
		produto.setPreco(request.getPreco());
		produto.setQuantidade(request.getQuantidade());
		produto.setDescricao(request.getDescricao());
		
		produtoRepository.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto cadastrado com sucesso!");
		
		}catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro: "+e.getMessage());
			
		}
	}
	
	@CrossOrigin
	@ApiOperation("Serviço para atualização dos dados de um produto.")
	@PutMapping(value = ENDPOINT)
	public ResponseEntity<String> atualizarProduto(@RequestBody ProdutoPutRequest request) {
		
		try {
			
			Produto found = produtoRepository.findById(request.getIdProduto()).orElse(null);
			
			if(found!=null) {
				
				found.setNome(request.getNome());
				found.setPreco(request.getPreco());
				found.setQuantidade(request.getQuantidade());
				found.setDescricao(request.getDescricao());
				
				produtoRepository.save(found);
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.body("Produto atualizado com sucesso!");
				
			}else {
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Produto não encontrado! Por favor verifique.");
				
			}
			
		}catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro: "+e.getMessage());
			
		}
	}
	
	@CrossOrigin
	@ApiOperation("Serviço para exclusão de um produto.")
	@DeleteMapping(value = ENDPOINT+"/{idProduto}")
	public ResponseEntity<String> deletarProduto(@PathVariable Integer idProduto) {
		
		try {

			Produto found = produtoRepository.findById(idProduto).orElse(null);
			
			if(found!=null) {
			
			produtoRepository.deleteById(idProduto);
		
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Produto deletado com sucesso!");
			
			}else {
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Não foi possível deletar o produto, pois o produto com o id: "+
								idProduto+" não foi encontrado!");
				
			}
		
		}catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro: "+e.getMessage());
			
		}
	}
	
	@CrossOrigin
	@ApiOperation("Serviço para consultar todos os produtos da aplicação.")
	@GetMapping(value = ENDPOINT)
	public ResponseEntity<List<ProdutoGetResponse>> buscarProdutos() {
		
		List<ProdutoGetResponse> response = new ArrayList<ProdutoGetResponse>();
		
		for(Produto produto : produtoRepository.findAll()) {
			
			ProdutoGetResponse item = new ProdutoGetResponse();
			
			item.setIdProduto(produto.getIdProduto());
			item.setNome(produto.getNome());
			item.setPreco(produto.getPreco());
			item.setQuantidade(produto.getQuantidade());
			item.setDescricao(produto.getDescricao());
			item.setTotal(produto.getQuantidade()*produto.getPreco());
			
			response.add(item);
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
	
	@CrossOrigin
	@ApiOperation("Serviço para consultar um produto pelo ID.")
	@GetMapping(value = ENDPOINT+"/{idProduto}")
	public ResponseEntity<ProdutoGetResponse> buscarProdutoPorId(@PathVariable Integer idProduto) {
		
		Produto produto = produtoRepository.findById(idProduto).orElse(null);
		
		try {
		
		if(produto!=null) {
			
			ProdutoGetResponse response = new ProdutoGetResponse();
			
			response.setIdProduto(produto.getIdProduto());
			response.setNome(produto.getNome());
			response.setPreco(produto.getPreco());
			response.setQuantidade(produto.getQuantidade());
			response.setDescricao(produto.getDescricao());
			response.setTotal(produto.getQuantidade()*produto.getPreco());
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
			
		}else {
			
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(null);
			
		}
		
		}catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
		
	}
}
