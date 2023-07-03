package modelo;

import java.util.ArrayList;

public class Participante {
	protected String nome;
	private ArrayList<Mensagem> recebidas = new ArrayList<>();
	private ArrayList<Mensagem> enviadas = new ArrayList<>();
	
	public Participante(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Mensagem> getRecebidas() {
		return recebidas;
	}

	public void addRecebida(Mensagem mensagem) {
		recebidas.add(mensagem);
	}
	
	public void addEnviada(Mensagem mensagem) {
		enviadas.add(mensagem);
	}
	
	public void removerRecebida(Mensagem mensagem) {
		recebidas.remove(mensagem);
	}
	
	public void removerEnviada(Mensagem mensagem) {
		enviadas.remove(mensagem);
	}
	
	public Mensagem localizarEnviada(int id) {
		Mensagem msg = null;
		for(Mensagem m : enviadas ) {
			if(m.getId() == id) { msg = m;}
		}
		return msg;
	}

	public ArrayList<Mensagem> getEnviadas() {
		return enviadas;
	}

	public void setEnviadas(ArrayList<Mensagem> enviadas) {
		this.enviadas = enviadas;
	}
	
	public String toString() {
		return nome;
	}
	
	

}
