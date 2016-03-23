package com.setupmyproject.daos;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.setupmyproject.models.RequestedSetup;

@Repository
public class RequestedSetupDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 
	 * @param requestedSetupToken token associado a geração do setup
	 * @return setup solicitado. Procura pelo setup solicitado
	 */
	public RequestedSetup searchByToken(String requestedSetupToken) {
		String hql = "select rs from RequestedSetup rs where rs.generatedToken = :token";
		RequestedSetup requestedSetup = entityManager
				.createQuery(hql, RequestedSetup.class)
				.setParameter("token", requestedSetupToken).getSingleResult();
		return requestedSetup;
	}

	public void save(RequestedSetup newRequestedSetup) {
		entityManager.persist(newRequestedSetup);
	}

	/**
	 * 
	 * @param generatedToken token associado a geração do setup
	 * @return
	 */
	public Optional<RequestedSetup> tryToFindByToken(String generatedToken) {
		try{
			return Optional.of(searchByToken(generatedToken));
		} catch(NoResultException noResultException){
			return Optional.empty();
		}
	}

	/**
	 * Aqui poderia ser usado o lance da interface para que o parâmetro ficasse mais expressivo, só que eu não vi necessidade.
	 * @param id do {@link RequestedSetup}
	 * @param newNumber novo numero de downloads
	 */
	public void updateDownloads(Integer id,int newNumber) {
		Query query = entityManager.createQuery("update RequestedSetup set downloadCounter=:newNumber where id=:id");
		query.setParameter("newNumber",newNumber);
		query.setParameter("id", id);
		query.executeUpdate();
	}

}
