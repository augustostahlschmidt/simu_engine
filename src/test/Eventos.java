package test;

import java.util.ArrayList;
import java.util.List;

public class Eventos {	
	private static boolean logarEventoConsole = false;
	public static int minutoDoUltimoEvento = 0;
	private static List<Evento> eventos = new ArrayList<>();
	public static List<EventosGrupo> eventosGrupo = new ArrayList<>();	
	public static List<String> todosEventos = new ArrayList<>();	
	
	public static void adicionar(Evento evento) {
		eventos.add(evento);
		logar(evento);
		atualizarMinutoUltimoEvento(evento);
	}
	
	private static void atualizarMinutoUltimoEvento(Evento evento) {
		int minutoEvento = evento.getMinuto();
		
		if(minutoEvento > minutoDoUltimoEvento) {
			minutoDoUltimoEvento = minutoEvento;
			minutoDoUltimoEvento++;
		}
	}

	public static int getMinutoUltimoEvento() {
		return eventos.get(eventos.size()-1).getMinuto();
	}

	public static List<Evento> getEventosDoMinuto(int minuto) {
		List<Evento> eventosDoMinuto = new ArrayList<>();
		
		for(Evento evento : eventos)
			if(evento.getMinuto() == minuto)
				eventosDoMinuto.add(evento);
		
		return eventosDoMinuto;
	}
	
	public static int minutoAtual = 0;
	public static int iteracoes = 20;
	public static void logar(Evento evento) {
		String minutoAtualstr = "[Minuto " + minutoAtual + "] ";
		String log = null;
		String grupoStr = null;
		
		if(evento.getGrupoClientes() != null)
			grupoStr = "GRUPO_" + evento.getGrupoClientes().getNumero();
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_CHEGOU) {
			log = "[Chegou] " + grupoStr + " / qtdPessoas " + evento.getGrupoClientes().getQtdClientes() + " -> Termina de pedir no minuto " + evento.getMinutoEspecial();
			Estatisticas.adicionarPessoas(evento.getGrupoClientes());
			Estatisticas.adicionarGrupo();
			Estatisticas.eventosGrupo.add(new EventosGrupo(evento.getGrupoClientes().getNumero()));
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);			
		}

		if(evento.getTipoEvento() == TipoEvento.GRUPO_TERMINA_PEDIR_EM) {
			return;
//			log = "Grupo " + evento.getGrupoClientes().getNumero() + " sai do caixa " + evento.getCaixa().getNumero() + " no minuto " + evento.getGrupoClientes().getMinutoGrupoSairFilaCaixa();
		}
		
		if(evento.getTipoEvento() == TipoEvento.PEDIDO_ADICIONADO_FILAPED) {
			Estatisticas.temposEsperaFilaPedidos.add( new Timesheet(evento.getGrupoClientes().getNumero(), minutoAtual) );
			log = "[Pedido adicionado na fila] " + grupoStr;
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}

		if(evento.getTipoEvento() == TipoEvento.PEDIDO_SAIU_FILA) {
			Estatisticas.finalizarTempoEsperaPedido(evento.getGrupoClientes(), minutoAtual);
			log = "[Pedido Saindo da Fila] " + evento.getPedido().getNumero() + " " + grupoStr;
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.COZINHA_PREPARANDO_PEDIDO) {
			log = "[Pedido Sendo Preparado] " + evento.getPedido().getNumero() + " do " + grupoStr + " -> Fica pronto no minuto " + evento.getMinutoEspecial();
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.COZINHA_TERMINA_PEDIDO_EM) {
			return;
//			log = "Pedido do Grupo " + evento.getGrupoClientes().getNumero() + " fica pronto no minuto " + evento.getMinuto();
		}
		
		if(evento.getTipoEvento() == TipoEvento.COZINHA_TERMINOU_PEDIDO) {
			log = "[Pedido Pronto] " + evento.getPedido().getNumero() + " " + grupoStr;	
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_ADICIONADO_FILAMESAS) {
			Estatisticas.temposEsperaFilaMesas.add( new Timesheet(evento.getGrupoClientes().getNumero(), minutoAtual) );
			log = "[Grupo foi pra fila de mesas] " + grupoStr;	
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}

		if(evento.getTipoEvento() == TipoEvento.GRUPO_ADICIONADO_FILABALC) {
			Estatisticas.temposEsperaFilaBalcao.add( new Timesheet(evento.getGrupoClientes().getNumero(), minutoAtual) );
			log = "[Grupo foi pra fila do balcao] " + grupoStr;	
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_SAIU_FILABALC) {
			Estatisticas.finalizarTempoEsperaBalcao(evento.getGrupoClientes(), minutoAtual);
			log = "[Grupo Saiu da Fila do Balcao] " + grupoStr;
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}

		if(evento.getTipoEvento() == TipoEvento.GRUPO_SAIU_FILAMESAS) {
			Estatisticas.finalizarTempoEsperaMesa(evento.getGrupoClientes(), minutoAtual);
			log = "[Grupo Saiu da Fila de Mesas] " + grupoStr;
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}

		if(evento.getTipoEvento() == TipoEvento.GRUPO_OCUPOU_BALCAO) {
			log = "[Sentou balcao " + evento.getAssentoBalcao().getNumero() + "] " + grupoStr;	
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_OCUPOU_MESA) {
			log = "[Sentou mesa " + evento.getMesa().getNumero() + "] " + grupoStr;	
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_LIBEROU_BALCAO) {
			log = "[Leaving] " + grupoStr + " [Terminou de comer, Balcao " + evento.getAssentoBalcao().getNumero() + " liberado]";		
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_LIBEROU_MESA) {
			log = "[Leaving] " + grupoStr + " [Terminou de comer, Mesa " + evento.getMesa().getNumero() + " liberada]";		
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}

		if(evento.getTipoEvento() == TipoEvento.GRUPO_TERMINA_COMER_EM) {
			log = "[Servido] " + grupoStr + " -> termina de comer no minuto " + evento.getMinutoEspecial();
//			log = "Grupo " + evento.getGrupoClientes().getNumero() + " termina de comer no minuto " + evento.getGrupoClientes().getMinutoGrupoDesocupaMesa();		
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(evento.getTipoEvento() == TipoEvento.RESTAURANTE_FECHOU) {
			log = "Restaurante fechou.";	
			Estatisticas.tempoTotalSimulacao = evento.getMinuto();
		}
		
		if(evento.getTipoEvento() == TipoEvento.PEDIDO_PRONTO_TAKEAWAY) {
			log = "[Takeaway] Pedido do grupo " + grupoStr + " ficou pronto enquanto ele(s) estava(m) na fila.";
			Estatisticas.adicionarEventoDeGrupo(evento.getGrupoClientes().getNumero(), minutoAtualstr + log);
		}
		
		if(log == null)
			return;
		
		todosEventos.add(minutoAtualstr + log);
		
		if(!logarEventoConsole)
			return;
		System.out.println(minutoAtualstr + log);
	}
}
