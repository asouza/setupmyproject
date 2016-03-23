package com.setupmyproject.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.setupmyproject.payment.Payment;

@Repository
public class PaymentDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 
	 * @param transactionCode
	 *            codigo gerado pela transação com o Paypal ou outro provider :P
	 * @return Talvez um pagamento
	 */
	public Optional<Payment> searchByCode(String transactionCode) {
		String hql = "select p from Payment p where p.transactionId = :txCode";
		List<Payment> payments = entityManager.createQuery(hql, Payment.class)
				.setParameter("txCode", transactionCode).getResultList();
		return payments.isEmpty() ? Optional.empty() : Optional.of(payments
				.get(0));
	}

	public void save(Payment payment) {
		entityManager.persist(payment);
	}

	/**
	 * 
	 * @param token para buscar o pagamento
	 * @return possível pagamento encontrado
	 */
	public Optional<Payment> searchByToken(String token) {
		String hql = "select p from Payment p where p.duplicatedToken = :token";
		List<Payment> payments = entityManager.createQuery(hql, Payment.class)
				.setParameter("token", token).getResultList();
		return payments.isEmpty() ? Optional.empty() : Optional.of(payments
				.get(0));
	}

}
