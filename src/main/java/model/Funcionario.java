package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario extends Usuario {
	
	private Integer nivelDeAcesso;

	public Funcionario() {
		super();
	}

	public Funcionario(String usuario, String senha, String nome, String cpf, Integer nivelDeAcesso) {
		super(usuario, senha, nome, cpf);
		this.nivelDeAcesso = nivelDeAcesso;
	}
	
	public Integer getNivelDeAcesso() {
		return nivelDeAcesso;
	}

	public void setNivelDeAcesso(Integer nivelDeAcesso) {
		this.nivelDeAcesso = nivelDeAcesso;
	}
	
}
