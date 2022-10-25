package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import restaurante.GrupoClientes;

public class Gerador {
	public static void gerar() {
		temporizarChegadaNovosGruposClientes();
		temporizarRealizacaoPagamentoPedidos();
		temporizarPreparoPedidos();
		temporizarTempoParaComer();	
		
		gerarGruposDeClientes();
	}
	
	public static Eventos eventos = new Eventos();
	public static int tempoMaximoAGerarGruposDeClientes = 180;
	 
	public static Eventos getEventos() {
		//sort lista de eventos por minuto
		return eventos;
	}
	
	public static void gerarGruposDeClientes() {
		List<Integer> temposDeChegadaDeGrupos = Gerador.getTempoChegadaDeGrupos();
		
		int somaMinutos = 0;
		int stopIndex = 0;
		for(int i = 0; i < temposDeChegadaDeGrupos.size(); i++) {
			somaMinutos += temposDeChegadaDeGrupos.get(i);		
			if(somaMinutos <= 180) {
				GrupoClientes grupoClientes = new GrupoClientes(somaMinutos);
				Evento evento = new Evento(TipoEvento.GRUPO_CHEGA_EM, grupoClientes, somaMinutos);
				eventos.adicionar(evento);			
			}
				
			else if(somaMinutos > 180) {
				stopIndex--;
				break;
			}
		}
	}
	
	public static int GrupoClientesID = 0;
	public static int ClienteID = 0;
	public static int PedidoID = 0;	
	
	public static List<Integer> chegadaNovosGruposClientes = new ArrayList<>();
	public static List<Integer> tempoGrupoPedirEPagar = new ArrayList<>();
	public static List<Integer> tempoPrepararPedido = new ArrayList<>();
	public static List<Integer> tempoGrupoComer = new ArrayList<>();

	public static int numeroDeGrupos = 60;
	public static int qtdNumerosAGerar = 2000;	
	
	public static int getProximoGrupoClientes() {
		GrupoClientesID++;
		return GrupoClientesID;
	}
	
	public static int getProximoCliente() {
		ClienteID++;
		return ClienteID;
	}
	
	public static int getProximoPedido() {
		PedidoID++;
		return PedidoID;
	}
	
	public static int getGrupoClientesAtual() {
		return GrupoClientesID;
	}
	
	public static int getClienteAtual() {
		return ClienteID;
	}
	
	public static int getPedidoAtual() {
		return PedidoID;
	}	
	
	private static void temporizarChegadaNovosGruposClientes() {
		// 3 minutos (exponencial)
		for(int i = 0; i <= qtdNumerosAGerar; i++) {
			double time_d = normal(3, 1);
			if(time_d < 1)
				time_d = 1;
			int time = (int) time_d;
			if(time < 0) time = 2;
			chegadaNovosGruposClientes.add(time);
		}			
	}
	
	private static void temporizarRealizacaoPagamentoPedidos() {
		// 2 a 8 minutos (normal)		
		for(int i = 0; i <= qtdNumerosAGerar; i++) {
			double d_time  = normal(8, 2);
			int time = (int) d_time;
			if(time < 0) time = 6;
			tempoGrupoPedirEPagar.add(time);
		}		
	}
	
	private static void temporizarPreparoPedidos() {
		// 5 a 14 minutos (normal)
		for(int i = 0; i <= qtdNumerosAGerar; i++) {
			double d_time = normal(14, 5);
			int time = (int) d_time;
			if(time < 0) time = 9;
			tempoPrepararPedido.add(time);
		}		
	}
	
	private static void temporizarTempoParaComer() {
		// 8 a 20 minutos (normal)
		for(int i = 0; i <= qtdNumerosAGerar; i++) {
			double d_time = normal(20, 8);
			int time = (int) d_time;
			
			if(time < 0) time = 12;
			tempoGrupoComer.add(time);
		}	
	}
	
	public static List<Integer> getTempoChegadaDeGrupos() {
		return chegadaNovosGruposClientes;
	}
	
	public static int getTempoGrupoPedirEPagar(int grupo){	
		return tempoGrupoPedirEPagar.get(grupo-1);
	}
	
	public static int getTempoPrepararPedido(int grupo){
		return tempoPrepararPedido.get(grupo-1);
	}
	
	public static int getTempoGrupoComer(int grupo){
		return tempoGrupoComer.get(grupo-1);
	}
	
    private static double normal(double meanValue, double stdDeviationValue){
        double w = 2;
        double result = 0;

        while(w > 1) {
            double u1 = new Random().nextGaussian();
            double u2 = new Random().nextGaussian();

            double v1 = 2 * u1 - 1;
            double v2 = 2 * u2 - 2;

            w = v1*v1 + v2*v2;

            double y = Math.sqrt((-2 * Math.log(w) / w));

            double x1 = v1 * y;

            if(w <= 1) {
                result = meanValue + x1 * stdDeviationValue;
            }
        }
        return result;
    }
    
    private static double exponential(double meanValue){
        return meanValue*Math.log(1-new Random().nextDouble());
    }
}
