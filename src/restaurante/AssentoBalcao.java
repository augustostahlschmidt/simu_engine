package restaurante;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

public class AssentoBalcao {
	private int numero;
	private boolean ocupada;
	private GrupoClientes grupoClientes;
	
	public AssentoBalcao(int numero) {
		this.numero = numero;
		this.grupoClientes = null;
	} 
	
	public void ocupar(GrupoClientes grupoClientes, int minuto) {
		this.grupoClientes = grupoClientes;
		this.ocupada = true;
		
		Evento evento = new Evento(TipoEvento.GRUPO_OCUPOU_BALCAO, this.grupoClientes, this, minuto);
		Eventos.adicionar(evento);
	}
	
	public void liberar(int minuto) {
		Evento evento = new Evento(TipoEvento.GRUPO_LIBEROU_BALCAO, this.grupoClientes, this, minuto);
		Eventos.adicionar(evento);
		
		this.grupoClientes = null;
		this.ocupada = false;
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
		return "(Balcao " + this.numero + " / ocupada? " + this.ocupada + ")";
	}
}
