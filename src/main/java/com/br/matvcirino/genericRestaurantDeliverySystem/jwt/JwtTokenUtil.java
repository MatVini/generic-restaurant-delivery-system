package com.br.matvcirino.genericRestaurantDeliverySystem.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@SuppressWarnings("deprecation")
@Component
public class JwtTokenUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 horas
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	public String generateAccessToken(Usuario usuario) {
		return Jwts.builder()
				.setSubject(usuario.getId() + "," + usuario.getUsername())
				.setIssuer("Restaurante Delivery")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			
			return true;
			
		} catch (ExpiredJwtException ex1) {
			LOGGER.error("JWT expirado", ex1);
		} catch (IllegalArgumentException ex2) {
			LOGGER.error("Token é nulo ou está vazio", ex2);
		} catch (MalformedJwtException ex3) {
			LOGGER.error("JWT inválido", ex3);
		} catch (UnsupportedJwtException ex4) {
			LOGGER.error("JWT não suportado", ex4);
		} catch (SignatureException ex5) {
			LOGGER.error("Validação de assinatura falhou", ex5);
		}
		
		return false;
	}
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
}
