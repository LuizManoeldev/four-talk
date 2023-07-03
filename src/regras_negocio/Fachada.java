package regras_negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private Fachada() {}

	private static Repositorio repositorio = new Repositorio();
	
	
	
	public static void carregar() {
		repositorio.carregarObjetos();
	}

	public static ArrayList<Individual> listarIndividuos() {
		return repositorio.getIndividuos();	
	}
	public static ArrayList<Grupo> listarGrupos() {
		return repositorio.getGrupos();
	}
	public static ArrayList<Mensagem> listarMensagens() {
		return repositorio.getMenssagens();
	}

	public static ArrayList<Mensagem> listarMensagensEnviadas(String nome) throws Exception{
		Individual ind = repositorio.localizarIndividual(nome);	
		if(ind == null) 
			throw new Exception("listar  mensagens enviadas - nome nao existe:" + nome);

		return ind.getEnviadas();	
	}

	public static ArrayList<Mensagem> listarMensagensRecebidas(String nome) throws Exception{
		Individual ind = repositorio.localizarIndividual(nome);	
		if(ind == null) 
			throw new Exception("listar  mensagens recebidas - nome nao existe:" + nome);

		return ind.getRecebidas();
	}
	
	// 1 - Check
	public static void criarIndividuo(String nome, String senha) throws  Exception{
		if(nome.isEmpty()) 
			throw new Exception("criar individual - nome vazio:");
		if(senha.isEmpty()) 
			throw new Exception("criar individual - senha vazia:");
		
		Participante p = repositorio.localizarParticipante(nome);	
		if(p != null) 
			throw new Exception("criar individual - nome ja existe:" + nome);


		Individual individuo = new Individual(nome,senha, false);
		repositorio.adicionar(individuo);
		
		repositorio.salvarObjetos();
	}
	
	// 2 - Check
	public static Individual validarIndividuo(String nomeindividuo, String senha) throws Exception{
		Individual individuo = repositorio.localizarIndividual(nomeindividuo);
		
		if (individuo == null) 
			throw new Exception("validar individuo - individuo inexistente");
		if (individuo.getSenha().equals(senha) == false)
			throw new Exception("validar individuo - senha incorreta");
		
		return individuo;
	}
	
	// 3 - Check
	public static void criarAdministrador(String nome, String senha) throws  Exception{
		Individual individuo = repositorio.localizarIndividual(nome);
		
		if (individuo == null) 
			throw new Exception("criar administrador - individuo inexistente");
		if (individuo.getSenha() != senha)
			throw new Exception("criar administrador - senha incorreta");	
		if (individuo.getAdministrador() == true)
			throw new Exception("criar administrador - ja e administrador");
		
		individuo.setAdministrador(true);
		
		repositorio.salvarObjetos();
	}

	
	// 4 - Check
	public static void criarGrupo(String nome) throws  Exception{
		//localizar nome no repositorio
		Participante p = repositorio.localizarParticipante(nome);
		if(p != null)
			throw new Exception("criar grupo - grupo ja existe");
		
		//criar o grupo	
		Grupo grupo = new Grupo(nome);
		repositorio.adicionar(grupo);
		
		repositorio.salvarObjetos();
	}
	
	// 5 - Check 
	public static void inserirGrupo(String nomeindividuo, String nomegrupo) throws  Exception{
		//localizar nomeindividuo no repositorio
		Individual individuo = repositorio.localizarIndividual(nomeindividuo);
		if(individuo == null)
			throw new Exception("inserir grupo - individuo nao existe");
		
		//localizar nomegrupo no repositorio
		Grupo grupo = (Grupo)repositorio.localizarParticipante(nomegrupo);
		if(grupo == null)
			throw new Exception("inserir grupo - grupo nao existe");
		
		//verificar se individuo nao esta no grupo
		if( individuo.localizarGrupo(nomegrupo) != null)
			if(individuo.localizarGrupo(nomegrupo).equals(grupo) == true)
				throw new Exception("inserir grupo - ja faz parte do grupo");
		else {
		//adicionar individuo com o grupo e vice-versa
		individuo.addGrupo((Grupo)grupo);
		grupo.adicionar(individuo);
		}
		
		repositorio.salvarObjetos();
	}
	
	// 6 - Check	
	public static void removerGrupo(String nomeindividuo, String nomegrupo) throws  Exception{
		//localizar nomeindividuo no repositorio
		Individual individuo = repositorio.localizarIndividual(nomeindividuo);
		if(individuo == null)
			throw new Exception("remover grupo - individuo nao existe");
		
		//localizar nomegrupo no repositorio
		Grupo grupo = (Grupo)repositorio.localizarParticipante(nomegrupo);
		if(grupo == null)
			throw new Exception("remover grupo - grupo nao existe");
		
		//verificar se individuo nao esta no grupo
		if(individuo.localizarGrupo(nomegrupo) != null)
			if(individuo.localizarGrupo(nomegrupo).equals(grupo) != true)
				throw new Exception("remover grupo - nao faz parte do grupo");	
		
		//remover individuo com o grupo e vice-versa
		individuo.removerGrupo(grupo);
		grupo.remover(individuo);
		
		repositorio.salvarObjetos();
	}

	
	public static void criarMensagem(String nomeemitente, String nomedestinatario, String texto) throws Exception{
		
		if(texto.isEmpty()) 
			throw new Exception("criar mensagem - texto vazio:");

		Individual emitente = repositorio.localizarIndividual(nomeemitente);	
		if(emitente == null) 
			throw new Exception("criar mensagem - emitente nao existe:" + nomeemitente);

		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);	
		if(destinatario == null) 
			throw new Exception("criar mensagem - destinatario nao existe:" + nomeemitente);

		if(destinatario instanceof Grupo g && emitente.localizarGrupo(g.getNome())== null)
			throw new Exception("criar mensagem - grupo nao permitido:" + nomedestinatario);

		//gerar id no repositorio para a mensagem -- Pegar o ultimo id += 1
		int id = repositorio.maiorId();
		
		//criar mensagem
		Mensagem mensagem = new Mensagem(id, emitente,destinatario ,texto);
		
		//adicionar mensagem ao emitente e destinatario
		emitente.addEnviada(mensagem);
		destinatario.addRecebida(mensagem);
		
		//adicionar mensagem ao repositorio
		repositorio.adicionar(mensagem);
		
		//caso destinatario seja tipo Grupo então criar copias da mensagem, tendo o grupo como emitente e cada membro do grupo como destinatario, 
		if(destinatario instanceof Grupo) {
			Grupo grupo = (Grupo) destinatario;
			// Mudando o formato da mensagem para = nome/texto
			String nova_mensagem = emitente.getNome() + "/" + mensagem.getTexto();
			mensagem.setTexto(nova_mensagem);
			
			for(Individual ind : grupo.getIndividuos()) {
				if(ind.equals(emitente) == false)
					ind.addRecebida(mensagem);
				//  usando mesmo id e texto, e adicionar essas copias no repositorio
				repositorio.adicionar(mensagem);
			}	
		}
		
		repositorio.salvarObjetos();
	}

