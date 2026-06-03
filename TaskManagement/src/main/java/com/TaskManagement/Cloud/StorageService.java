package com.TaskManagement.Cloud;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	public String store(MultipartFile file,String relativeFolder);
	byte[] read(String storagePath);
	void delete(String cloudId);

}

