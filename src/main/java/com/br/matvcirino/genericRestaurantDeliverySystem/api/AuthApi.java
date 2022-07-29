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

import com.br.matvcirino.genericRestaurantDeliverySystem.JsonControleUsuario;
import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Usuario;
import com.br.matvcirino.genericRestaurantDeliverySystem.jwt.JwtTokenUtil;
import com.br.matvcirino.genericRestaurantDeliverySystem.repository.RepositorioUsuario;

@RestController
public class AuthApi {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private RepositorioUsuario repositorio;
	
	@Autowired
	JwtTokenUtil jwtUtil;
	
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
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody JsonControleUsuario json) {
		String username = json.getUsername();
		String password = encoder.encode(json.getPassword());
		Usuario novoUsuario = new Usuario(username, password);
		repositorio.save(novoUsuario);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
