package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.IUsuarioRepository;
import br.com.cotiinformatica.requests.AccountPostRequest;
import br.com.cotiinformatica.security.MD5Cryptography;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class AccountController {

	private final String ENDPOINT = "/api/account";
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@CrossOrigin
	@ApiOperation("Serviço para cadastro de conta de usuário.")
	@PostMapping(value = ENDPOINT)
	public ResponseEntity<String> cadastroUsuario(@RequestBody AccountPostRequest request){
		
		try {
			
			if(usuarioRepository.findByLogin(request.getLogin())!=null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Login já existente. Por favor tente outro.");
				
			}
			
			
			Usuario usuario = new Usuario();
			
			
			usuario.setNome(request.getNome());
			usuario.setLogin(request.getLogin());
			usuario.setSenha(MD5Cryptography.encrypt(request.getSenha()));
			
			usuarioRepository.save(usuario);
			
			return ResponseEntity.status(HttpStatus.OK)
					.body("Conta de usuário cadastrada com sucesso.");
			
		}catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro: "+e.getMessage());
			
		}
		
	}
	
}
