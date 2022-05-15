package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.JPAUtil;
import dao.ProdutoDao;
import model.Categoria;
import model.Produto;

public class Main {

	public static void main(String[] args) {
		
		
//		Produto produto = new Produto("Short", "Azul", 24.90, 100, Categoria.FEMININO);
		
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDao dao = new ProdutoDao(em);
		
//		dao.cadastrarProduto(produto);
		
		for (Produto p: dao.listarProdutos()) {
			System.out.println(p.getNome()+ " " + p.getDescricao());
		}
		
	}

}