package restaurante;

import java.util.List;
import java.util.stream.Collectors;

import test.Estatisticas;
import test.Evento;
import test.Eventos;
import test.EventosGrupo;
import test.Gerador;
import test.Log;
import test.TipoEvento;

import java.util.ArrayList;
import java.util.Collection;

public class Restaurante {
	private Caixa caixa1;
	private Caixa caixa2;
	
	private List<AssentoBalcao> balcao;
	private List<Mesa> mesas2lugares;
	private List<Mesa> mesas4lugares;
	
	private Cozinha cozinha;
	private FilaBalc filaBalcao;
	private FilaMesas filaMesas;
	private FilaPed filaPedidos;
	
	public Restaurante() {		
		this.caixa1 = new Caixa(1);
		this.caixa2 = new Caixa(2);		
		
		this.balcao = new ArrayList<>();		
		AssentoBalcao assento1 = new AssentoBalcao(1);
		AssentoBalcao assento2 = new AssentoBalcao(2);
		AssentoBalcao assento3 = new AssentoBalcao(3);
		AssentoBalcao assento4 = new AssentoBalcao(4);
		AssentoBalcao assento5 = new AssentoBalcao(5);
		AssentoBalcao assento6 = new AssentoBalcao(6);
		this.balcao.add(assento1);
		this.balcao.add(assento2);
		this.balcao.add(assento3);
		this.balcao.add(assento4);
		this.balcao.add(assento5);
		this.balcao.add(assento6);
		
		this.mesas4lugares = new ArrayList<Mesa>();	
		Mesa mesaQuatroLugares1 = new Mesa(1, 4);
		Mesa mesaQuatroLugares2 = new Mesa(2, 4);
		Mesa mesaQuatroLugares3 = new Mesa(3, 4);
		Mesa mesaQuatroLugares4 = new Mesa(4, 4);
		this.mesas4lugares.add(mesaQuatroLugares1);
		this.mesas4lugares.add(mesaQuatroLugares2);
		this.mesas4lugares.add(mesaQuatroLugares3);
		this.mesas4lugares.add(mesaQuatroLugares4);
		
		this.mesas2lugares = new ArrayList<Mesa>();	
		Mesa mesaDoisLugares1 = new Mesa(5, 2);
		Mesa mesaDoisLugares2 = new Mesa(6, 2);
		Mesa mesaDoisLugares3 = new Mesa(7, 2);
		Mesa mesaDoisLugares4 = new Mesa(8, 2);		
		this.mesas2lugares.add(mesaDoisLugares1); 
		this.mesas2lugares.add(mesaDoisLugares2);
		this.mesas2lugares.add(mesaDoisLugares3);
		this.mesas2lugares.add(mesaDoisLugares4);		
		
		this.filaBalcao = new FilaBalc();
		this.filaMesas = new FilaMesas();
		this.filaPedidos = new FilaPed();		
		this.cozinha = new Cozinha();
	} 

	private List<Evento> eventosProcessadosNoMinuto = new ArrayList<>();
	public void abrir() {	
		this.processarEventos();
	} 

	private void processarEventos() {
		for(int minuto = 0; minuto <= Eventos.minutoDoUltimoEvento; minuto++) {			
			Estatisticas.coletarAmostra(this.filaPedidos, this.filaBalcao, this.filaMesas);
			
			eventosProcessadosNoMinuto = new ArrayList<>();
			Eventos.minutoAtual = minuto;
			
			this.processarEventos(minuto);
		}
	}

	private void processarEventos(int minuto) {
		for(int iteracoes = 0; iteracoes < Eventos.iteracoes; iteracoes++) {
			List<Evento> eventosAProcessar = Eventos.getEventosDoMinuto(minuto);
			
			for(Evento eventoJaProcessado: eventosProcessadosNoMinuto) {
				eventosAProcessar.remove(eventoJaProcessado);
			}
			
			for(Evento evento : eventosAProcessar) {			
				this.processarEvento(evento, minuto);				
				eventosProcessadosNoMinuto.add(evento);
			} 		
		}
	}
	
