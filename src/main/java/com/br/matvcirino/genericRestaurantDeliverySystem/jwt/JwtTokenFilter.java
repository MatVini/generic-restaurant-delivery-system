package com.br.matvcirino.genericRestaurantDeliverySystem.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.matvcirino.genericRestaurantDeliverySystem.entity.Usuario;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (!hasAuthorizationHeader(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String accessToken = getAccessToken(request);
		
		if (!jwtUtil.validateAccessToken(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		setAuthenticationContext(accessToken, request);
		filterChain.doFilter(request, response);
	}

	private boolean hasAuthorizationHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		System.out.println("Authorization Header: " + header);
		
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}
	
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		System.out.println("Access Token: " + token);
		return token;
	}

	private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(accessToken);
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, null);
		auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private UserDetails getUserDetails(String accessToken) {
		Usuario userDetails = new Usuario();
		String[] subjectArray = jwtUtil.getSubject(accessToken).split(",");
		
		userDetails.setId(Long.parseLong(subjectArray[0]));
		userDetails.setUsername(subjectArray[1]);
		
		return userDetails;
	}
}
