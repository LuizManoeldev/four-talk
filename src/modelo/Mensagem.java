package modelo;

import java.time.LocalDateTime;

public class Mensagem {
	private int id;
	private String texto;
	private Participante emitente;
	private Participante Destinatario;
	private LocalDateTime datahora = LocalDateTime.now();
	
	public Mensagem(int id, Participante emitente, Participante destinatario, String texto) {
		super();
		this.id = id;
		this.texto = texto;
		this.emitente = emitente;
		Destinatario = destinatario;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Participante getEmitente() {
		return emitente;
	}

	public void setEmitente(Participante emitente) {
		this.emitente = emitente;
	}

	public Participante getDestinatario() {
		return Destinatario;
	}

	public void setDestinatario(Participante destinatario) {
		Destinatario = destinatario;
	}

	public LocalDateTime getData() {
		return datahora;
	}

	public void setDatahora() {
		this.datahora = LocalDateTime.now();
	}
	
	public String toString() {
		return this.getId() + ":"				 +
			   " Emitente= " + this.getEmitente() +
			   ", Destinatario= " + this.getDestinatario() +
			   ", Data= " + this.getData() +
			   ", Texto= " + this.getTexto();
			   
	}
	
	
	
	
	

}
