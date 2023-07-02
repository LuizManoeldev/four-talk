package modelo;

import java.util.ArrayList;

public class Grupo extends Participante {
	private ArrayList<Individual> individuos = new ArrayList<>();
	
	public Grupo(String nome) {
		super(nome);
		
	}

	public ArrayList<Individual> getIndividuos() {
		return individuos;
	}
	
	public void adicionar(Individual individuo) {
		individuos.add(individuo);
	}
	
	public void remover(Individual individuo) {
		individuos.remove(individuo);
	}
	
	
}
