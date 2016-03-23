package com.setupmyproject.downloads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.zeroturnaround.zip.ZipUtil;

public class DownloadableProject {

	private byte[] zipBytes;
	private File fileToBePacked;

	public DownloadableProject(String projectPath, File fileToBePacked) {
		this.fileToBePacked = fileToBePacked;
		
		ZipUtil.pack(new File(projectPath), fileToBePacked);			
		try {
			this.zipBytes = IOUtils.toByteArray(new FileInputStream(fileToBePacked));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	public HttpEntity<byte[]> toHttpEnty() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment; filename=\""+fileToBePacked.getName()+"\"");
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(zipBytes,httpHeaders);
		return entity;
	}

}
