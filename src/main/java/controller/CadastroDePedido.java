package controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import dao.CategoriaDao;
import dao.ClienteDao;
import dao.EnderecoDao;
import dao.PedidoDao;
import dao.ProdutoDao;
import model.Categoria;
import model.Cliente;
import model.Endereco;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.StatusPedido;
import dao.JPAUtil;

//import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) throws InterruptedException {

		popularBancoDeDados();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);
		
		
		System.out.println("Entrar como Cliente ou Funcionário? Digite 0 para Funcionário - Qualquer outro número para Cliente");
		
		Scanner leitor = new Scanner(System.in);
		
		int opt = leitor.nextInt();
		
		System.out.println("Digite seu usuário");
		String usuario = leitor.next();
		
		System.out.println("Digite sua senha");
		String senha = leitor.next();
		
		Cliente clienteTeste = clienteDao.buscarPorId(usuario);

		try {
			
			if (clienteTeste.autenticacao(usuario, senha)) {
					
				System.out.println("Usuário: " + usuario + " logado com sucesso");
				
				int contPagamento = 0;
				int contCancelamento = 0;
				
				if (opt != 0) {
					
					while (true) {
						System.out.println("Digite a Opcao Referente ao que deseja fazer");
						System.out.println("1: Para fazer um pedido");
						System.out.println("2: Para pagar um pedido");
						System.out.println("3: Para cancelar um pedido");
						System.out.println("Qualquer outro para sair");
						int optUsuario = leitor.nextInt();
						
						if (optUsuario == 1) {
							
							Pedido pedido = new Pedido(clienteTeste);
							
							System.out.println("Produtos Disponíveis");
							produtoDao.imprimirCatalogo();
							
							System.out.println("Quantos Itens você deseja pedir ?");
							int qtd = leitor.nextInt();
							
							for (int i = 0; i < qtd; i++) {
								
								int numItem = i+1;
								System.out.println("Item  " + 0+numItem + " Digite o id do produto que deseja adicionar");
								long id = leitor.nextLong();
								Produto produto = produtoDao.buscarPorId(id);
								
								System.out.println("Digite a quantidade de " + produto.getNome() + " " + produto.getDescricao() + " que você deseja adicionar ao pedido");
								int qtdProdutos = leitor.nextInt();
								pedido.adicionarItem(new ItemPedido(qtdProdutos, pedido, produto));
								
							}
							
							BigDecimal valor = pedido.getValorTotal();
							System.out.println("Valor Total do pedido: R$ " + valor);
							System.out.println("Deseja pagar o pedido ?");
							System.out.println("1 Para Sim ou qualquer outra tecla para não");
							
							int optPagamento = leitor.nextInt();
							
							if (optPagamento == 1) {
								pedido.pagarPedido();
							}
							
							em.getTransaction().begin();
							pedidoDao.cadastrar(pedido);
							em.getTransaction().commit();
							
							long numPedido = pedido.getId();
							System.out.println("Pedido registrado com o número: " + numPedido);
							
				
						}
						
						else if (optUsuario == 2) {
							
							/* 
							 * Mocks para fins de testar o pagamento dos pedidos, caso queira obter mais testes basta incluir mais mocks 
							 * de pedidos, produtos e/ou clientes ou cadastrar manu
							*/
							
							Produto produto = produtoDao.buscarPorId(1l);
							Produto produto2 = produtoDao.buscarPorId(2l);
							Produto produto3 = produtoDao.buscarPorId(3l);
		
							Pedido pedido = new Pedido(clienteTeste);
							Pedido pedido2 = new Pedido(clienteTeste);
							
							pedido.adicionarItem(new ItemPedido(1, pedido, produto));
							pedido.adicionarItem(new ItemPedido(2, pedido, produto2));
							pedido2.adicionarItem(new ItemPedido(3, pedido2, produto3));
							
							if (contPagamento == 0) {
								em.getTransaction().begin();
								pedidoDao.cadastrar(pedido);
								pedidoDao.cadastrar(pedido2);
								em.getTransaction().commit();
								//controlador para fins de não cadastrar novamente no banco de dados e continuar pagando os pedidos até tornar a lista vazia
								contPagamento ++;
							}
						
							List<Pedido> pedidosPendentes = pedidoDao.buscarPedidosPendentes(StatusPedido.PENDENTE);
							
							if (pedidosPendentes.isEmpty()) {
								System.out.println();
								System.out.println("Você não possui pedidos a serem pagos, por favor faça um pedido!");
								System.out.println();
							} else {
								
								pedidoDao.imprimirPedidos(pedidosPendentes);
								System.out.println("Digite o numero do pedido que deseja pagar");
								long optPagamento = leitor.nextLong();
								
								Pedido pedidoPagamento = pedidoDao.buscarPorId(optPagamento);
								List<ItemPedido> itens = pedidoPagamento.getItens();
								
								System.out.println("O pedido número: " + optPagamento + " tem os seguintes itens:");
								System.out.println("------------------------------------------------------------------");
								for (ItemPedido i : itens) {
									String nome = i.getProduto().getNome();
									String descricao = i.getProduto().getDescricao();
									int qtdItens = i.getQuantidade();
									BigDecimal valorUnitario = i.getPrecoUnitario();
									System.out.println("Quantidade: " + qtdItens + " | Valor Unitário: R$ " + valorUnitario + " | Item: " + nome + " " + descricao );
									System.out.println("------------------------------------------------------------------");
								}	
								
								System.out.println("Tem certeza que deseja pagar este pedido ? 1 para SIM, Quaquer para não");
								int optPagamentoConfirm = leitor.nextInt();
								
								if (optPagamentoConfirm == 1) {
									
									pedidoPagamento.pagarPedido();
									em.getTransaction().begin();
									pedidoDao.atualizar(pedidoPagamento);
									em.getTransaction().commit();
									
									
									pedidosPendentes = pedidoDao.buscarPedidosPendentes(StatusPedido.PENDENTE);
									if (!pedidosPendentes.isEmpty()) {
										pedidoDao.imprimirPedidos(pedidosPendentes);
									} else {
										System.out.println("Você não possui pedidos a serem pagos");
									}
								}
								
								
							}
						}
						
						else if (optUsuario == 3) {
							
							/* 
							 * Mocks para fins testar o cancelamento dos pedidos, caso queira obter mais testes basta incluir mais mocks 
							 * de pedidos, produtos e/ou clientes ou cadastrar manu
							*/
							
							Produto produto2 = produtoDao.buscarPorId(2l);
							Produto produto3 = produtoDao.buscarPorId(3l);
		
							Pedido pedido = new Pedido(clienteTeste);
							Pedido pedido2 = new Pedido(clienteTeste);
							
							pedido.adicionarItem(new ItemPedido(2, pedido, produto2));
							pedido2.adicionarItem(new ItemPedido(3, pedido2, produto3));
							
							pedido2.setStatus(StatusPedido.PAGO);
							
							if (contCancelamento == 0) {
								em.getTransaction().begin();
								pedidoDao.cadastrar(pedido);
								pedidoDao.cadastrar(pedido2);
								em.getTransaction().commit();
								//controlador para fins de não cadastrar novamente no banco de dados e continuar cancelando os pedidos até tornar a lista vazia
								contCancelamento ++;
							}
						
							List<Pedido> pedidosPendentes = pedidoDao.buscarPedidosPendentes(StatusPedido.PENDENTE);
							List<Pedido> pedidosPagos = pedidoDao.buscarPedidosPendentes(StatusPedido.PAGO);
							
							if (pedidosPendentes.isEmpty() && pedidosPagos.isEmpty()) {
								System.out.println();
								System.out.println("Você não possui pedidos a serem cancelados");
								System.out.println();
							} else {
								
								if (!pedidosPendentes.isEmpty()) {
									pedidoDao.imprimirPedidos(pedidosPendentes);
								}
								
								if (!pedidosPagos.isEmpty()) {
									pedidoDao.imprimirPedidos(pedidosPagos);
								}
								
								System.out.println("Digite o numero do pedido que deseja cancelar");
								long optPagamento = leitor.nextLong();
								
								Pedido pedidoCancelamento = pedidoDao.buscarPorId(optPagamento);
								List<ItemPedido> itens = pedidoCancelamento.getItens();
								
								System.out.println("O pedido número: " + optPagamento + " tem os seguintes itens:");
								System.out.println("------------------------------------------------------------------");
								for (ItemPedido i : itens) {
									String nome = i.getProduto().getNome();
									String descricao = i.getProduto().getDescricao();
									int qtdItens = i.getQuantidade();
									BigDecimal valorUnitario = i.getPrecoUnitario();
									System.out.println("Quantidade: " + qtdItens + " | Valor Unitário: R$ " + valorUnitario + " | Item: " + nome + " " + descricao );
									System.out.println("------------------------------------------------------------------");
								}
								
								System.out.println("Tem certeza que deseja cancelar este pedido ? 1 para SIM, Qualquer para não");
								int optCancelamentoConfirm = leitor.nextInt();
								
								if (optCancelamentoConfirm == 1) {
									pedidoCancelamento.cancelarPedido();
									
									em.getTransaction().begin();
									pedidoDao.atualizar(pedidoCancelamento);
									em.getTransaction().commit();
									
									System.out.println("Você cancelou o pedido numero " +  optPagamento + " com o Valor de: R$" + pedido.getValorTotal());
									
									pedidosPendentes = pedidoDao.buscarPedidosPendentes(StatusPedido.PENDENTE);
									pedidosPagos = pedidoDao.buscarPedidosPendentes(StatusPedido.PAGO);
									
									if (!pedidosPendentes.isEmpty()) {
										pedidoDao.imprimirPedidos(pedidosPendentes);
									}
									if (!pedidosPagos.isEmpty()) {
										pedidoDao.imprimirPedidos(pedidosPagos);
									} else {
										System.out.println("Você não possui pedidos a serem cancelados");
									}
								}
							}
						}
						
						else {
							System.out.println("Sistema encerrado com sucesos");
							break;
						}
						
					}
				}
			} else {
				System.out.println("Usuário o senha incorreta, por favor tente novamente!");
			}
		} catch (NullPointerException e) {
			System.out.println("Usuário o senha incorreta, por favor tente novamente");
		}
		
	}
	
	private static void popularBancoDeDados() {
		
		Categoria adulto = new Categoria("ADULTO");
		Categoria infatil = new Categoria("INFANTIL");
		Categoria masc = new Categoria("MASC");
		Categoria fem = new Categoria("FEM");
		Categoria blusa = new Categoria("BLUSA");
		Categoria vestido = new Categoria("VESTIDO");
		Categoria camisa = new Categoria("CAMISA");
		Categoria camiseta = new Categoria("CAMISETA");
		Categoria shorts = new Categoria("SHORT");
		Categoria calca = new Categoria("CALÇA");
		Categoria sapato = new Categoria("SAPATO");
		Categoria inverno = new Categoria("INVERNO");
		Categoria verao = new Categoria("VERÃO");
		
		Produto camisa1 = new Produto(118,"Camisa", "Camuflada", new BigDecimal("59.90"), adulto);
		Produto camisa2 = new Produto(134,"Camisa", "Vermelha", new BigDecimal("79.90"), infatil);
		Produto camiseta1 = new Produto(100,"Camiseta", "Estampada", new BigDecimal("29.90"), fem);
		Produto shorts1 = new Produto(112,"Short", "Jeans", new BigDecimal("109.90"), masc);
		Produto vestido1 = new Produto(100,"Vestido", "Longo", new BigDecimal("189.90"), vestido);
		Produto blusa1 = new Produto(80, "Blusa", "Colorida", new BigDecimal("30.90"), blusa);
		Produto sueter = new Produto(78, "Sueter", "Tricor", new BigDecimal("119.90"), inverno);
		Produto casaco = new Produto(210, "Casaco", "Jeans", new BigDecimal("179.90"), inverno);
		Produto conjunto = new Produto(44, "Pijama", "de Frio", new BigDecimal("129.90"), inverno);
		Produto sunga1 = new Produto(56, "Sunga", "Preta", new BigDecimal("99.90"), verao);
		Produto sunga2 = new Produto(90, "Sunga", "Azul", new BigDecimal("69.90"), verao);
		Produto biquine1 = new Produto(96, "Biquine", "Verde", new BigDecimal("114.90"), verao);
		Produto biquine2 = new Produto(36, "Biquine", "Amarelo", new BigDecimal("99.90"), verao);
		Produto maio = new Produto(67, "Maiô", "Com Bolinhas", new BigDecimal("139.90"), verao);
		Produto calca1 = new Produto(98, "Calça", "Social", new BigDecimal("139.90"), calca);
		Produto calca2 = new Produto(12, "Calça", "Jeans", new BigDecimal("139.90"), calca);
		Produto calca3 = new Produto(28, "Calça", "Moletom", new BigDecimal("99.90"), calca);
		Produto calca4 = new Produto(30, "Calça", "Boca de Sino", new BigDecimal("109.90"), calca);
		
		Endereco endereco = new Endereco("48607235", "Avenida da Amizade", "1069", "General Dutra", "Paulo Afonso", "Bahia");
		Endereco endereco1 = new Endereco("48608490", "Rua da Harmonia", "S/N", "Alves de Souza", "Paulo Afonso", "Bahia");
		Endereco endereco2 = new Endereco("48605605", "Avenida Centenário", "650", "Centenário", "Paulo Afonso", "Bahia");
		Endereco endereco3 = new Endereco("57051375", "Travessa Iris Alagoense", "22", "Farol", "Maceió", "Alagoas");
		Endereco endereco4 = new Endereco("48601270", "Rua São Francisco", "500", "Centro", "Paulo Afonso", "Bahia");
		Endereco endereco5 = new Endereco("48000600", "Avenida Getulio Vargas", "48601-000", "Centro", "Paulo Afonso", "Bahia");
		
		
		Date date = new Date(14, 02, 1994);
		Date date1 = new Date(23, 07, 1995);
		Date date2 = new Date(23, 07, 2003);
		Date date3 = new Date(23, 9, 1992);
		Date date4 = new Date(17, 02, 1986);
		Date date5 = new Date(24, 10, 1996);
		Date date6 = new Date(18, 05, 1990);
		Date date7 = new Date(12, 02, 2000);
		
		
		Cliente cliente = new Cliente("gutoyago","testejava", "Yago Augusto", "20392432080", date, endereco );
		Cliente cliente1 = new Cliente("erikacarolis","testejava","Erika Caroline", "03631279094", date1, endereco );
		Cliente cliente2 = new Cliente("dudacamile","testejava","Eduarda Camile", "07976907000", date2, endereco );
		Cliente cliente3 = new Cliente("yayaguedes","testejava","Yasmin Guedes", "22457912075", date3, endereco3 );
		Cliente cliente4 = new Cliente("marycleide","testejava","Cleide Maria", "84608121030", date4, endereco2);
		Cliente cliente5 = new Cliente("zecarlos12","testejava","Jose Carlos", "96105819005", date5, endereco3);
		Cliente cliente6 = new Cliente("vallins","testejava","Val Lins", "51206817003", date6, endereco5);
		Cliente cliente7 = new Cliente("marimilene","testejava","Marianne Milene", "27183061060", date7, endereco4);
		
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		EnderecoDao enderecoDao = new EnderecoDao(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(adulto);
		categoriaDao.cadastrar(infatil);
		categoriaDao.cadastrar(masc);
		categoriaDao.cadastrar(fem);
		categoriaDao.cadastrar(blusa);
		categoriaDao.cadastrar(vestido);
		categoriaDao.cadastrar(camisa);
		categoriaDao.cadastrar(camiseta);
		categoriaDao.cadastrar(shorts);
		categoriaDao.cadastrar(calca);
		categoriaDao.cadastrar(sapato);
		categoriaDao.cadastrar(inverno);
		categoriaDao.cadastrar(verao);
		
		produtoDao.cadastrar(camisa1);
		produtoDao.cadastrar(camisa2);
		produtoDao.cadastrar(camiseta1);
		produtoDao.cadastrar(shorts1);
		produtoDao.cadastrar(vestido1);
		produtoDao.cadastrar(blusa1);
		produtoDao.cadastrar(sueter);
		produtoDao.cadastrar(casaco);
		produtoDao.cadastrar(conjunto);
		produtoDao.cadastrar(sunga1);
		produtoDao.cadastrar(sunga2);
		produtoDao.cadastrar(biquine1);
		produtoDao.cadastrar(biquine2);
		produtoDao.cadastrar(maio);
		produtoDao.cadastrar(calca1);
		produtoDao.cadastrar(calca2);
		produtoDao.cadastrar(calca3);
		produtoDao.cadastrar(calca4);
		
		enderecoDao.cadastrar(endereco);
		enderecoDao.cadastrar(endereco1);
		enderecoDao.cadastrar(endereco2);
		enderecoDao.cadastrar(endereco3);
		enderecoDao.cadastrar(endereco4);
		enderecoDao.cadastrar(endereco5);
		
		clienteDao.cadastrar(cliente);
		clienteDao.cadastrar(cliente1);
		clienteDao.cadastrar(cliente2);
		clienteDao.cadastrar(cliente3);
		clienteDao.cadastrar(cliente4);
		clienteDao.cadastrar(cliente5);
		clienteDao.cadastrar(cliente6);
		clienteDao.cadastrar(cliente7);
		
		
		em.getTransaction().commit();
		em.close();
	}

}
