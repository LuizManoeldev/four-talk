package modelo;

import java.time.LocalDateTime;

public class Mensagem {
	private int id;
	private String texto;
	private Participante emitente;
	private Participante Destinatario;
	private LocalDateTime datahora;
	
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

	public LocalDateTime getDatahora() {
		return datahora;
	}

	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}
	
	
	
	

}
