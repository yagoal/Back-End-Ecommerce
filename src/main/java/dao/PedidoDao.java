package dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import model.Pedido;
//import br.com.alura.loja.vo.RelatorioDeVendasVo;
import model.Produto;
import model.StatusPedido;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void atualizar(Pedido pedido) {
		this.em.merge(pedido);
	}
	
	public void remover(Pedido pedido) {
		pedido = em.merge(pedido);
		this.em.remove(pedido);
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public Pedido buscarPorId(Long id) {
		return em.find(Pedido.class, id);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	public List<Pedido> buscarPedidosPendentes (StatusPedido statusPedido) {
		String jpql = "SELECT p FROM Pedido as p WHERE p.statusPedido = :statusPedido";
		return em.createQuery(jpql, Pedido.class)
				.setParameter("statusPedido", statusPedido)
				.getResultList();
	}
	
	public void imprimirPedidos (List<Pedido> pedidos) {
		
		System.out.println("Pedidos: ");
		System.out.println("---------------------------------------------------");
		for (Pedido p : pedidos) {
			String aux = p.getStatus().toString().toLowerCase();
			System.out.println("id: " + p.getId() + " | "
					+ "Valor Total: R$ " + p.getValorTotal() 
					+ " | Status: " + aux.substring(0,1).toUpperCase().concat(aux.substring(1))
					+ " |");
			
			System.out.println("---------------------------------------------------");
		}
	}
	
	public Pedido buscarPedidoComCliente(Long id) {
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}
