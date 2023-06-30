package modelo;

import java.util.ArrayList;

public class Individual extends Participante {
	private String senha;
	private boolean administrador = false;
	private ArrayList<Grupo> grupos;
	
	

	public Individual(String nome, String senha, boolean administrador) {
		super(nome); 
		this.senha = senha;
		this.administrador = administrador; 
		
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public Grupo localizarGrupo(String nome) {
		Grupo grupo = null;
		
		for(Grupo g : grupos) {
			if(g.getNome().equals(nome)) { grupo = g;}
		}
		return grupo;
	}
	
	
}
