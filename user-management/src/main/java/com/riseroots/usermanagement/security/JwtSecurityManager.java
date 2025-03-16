package com.riseroots.usermanagement.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtSecurityManager extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expirationTime;

	private final UserDetailsService userDetailsService;

	@Qualifier("scPasswordEncoder") // Specify the correct bean name
	private final PasswordEncoder passwordEncoder;

	/**
	 * Generate JWT token for a given username.
	 */
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
				.signWith(SignatureAlgorithm.HS256, secretKey) // Replace with your secret key
				.compact();
	}

	/**
	 * Extract username from JWT token.
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	/**
	 * Validate the JWT token.
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * Check if the token is expired.
	 */
	private boolean isTokenExpired(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims.getExpiration().before(new Date());
	}

	/**
	 * Filter logic to validate JWT in incoming requests.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("Control came to jwtSecurityManager.."+ request.getRequestURI());

		// Skip filter for /login and /register routes
		if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/register")) {
			filterChain.doFilter(request, response);
			return; // Skip further processing for these requests
		}

		// Continue with JWT validation for other requests
		String token = getTokenFromRequest(request);
		if (token != null) {
			// your JWT validation logic here
		} else {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing Token");
			return;
		}

		filterChain.doFilter(request, response);
	}

	private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": \"" + message + "\"}");
		response.getWriter().flush();
	}

	/**
	 * Extract JWT token from Authorization header.
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Remove "Bearer " prefix
		}
		return null;
	}
}