	private void processarEvento(Evento evento, int minuto) {
		GrupoClientes grupoClientes = evento.getGrupoClientes();
		
		if(evento.getTipoEvento() == TipoEvento.GRUPO_CHEGA_EM) {		
			// Grupo vai para um dos caixas
			Caixa caixa = getCaixaComMenorFila();
			caixa.adicionarClientesNaFila(grupoClientes, minuto);		
		}		
		
		else if(evento.getTipoEvento() == TipoEvento.GRUPO_TERMINA_PEDIR_EM) { 	
			// Cozinha tenta comecar preparo do pedido e grupo tenta sentar
			
			Pedido pedido = new Pedido(grupoClientes);					
			if(cozinha.estaCheia())
				this.filaPedidos.adicionarPedidoNaFila(pedido, minuto);
			else
				cozinha.adicionarPedido(pedido, minuto);				
			
			if(haLugaresDisponiveis(grupoClientes) == true)
				this.acomodarGrupo(grupoClientes, minuto);
			else
				this.adicionarClienteNaFila(grupoClientes, minuto);
		}
		
		else if(evento.getTipoEvento() == TipoEvento.COZINHA_TERMINA_PEDIDO_EM) {
			// Se grupo esta sentado = serve pedidop
			// Se grupo esta na fila = takeaway -> tira grupo da fila e manda embora
			Pedido pedidoPronto = cozinha.removerPedido(grupoClientes, evento.getPedido(), minuto);
			
			if(this.filaPedidos.temPedidos()) {
				Pedido pedido = this.filaPedidos.getProximoPedidoASerPreparado(minuto);
				cozinha.adicionarPedido(pedido, minuto);
			}
				
			if(pedidoPronto.getGrupoClientes().estaSentado() == true) {
				pedidoPronto.getGrupoClientes().servir(pedidoPronto, minuto);
			} else {
				this.takeaway(grupoClientes, pedidoPronto, minuto);
			}
		}
		
		else if(evento.getTipoEvento() == TipoEvento.GRUPO_TERMINA_COMER_EM) {
			// Libera mesa ou balcao para grupo que esta na fila
			int qtdPessoasSairam = this.mandarEmbora(grupoClientes, minuto);
			
			if(qtdPessoasSairam == 1 && this.filaBalcao.temGente() == true) {
				GrupoClientes proximoGrupoASentar = this.filaBalcao.getProximoGrupoASentar(minuto);
				this.acomodarGrupo(proximoGrupoASentar, minuto);
			}
			else if(qtdPessoasSairam == 2) {
				if(this.filaMesas.temGrupoMesaDe2() == true) {
					GrupoClientes proximoGrupoASentar = this.filaMesas.getProximoGrupoASentar(2, minuto);
					this.acomodarGrupo(proximoGrupoASentar, minuto);					
				}
			}
			else if(qtdPessoasSairam > 2) {
				if(this.filaMesas.temGrupoMesaDe4() == true) {
					GrupoClientes proximoGrupoASentar = this.filaMesas.getProximoGrupoASentar(4, minuto);
					this.acomodarGrupo(proximoGrupoASentar, minuto);					
				}				
			}
		}
	}

	private void takeaway(GrupoClientes grupoClientes, Pedido pedidoPronto, int minuto) {
		int qtdClientesNoGrupo = grupoClientes.getQtdClientes();	

		if(qtdClientesNoGrupo == 1) 
			this.filaBalcao.removerClientesDaFila(grupoClientes, minuto);	
		else if(qtdClientesNoGrupo > 1)
			this.filaMesas.removerClientesDaFila(grupoClientes, minuto);		
		
		Evento evento = new Evento(TipoEvento.PEDIDO_PRONTO_TAKEAWAY, grupoClientes, minuto);
		Eventos.adicionar(evento);
	}

	private int mandarEmbora(GrupoClientes grupoClientes, int minuto) {
		int qtdClientesNoGrupo = grupoClientes.getQtdClientes();	
		int grupoClientesARemover = grupoClientes.getNumero();

		if(qtdClientesNoGrupo == 1) {
			grupoClientes.levantar(minuto);
			
			for(AssentoBalcao ab : this.balcao) {
				if(ab.estaOcupada())
					if(ab.getGrupoClientes().getNumero() == grupoClientesARemover)
						ab.liberar(minuto);
			}
		}		
		
		else if(qtdClientesNoGrupo == 2) {
			grupoClientes.levantar(minuto);
			this.desocuparMesa(grupoClientes, mesas2lugares, minuto);
		}			
		else if(qtdClientesNoGrupo > 2) {
			grupoClientes.levantar(minuto);
			this.desocuparMesa(grupoClientes, mesas4lugares, minuto);			
		}
		
		return qtdClientesNoGrupo;
	}

	private void adicionarClienteNaFila(GrupoClientes grupoClientes, int minuto) {
		int qtdClientesNoGrupo = grupoClientes.getQtdClientes();
		
		if(qtdClientesNoGrupo == 1) {
			this.filaBalcao.adicionarClientesNaFila(grupoClientes, minuto);
		}			
		else if(qtdClientesNoGrupo > 1) {
			this.filaMesas.adicionarClientesNaFila(grupoClientes, minuto);
		}
	}

