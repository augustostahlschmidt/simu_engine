package restaurante;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

public class Mesa {
	private int numero;
	private int lugares;
	private boolean ocupada;
	private GrupoClientes grupoClientes;
	
	public Mesa(int numero, int lugares) {
		this.numero = numero;
		this.lugares = lugares;
		this.grupoClientes = null;
	} 
	
	public void ocupar(GrupoClientes grupoClientes, int minuto) {
		this.grupoClientes = grupoClientes;
		this.ocupada = true;
		
		Evento evento = new Evento(TipoEvento.GRUPO_OCUPOU_MESA, grupoClientes, this, minuto);
		Eventos.adicionar(evento);
	}
	
	public void liberar(GrupoClientes grupoClientes, int minuto) {
		this.grupoClientes = null;
		this.ocupada = false;
		
		Evento evento = new Evento(TipoEvento.GRUPO_LIBEROU_MESA, grupoClientes, this, minuto);
		Eventos.adicionar(evento);
	}
	
	public boolean estaOcupada() {
		return this.ocupada;
	}

	public int getNumero() {
		return this.numero;
	}
	
	public GrupoClientes getGrupoClientes() {
		return this.grupoClientes;
	}
	
	public String toString() {
		return "(Mesa " + this.numero + " / ocupada? " + this.ocupada + ")";
	}
}
