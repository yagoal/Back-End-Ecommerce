package dao;

import javax.persistence.EntityManager;

import model.Cliente;

public class ClienteDao {

	private EntityManager em;

	public ClienteDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
	}
	
	public Cliente buscarPorId(String id) {
		return em.find(Cliente.class, id);
	}

}