	private void acomodarGrupo(GrupoClientes grupoClientes, int minuto) {		
		int qtdClientesNoGrupo = grupoClientes.getQtdClientes();	
		
		if(qtdClientesNoGrupo == 1) {
			grupoClientes.sentar(minuto);
			
			for(AssentoBalcao ab : this.balcao)
				if(ab.estaOcupada() == false) {
					ab.ocupar(grupoClientes, minuto);
					break;
				}
		}		
		
		else if(qtdClientesNoGrupo == 2) {
			grupoClientes.sentar(minuto);
			this.ocuparProximaMesaDisponivel(grupoClientes, mesas2lugares, minuto);
		}			
		else if(qtdClientesNoGrupo > 2) {
			grupoClientes.sentar(minuto);
			this.ocuparProximaMesaDisponivel(grupoClientes, mesas4lugares, minuto);			
		}
	}

	private boolean haLugaresDisponiveis(GrupoClientes grupoClientes) {
		int qtdClientesNoGrupo = grupoClientes.getQtdClientes();
		
		if(qtdClientesNoGrupo == 1) {
			for(AssentoBalcao ab : this.balcao) {
				if(ab.estaOcupada() == false)
					return true;
			}
			return false;
		}			
		else if(qtdClientesNoGrupo == 2) {
			if( this.temMesaDisponivel(this.mesas2lugares) ) return true;
			else return false;
		}			
		else if(qtdClientesNoGrupo > 2) {
			if( this.temMesaDisponivel(this.mesas4lugares) ) return true;
			else return false;	
		}
		return false;
	}

	public void fechar() {
		Estatisticas.calcularTamanhosMediosFilas();
		Estatisticas.calcularTempoEsperaMedioFilas();
		Eventos.adicionar(new Evento(TipoEvento.RESTAURANTE_FECHOU, null, Eventos.minutoAtual));		
		Log.writeEventFile();
		Log.writeStatisticsFile();
		Log.writeGroupReportFile();
	}
	
	public Caixa getCaixaComMenorFila() {
		if(caixa1.getQtdPessoasNaFila() < caixa2.getQtdPessoasNaFila())
			return caixa1;
		return caixa2;
	}
	
	public boolean temMesaDisponivel(List<Mesa> mesas) {
		for(Mesa mesa : mesas) {
			if(mesa.estaOcupada() == false)
				return true;
		}
		return false;
	}
	
	public void ocuparProximaMesaDisponivel(GrupoClientes grupoClientes, List<Mesa> mesas, int minuto) {
		for(Mesa mesa : mesas) {
			if(mesa.estaOcupada() == false) {
				mesa.ocupar(grupoClientes, minuto);
				break;
			}
		}
	}
	
	public void desocuparMesa(GrupoClientes grupoClientes, List<Mesa> mesas, int minuto) {
		int numeroGrupo = grupoClientes.getNumero();
		
		for(Mesa mesa : mesas) {
			if(mesa.estaOcupada()) {
				if(mesa.getGrupoClientes().getNumero() == numeroGrupo) {
					mesa.liberar(grupoClientes, minuto);
					break;
				}
			}
		}
	}
	
	public boolean estaVazio() {
		boolean balcaoVazio = true;
		boolean mesas2lugaresVazias = true;
		boolean mesas4lugaresVazias = true;
		
		for(AssentoBalcao ab : this.balcao) {
			if(ab.estaOcupada()) {
				balcaoVazio = false;
				System.out.println();
				System.out.println(this.balcao);
			}
		}
		
		for(Mesa mesa2lugares : this.mesas2lugares) {
			if(mesa2lugares.estaOcupada()) {
				mesas2lugaresVazias = false;
				System.out.println();
				System.out.println(this.mesas2lugares);
			}
		}
		
		for(Mesa mesa4lugares : this.mesas4lugares) {
			if(mesa4lugares.estaOcupada()) {
				mesas4lugaresVazias = false;
				System.out.println();
				System.out.println(this.mesas4lugares);
			}
		}
		
//		System.out.println("\nBalcao Vazio? " + balcaoVazio + "\nMesas 2 lugares vazias? " + mesas2lugaresVazias + "\nMesas 4 lugares vazias? " + mesas4lugaresVazias + "\n");
		
		if(balcaoVazio && mesas2lugaresVazias && mesas4lugaresVazias)
			return true;
		return false;
	}
}
