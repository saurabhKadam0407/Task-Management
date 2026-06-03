package com.TaskManagement.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private TokenBlockListService tokenBlockService;
	
	@Autowired
	private CustomerUserDetailsService customerDetails;
	
	public void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
		
		String header= request.getHeader("Authorization");
		String token= jwtUtil.extractToken(header);
		
//		String token= null;
//		
		if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			
			token=header.substring(7);
		}
		if(token !=null) {
			if(tokenBlockService.isBlock(token)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Token is logged out");
			}
			
		}
		
     if(token !=null && jwtUtil.validateToken(token)) {
			
			String userEmail=jwtUtil.getUserEmail(token);
			
			try {
			 UserDetails userDetails = customerDetails.loadUserEmail(userEmail);
			
			UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}catch(Exception e) {
			SecurityContextHolder.clearContext();
			}
		}
		
		filterChain.doFilter(request, response);
		
			
	}	
		
		
		
	


}

