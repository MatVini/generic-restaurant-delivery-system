package com.br.matvcirino.genericRestaurantDeliverySystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.matvcirino.genericRestaurantDeliverySystem.controller.JsonControleUsuario;
import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Usuario;
import com.br.matvcirino.genericRestaurantDeliverySystem.jwt.JwtTokenUtil;
import com.br.matvcirino.genericRestaurantDeliverySystem.repository.RepositorioUsuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Acesso", description = "API para acesso à aplicação")
public class AuthApi {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private RepositorioUsuario repositorio;
	
	@Autowired
	JwtTokenUtil jwtUtil;
	
	@Operation(summary = "Faz login de um usuário cadastrado", description = "Requer nome de usuário (username) e senha (password). Devolve um token necessário para autenticação com as outras APIs.", tags = {"Acesso"})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
			Usuario usuario = (Usuario) authentication.getPrincipal();
			
			String token = jwtUtil.generateAccessToken(usuario);
			AuthResponse response = new AuthResponse(usuario.getUsername(), token);
			
			return ResponseEntity.ok(response);
			
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@Operation(summary = "Cadastra um usuário no sistema", description = "Requer nome de usuário (username) e senha (password).", tags = {"Acesso"})
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody JsonControleUsuario json) {
		String username = json.getUsername();
		String password = encoder.encode(json.getPassword());
		Usuario novoUsuario = new Usuario(username, password);
		repositorio.save(novoUsuario);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
