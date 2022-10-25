package test;

import java.util.ArrayList;
import java.util.List;

import restaurante.FilaBalc;
import restaurante.FilaMesas;
import restaurante.FilaPed;
import restaurante.GrupoClientes;

public class Estatisticas {
	public static int numeroTotalPessoas = 0;
	public static int numeroGrupos = 0;
	public static List<Timesheet> temposEsperaFilaPedidos = new ArrayList<>();
	public static List<Timesheet> temposEsperaFilaMesas = new ArrayList<>();
	public static List<Timesheet> temposEsperaFilaBalcao = new ArrayList<>();
	public static List<EventosGrupo> eventosGrupo = new ArrayList<>();
	
	public static void finalizarTempoEsperaPedido(GrupoClientes gc, int tempoSaidaFila) {
		for(Timesheet t : temposEsperaFilaPedidos)
			if(t.chave == gc.getNumero()) {
				t.tempo2 = tempoSaidaFila;
				break;
			}
	}
	public static void finalizarTempoEsperaMesa(GrupoClientes gc, int tempoSaidaFila) {
		for(Timesheet t : temposEsperaFilaMesas)
			if(t.chave == gc.getNumero()) {
				t.tempo2 = tempoSaidaFila;
				break;
			}		
	}	
	public static void finalizarTempoEsperaBalcao(GrupoClientes gc, int tempoSaidaFila) {
		for(Timesheet t : temposEsperaFilaBalcao)
			if(t.chave == gc.getNumero()) {
				t.tempo2 = tempoSaidaFila;
				break;
			}		
	}	
	
	public static void adicionarPessoas(GrupoClientes grupoClientes) {
		numeroTotalPessoas += grupoClientes.getQtdClientes();
	}

	public static void adicionarGrupo() {
		numeroGrupos++;
	}
	
	public static int tempoTotalSimulacao = 0;
	
	public static double somaAmostraFilaPed = 0;
	public static double somaAmostraFilaMesas = 0;
	public static double somaAmostraFilaBalc = 0;
	
	public static double tamanhoMedioFilaPed = 0;
	public static double tamanhoMedioFilaMesas = 0;
	public static double tamanhoMedioFilaBalc = 0;
	
	public static int qtdAmostras = 0;

	public static void coletarAmostra(FilaPed filaPedidos, FilaBalc filaBalcao, FilaMesas filaMesas) {
		somaAmostraFilaPed += filaPedidos.getQtdPedidossNaFila();
		somaAmostraFilaMesas += filaBalcao.getQtdGruposNaFila();
		somaAmostraFilaBalc += filaMesas.getQtdGruposNaFila();		
		qtdAmostras++;
	}

	public static void calcularTamanhosMediosFilas() {
		tamanhoMedioFilaPed = somaAmostraFilaPed / qtdAmostras;
		tamanhoMedioFilaMesas = somaAmostraFilaMesas / qtdAmostras;
		tamanhoMedioFilaBalc = somaAmostraFilaBalc / qtdAmostras;
	}
	
	public static double tempoMedioFilaPedidos = 0;
	public static double tempoMedioFilaMesas = 0;
	public static double tempoMedioFilaBalcao = 0;
	
	public static void calcularTempoEsperaMedioFilas() {
		double tempoEsperaPedido = 0;
		double somaTempoEsperaPedido = 0;
		for(Timesheet t : temposEsperaFilaPedidos) {
			tempoEsperaPedido = t.tempo2 - t.tempo1;
//			System.out.println("tempoEsperaPedido: " + tempoEsperaPedido);
			somaTempoEsperaPedido += tempoEsperaPedido;
		}
//		System.out.println("somaTempoEsperaPedido: " + somaTempoEsperaPedido);
//		System.out.println("qtd pedidos que esperaram: " + temposEsperaFilaPedidos.size());

		tempoMedioFilaPedidos = somaTempoEsperaPedido / temposEsperaFilaPedidos.size();
		
		double tempoEsperaMesa = 0;
		double somaTempoEsperaMesa = 0;
		for(Timesheet t : temposEsperaFilaMesas) {
			tempoEsperaMesa = t.tempo2 - t.tempo1;
			somaTempoEsperaMesa += tempoEsperaMesa;
		}
		tempoMedioFilaMesas = somaTempoEsperaMesa / temposEsperaFilaMesas.size();
		
		double tempoEsperaBalcao = 0;
		double somaTempoEsperaBalcao = 0;
		for(Timesheet t : temposEsperaFilaBalcao) {
			tempoEsperaBalcao = t.tempo2 - t.tempo1;
			somaTempoEsperaBalcao += tempoEsperaBalcao;
		}
//		System.out.println("somaTempoEsperaBalcao: " + somaTempoEsperaBalcao);
//		System.out.println("qtd grupos esperaram balcao: " + temposEsperaFilaPedidos.size());
		tempoMedioFilaBalcao = somaTempoEsperaBalcao / temposEsperaFilaBalcao.size();
	}
	
	public static void adicionarEventoDeGrupo(int grupo, String evento) {
		for(EventosGrupo eg : eventosGrupo) {
			if(eg.getGrupo() == grupo) {
				eg.adicionar(evento);
			}
		}
	}
}
