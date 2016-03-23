package pacoteASerDefinido;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ${mavenForm.packageFor('models.PaginatedList')};

/**
 * It deals with the pagination details
 */
public class PaginatorQueryHelper {

	public PaginatorQueryHelper() {
	}

	/**
	 * Do the pagination logic to compose the query
	 * 
	 * @param query base query to list objetcs... Ex: select u from Users.
	 * @param countQuery query to count number of objects
	 * @param currentPage
	 * @param max max number of elements
	 * @return
	 */
	public <T> PaginatedList list(TypedQuery<T> query,
			TypedQuery<Number> countQuery, int currentPage, int max) {
		List<T> currentList = query.setFirstResult(currentPage * max)
				.setMaxResults(max).getResultList();
		Number count = countQuery.getSingleResult();
		return new PaginatedList(currentList, count);
	}

	/**
	 * 
	 * @param manager {@link EntityManager} to create the query
	 * @param klass klass to guess the list query and count query
	 * @param currentPage 
	 * @param max max number of elements
	 * @return
	 */
	public <T> PaginatedList list(EntityManager manager, Class<T> klass,
			int currentPage, int max) {
		
		if(!klass.isAnnotationPresent(Entity.class)){
			throw new IllegalArgumentException("Your entity is not annotated with @Entity");
		}
		
		TypedQuery<T> listQuery = manager.createQuery(
				"select o from " + klass.getSimpleName() + " o", klass);

		TypedQuery<Number> countQuery = manager.createQuery(
				"select count(1) from " + klass.getSimpleName() + " o",
				Number.class);

		return list(listQuery, countQuery, currentPage, max);
	}
}
