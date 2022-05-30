package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Cliente;
import model.Endereco;

public class EnderecoDao {
	
	private EntityManager em;

	public EnderecoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Endereco endereco) {
		this.em.persist(endereco);
	}
	
	public void atualizar(Endereco endereco) {
		this.em.merge(endereco);
	}

	public void remover(Endereco endereco) {
		endereco = em.merge(endereco);
		this.em.remove(endereco);
	}
	
	public Endereco buscarPorId(Long id) {
		return em.find(Endereco.class, id);
	}
	
	public List<Endereco> buscarTodos() {
		String jpql = "SELECT e FROM Produto e";
		return em.createQuery(jpql, Endereco.class).getResultList();
	}
	
	public List<Endereco> buscarPorNome(String nome) {
		String jpql = "SELECT e FROM Cliente e WHERE e.nome = :nome";
		return em.createQuery(jpql, Endereco.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Endereco> buscarPorCidade(String cidade) {
		String jpql = "SELECT e FROM Endereco e WHERE e.cidade = :cidade";
		return em.createQuery(jpql, Endereco.class)
				.setParameter("cidade", cidade)
				.getResultList();
	}
	
	public List<Endereco> buscarPorLogradouro(String logradouro) {
		String jpql = "SELECT e FROM Endereco e WHERE e.logradouro = :logradouro";
		return em.createQuery(jpql, Endereco.class)
				.setParameter("logradouro", logradouro)
				.getResultList();
	}	
}
