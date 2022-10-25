package restaurante;

import java.util.ArrayList;
import java.util.List;

import test.Evento;
import test.Eventos;
import test.Gerador;
import test.TipoEvento;

public class GrupoClientes {
	private int numero;
	private int qtdClientes;
	private List<Cliente> clientes;

	private int tempoParaGrupoPedirEPagar = 0;
	private int tempoParaComer = 0;	
	private int minutoChegadaGrupo = 0;
	private int minutoGrupoSairFilaCaixa = 0;
	private int minutoGrupoSairMesa = 0;
	private int tempoParaPrepararPedido = 0;
	private int minutoSentou = 0;
	private int minutoTerminaComer = 0;
	private int minutoServido = 0;
	private int pedidoProntoNoMinuto = 0;
	private boolean estaSentado = false;
	private Pedido pedido;
	private int minutoLevantou = 0;
	
	public GrupoClientes(int minutoChegadaGrupo) {
		this.qtdClientes = (int) ((Math.random() * (4)) + 1);			
		this.numero = Gerador.getProximoGrupoClientes();

		this.clientes = new ArrayList<Cliente>();		
		for(int i = 0; i < qtdClientes; i++) {
			int clienteNumero = Gerador.getProximoCliente();
			Cliente cliente = new Cliente(clienteNumero, this.numero);
			this.clientes.add(cliente);
		}	
		
		this.minutoChegadaGrupo = minutoChegadaGrupo;
		this.tempoParaGrupoPedirEPagar = Gerador.getTempoGrupoPedirEPagar(this.numero);
		this.tempoParaComer = Gerador.getTempoGrupoComer(this.numero);
		
		if(this.tempoParaComer < 0) {
			System.out.println();
			System.out.println("TEMPO PARA COMER NEGATIVO???????");
			System.out.println();
		}
		
		this.minutoGrupoSairFilaCaixa = this.minutoChegadaGrupo + this.tempoParaGrupoPedirEPagar;
		this.tempoParaPrepararPedido = Gerador.getTempoPrepararPedido(this.numero);
	}
	
	public void setPedidoProntoNoMinuto(int minuto) {
		this.pedidoProntoNoMinuto = minuto;
	}
	
	public int getTempoParaPrepararPedido() {
		return this.tempoParaPrepararPedido;
	}
	
	public int getMinutoGrupoSairFilaCaixa() {
		return this.minutoGrupoSairFilaCaixa;
	}

	public int getQtdClientes() {
		return qtdClientes;
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public int getTempoParaPedirEPagar() {
		return this.tempoParaGrupoPedirEPagar;
	}

	public void setTempoParaPedirEPagar(int tempoParaPedirEPagar) {
		this.tempoParaGrupoPedirEPagar = tempoParaPedirEPagar;
	}
	
	public int getTempoParaComer() {
		return this.tempoParaComer;
	}
	
	public int getMinutoChegada() {
		return this.minutoChegadaGrupo;
	}
	
	public void sentar(int minuto) {
		this.minutoSentou = minuto;
		this.estaSentado = true;
	}
	
	public void servir(Pedido pedido, int minuto) {
		this.pedido = pedido;
		this.minutoServido = minuto;
		this.minutoTerminaComer = this.minutoServido + this.tempoParaComer;
		
		if(this.minutoTerminaComer < minuto) {
			System.out.println();
			System.out.println("UEPA UEPA UEPA");
			System.out.println("minuoTerminaComer: " + this.minutoTerminaComer);
			System.out.println("minutoServido: " + this.minutoServido);
			System.out.println("tempoParaComer: " + this.tempoParaComer);
			System.out.println();
		}
		
		Evento evento = new Evento(TipoEvento.GRUPO_TERMINA_COMER_EM, this, this.minutoTerminaComer);
		evento.setMinutoEspecial(this.minutoTerminaComer);
		Eventos.adicionar(evento);
	}
	
	public int getMinutoTerminaComer() {
		return this.minutoTerminaComer;
	}
	
	public void levantar(int minuto) {
		this.minutoLevantou = minuto;
		this.estaSentado = false;
	}
	
	public int getMinutoLevantou() {
		return this.minutoLevantou;
	}
	
	public boolean estaSentado() {
		return this.estaSentado;
	}
	
	public String toString() {
		return "[" + numero + "] " + "Minuto chegada: " + this.minutoChegadaGrupo 
				+ " /  QtdClientes: " + qtdClientes + " / TempoGrupoPedirEPagar: " + this.tempoParaGrupoPedirEPagar + 
				" / TempoGrupoComer: " + this.tempoParaComer;
	}
}
