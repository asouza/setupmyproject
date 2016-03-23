package com.setupmyproject.models;

import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.setupmyproject.components.TokenGenerator;

@Entity
public class RequestedSetup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true, length = 1000)
	private String generatedToken;
	@Lob
	private String bson;
	@Column(nullable=false)
	private int downloadCounter;

	/**
	 * 
	 * @param generatedToken token associado ao pedido
	 * @param bson
	 */
	public RequestedSetup(String bson) {
		super();
		this.generatedToken = new TokenGenerator().generate();
		this.bson = bson;
	}
	
	
	@Deprecated
	public RequestedSetup() {}

	public CommandGenerators getCommandGenerators() {
		return new SetupState(bson).getCommandGenerators();
	}

	public String getGeneratedToken() {
		return generatedToken;
	}


	/**
	 * 
	 * @param associatedOperation é um experimento. Essa função representa o código que deve rodar após mexermos no objeto
	 */
	public void incrementDownload(Function<RequestedSetup, Void> associatedOperation) {
		this.downloadCounter++;
		associatedOperation.apply(this);
	}


	public Integer getId() {
		return id;
	}
	
	public int getDownloadCounter() {
		return downloadCounter;
	}
	
	public String getBsonEncrypted() {
		return bson;
	}

}
