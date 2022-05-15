package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Produto;

public class ProdutoDao {
	
	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void cadastrarProduto (Produto produto) {
		em.getTransaction().begin();
		em.persist(produto);
		em.getTransaction().commit();
		em.close();
	}
	
	public void removerProduto (Produto produto) {
		em.getTransaction().begin();
		em.remove(produto);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Produto> listarProdutos () {
		em.getTransaction().begin();
		List<Produto> lista = em.createQuery("From Produto").getResultList();
		em.getTransaction().commit();
		em.close();
		return lista;
	}
	
}
