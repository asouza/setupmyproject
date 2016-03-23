package com.setupmyproject.forge;

public class ViewSaveConfiguration {
	
	private String webPathToSave;
	private String extension;
	
	

	/**
	 * 
	 * @param webPathToSave caminho dentro da aplicação web para salvar. Ex: WEB-INF/jsp/views/
	 * @param extension jsp, xhtml ou qualquer coisa que vc queira
	 */
	public ViewSaveConfiguration(String webPathToSave, String extension) {
		super();
		this.webPathToSave = webPathToSave;
		this.extension = extension;
	}



	public String pathTo(String fileName) {
		return webPathToSave + fileName + "."+extension;
	}

}
