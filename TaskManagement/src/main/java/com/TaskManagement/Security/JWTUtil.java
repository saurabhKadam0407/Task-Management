package com.TaskManagement.Security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.TaskManagement.Entity.UserAuth;
import com.TaskManagement.Enum.Permission;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private final Key key;
	private final long expireToken=1000L*60*60*12;
	
	public JWTUtil() {
		String secret= System.getenv("JWT_SECRET");
		
		if(secret==null || secret.isEmpty()) {
			secret="Replace This With Very Secrecy Key";
		}
		key=Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(UserAuth user) {
		
		Map<String,Object>claims= new HashMap<>();
		claims.put("role", user.getRole().name());
		Set<Permission>permissions=RolePermissionConfig.getRolePermission().get(user.getRole());
		
		List<String> permNames= permissions==null? new ArrayList<>():
			                   permissions.stream().map(Enum::name).collect(Collectors.toList());
		
		claims.put("permissions", permNames);
		
		Date now = new Date();
		Date expire=new Date(now.getTime()+expireToken);
		
		return Jwts.builder().
				setClaims(claims).
				setSubject(user.getUserOfficialEmail()).
				setIssuedAt(now).
				setExpiration(expire).signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException ex) {
			return false;
		}
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	public String getUserEmail(String token) {
		return getClaims(token).getSubject();
	}
	public String extractToken(String header) {
		if(header !=null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		
		return null;
		
	}
	
	

}

