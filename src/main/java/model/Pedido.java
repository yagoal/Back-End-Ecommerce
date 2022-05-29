package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private LocalDate data = LocalDate.now();
	
	@Enumerated(EnumType.STRING)
	private StatusPedido statusPedido = StatusPedido.PENDENTE;
	
	@Enumerated(EnumType.STRING)
	private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	public Pedido() {
	}

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void pagarPedido () throws InterruptedException {
		System.out.println("Aguarde um momento para finalizarmos o pagamento");
		Thread.sleep(5000);
		this.statusPagamento = StatusPagamento.PAGO;
		this.statusPedido = StatusPedido.PAGO;
		System.out.println("Pagamento no valor de R$" + this.valorTotal + " Realizado com Sucesso!");
		System.out.println("Em breve você receberá seu pedido no seguinte endereço: " + this.enderecoPedido());
	}
	
	public void cancelarPedido() {
		
		if (this.statusPagamento == StatusPagamento.PAGO) {
			System.out.println("Seus pedido escolhido para cancelar encontra-se como pago, em até 8 dias úteis o valor será reembolsado via Pix ou Estornado no seu cartão de crédito");
		}
		
		this.statusPagamento = StatusPagamento.CANCELADO;
		this.statusPedido = StatusPedido.CANCELADO;
		System.out.println("Pedido cancelado com sucesso");
		
	}
	
	public String enderecoPedido () {
		return cliente.getEndereco().getLogradouro() + 
				", " + cliente.getEndereco().getNumero() + 
				", " + cliente.getEndereco().getBairro() + 
				", " + cliente.getEndereco().getCidade() + 
				", " + cliente.getEndereco().getEstado();
	}
	
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.getItens().add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getData() {
		return data;
	}
	
	public StatusPedido getStatus() {
		return statusPedido;
	}

	public void setStatus(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}
	
	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}



}
