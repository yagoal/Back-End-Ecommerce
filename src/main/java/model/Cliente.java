package model;

import java.sql.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String cpf;
	private String nome;
	private Date dataDeNascimentoDate;
	@OneToOne
	private Endereco endereco;
	
	
	public Cliente() {
		super();
	}

	public Cliente(String nome, String cpf, Date dataDeNascimentoDate, Endereco endereco) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataDeNascimentoDate = dataDeNascimentoDate;
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataDeNascimentoDate() {
		return dataDeNascimentoDate;
	}

	public void setDataDeNascimentoDate(Date dataDeNascimentoDate) {
		this.dataDeNascimentoDate = dataDeNascimentoDate;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
}
