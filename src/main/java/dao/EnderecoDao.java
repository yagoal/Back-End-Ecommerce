package dao;

import javax.persistence.EntityManager;

import model.Endereco;

public class EnderecoDao {
	
	private EntityManager em;

	public EnderecoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Endereco endereco) {
		this.em.persist(endereco);
	}
	
	public Endereco buscarPorId(Long id) {
		return em.find(Endereco.class, id);
	}
}