public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) throws Exception{
		
		//localizar emitente no repositorio
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);
		
		//localizar destinatario no repositorio
		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);
		
		//obter do emitente a lista  enviadas
		ArrayList<Mensagem> enviadasEmit = new ArrayList<>();
		for(Mensagem m : emitente.getEnviadas()) {
			enviadasEmit.add(m);
			
		}
 
		
		//obter do destinatario a lista  enviadas
		ArrayList<Mensagem> enviadasDest = new ArrayList<>();
		for(Mensagem m : destinatario.getEnviadas()) {
			enviadasDest.add(m);
		}
		
		//criar a lista conversa
		ArrayList<Mensagem> conversa = new ArrayList<>();
		//Adicionar na conversa as mensagens da lista enviadas cujo destinatario é igual ao parametro destinatario
		for(Mensagem m : enviadasEmit) {
			if(m.getDestinatario().equals(destinatario)) {
				conversa.add(m);
			}
			
		}
		//Adicionar na conversa as mensagens da lista recebidas cujo destinatario é igual ao parametro destinatario
		for(Mensagem m : enviadasDest) {
			if(m.getDestinatario().equals(emitente)) {
				conversa.add(m);
			}
			
		}
		
		//ordenar a lista conversa pelo id das mensagens
        Collections.sort(conversa,Comparator.comparingInt(Mensagem::getId));

		//retornar a lista conversa
		return conversa;
		
		
	}

	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
		//Classes para serem implementadas em Individual
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);

		Mensagem m = emitente.localizarEnviada(id);  
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);

		emitente.removerEnviada(m);
		Participante destinatario = m.getDestinatario();
		destinatario.removerRecebida(m);
		repositorio.remover(m);	

		if(destinatario instanceof Grupo g) {
			ArrayList<Mensagem> lista = destinatario.getEnviadas();
			lista.removeIf(new Predicate<Mensagem>() {
				@Override
				public boolean test(Mensagem t) {
					if(t.getId() == m.getId()) {
						t.getDestinatario().removerRecebida(t);
						repositorio.remover(t);	
						return true;		
					}
					else
						return false;
				}
			});

		}
		
		repositorio.salvarObjetos();
	}

	public static ArrayList<Mensagem> espionarMensagens(String nomeadministrador, String termo) throws Exception{
		//localizar individuo no repositorio
		Individual ind = repositorio.localizarIndividual(nomeadministrador);	
		if(ind == null) 
			throw new Exception("espionar mensagens - nome nao existe:" + nomeadministrador);
		
		//verificar se individuo é administrador
		if(ind.getAdministrador() == false) {
			throw new Exception("espionar mensagens - nao é administrador");
		}
		//listar as mensagens que contem o termo no texto
		ArrayList<Mensagem> selecionadas = new ArrayList<>();
		ArrayList<Mensagem> todas = repositorio.getMenssagens();		
		//contains(termo)
		for (Mensagem m : todas) {
			if(m.getTexto().contains(termo)) {
				selecionadas.add(m);
			}
		}
		return selecionadas;
	}

	public static ArrayList<String> ausentes(String nomeadministrador) throws Exception{
		//localizar individuo no repositorio
		Individual individuo = repositorio.localizarIndividual(nomeadministrador);
		if(individuo == null) 
			throw new Exception("ausentes - nome nao existe:" + nomeadministrador);
		//verificar se individuo é administrador
		if(individuo.getAdministrador() == false)
			throw new Exception("ausentes - " + nomeadministrador + " não é administrador");
		//listar os nomes dos participante que nao enviaram mensagens = individuos com arrylist enviadas vazio
		ArrayList<Individual> listaGeral = repositorio.getIndividuos();
		ArrayList<String> ausentes = new ArrayList<>();
		
		for(Individual ind : listaGeral) {
			if(ind.getEnviadas().size() == 0) {
				ausentes.add(ind.getNome());
			}
		}
		
		
		
		return ausentes;
	}

}