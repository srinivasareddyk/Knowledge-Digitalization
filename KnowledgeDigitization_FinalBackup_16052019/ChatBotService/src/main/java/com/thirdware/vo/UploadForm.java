package com.thirdware.vo;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm extends SfhVO{
	
	private MultipartFile files;
	public MultipartFile getFiles() {
		return files;
	}
	
	public void setFiles(MultipartFile files) {
		this.files = files;
	}

}
