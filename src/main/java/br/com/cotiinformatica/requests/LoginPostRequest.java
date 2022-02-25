package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPostRequest {

	private String login;
	private String senha;
	
}
