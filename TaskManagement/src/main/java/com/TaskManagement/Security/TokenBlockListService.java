package com.TaskManagement.Security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlockListService {
	
	private final Set<String>blockLstTokens= ConcurrentHashMap.newKeySet();
	
	public void blockListToken(String token) {
		blockLstTokens.add(token);
	}
	public boolean isBlock(String token) {
		return blockLstTokens.contains(token);
	}

}