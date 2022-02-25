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
import br.com.cotiinformatica.requests.LoginPostRequest;
import br.com.cotiinformatica.security.MD5Cryptography;
import br.com.cotiinformatica.security.TokenSecurity;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class LoginController {

	private final String ENDPOINT = "api/login";

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@CrossOrigin
	@ApiOperation("Serviço para autenticar um usuário.")
	@PostMapping(value = ENDPOINT)
	public ResponseEntity<String> autenticarUsuario(@RequestBody LoginPostRequest request) {

		try {
			
		Usuario usuario = usuarioRepository.findByLoginAndSenha(request.getLogin(), 
				MD5Cryptography.encrypt(request.getSenha()));
		
		if(usuario !=null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(TokenSecurity.generateToken(usuario.getLogin()));
			
		}else {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado.");
			
		}
			
		}catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro: "+e.getMessage());
			
		}

	}

}
