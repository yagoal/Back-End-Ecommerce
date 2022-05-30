package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Funcionario;

public class FuncionarioDao {

	private EntityManager em;

	public FuncionarioDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Funcionario funcionario) {
		this.em.persist(funcionario);
	}
	
	public void atualizar(Funcionario funcionario) {
		this.em.merge(funcionario);
	}

	public void remover(Funcionario funcionario) {
		funcionario = em.merge(funcionario);
		this.em.remove(funcionario);
	}
	
	public Funcionario buscarPorId(String id) {
		return em.find(Funcionario.class, id);
	}
	
	public List<Funcionario> buscarTodos() {
		String jpql = "SELECT f FROM Produto f";
		return em.createQuery(jpql, Funcionario.class).getResultList();
	}
	
	public List<Funcionario> buscarPorNome(String nome) {
		String jpql = "SELECT f FROM Funcionario f WHERE f.nome = :nome";
		return em.createQuery(jpql, Funcionario.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Funcionario> buscarPorNivelDeAcesso(Integer nivelDeAcesso) {
		String jpql = "SELECT p FROM Funcionario p WHERE p.nivelDeAcesso = :cidade";
		return em.createQuery(jpql, Funcionario.class)
				.setParameter("nivelDeAcesso", nivelDeAcesso)
				.getResultList();
	}
	
}
