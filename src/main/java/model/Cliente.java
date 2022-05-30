package model;

import java.sql.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

	private Date dataDeNascimentoDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Endereco endereco;
	
	public Cliente() {
		super();
	}

	public Cliente(String usuario, String senha, String nome, String cpf, Date dataDeNascimentoDate, Endereco endereco) {
		super(usuario, senha, nome, cpf);
		this.dataDeNascimentoDate = dataDeNascimentoDate;
		this.endereco = endereco;
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
