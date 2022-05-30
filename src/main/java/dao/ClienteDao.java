package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Cliente;
import model.Endereco;

public class ClienteDao {

	private EntityManager em;

	public ClienteDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
		
	}
	public void atualizar(Cliente cliente) {
		this.em.merge(cliente);
	}

	public void remover(Cliente cliente) {
		cliente = em.merge(cliente);
		this.em.remove(cliente);
	}
	
	public Cliente buscarPorId(String id) {
		return em.find(Cliente.class, id);
	}
	
	public List<Cliente> buscarTodos() {
		String jpql = "SELECT c FROM Produto c";
		return em.createQuery(jpql, Cliente.class).getResultList();
	}
	
	public List<Cliente> buscarPorNome(String nome) {
		String jpql = "SELECT c FROM Cliente c WHERE c.nome = :nome";
		return em.createQuery(jpql, Cliente.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Cliente> buscarPorCidade(String cidade) {
		String jpql = "SELECT c FROM Endereco c WHERE c.cidade = :cidade";
		return em.createQuery(jpql, Cliente.class)
				.setParameter("cidade", cidade)
				.getResultList();
	}
	
}
