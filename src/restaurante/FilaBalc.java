package restaurante;

import java.util.ArrayList;
import java.util.List;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

public class FilaBalc {
	private int qtdGruposNaFila;
	private List<GrupoClientes> gruposDeClientes;
	
	public FilaBalc() { 
		this.qtdGruposNaFila = 0;
		this.gruposDeClientes = new ArrayList<GrupoClientes>();
	}
	
	public void adicionarClientesNaFila(GrupoClientes grupoClientes, int minuto) { 
		this.qtdGruposNaFila++;
		this.gruposDeClientes.add(grupoClientes);
		
		Evento evento = new Evento(TipoEvento.GRUPO_ADICIONADO_FILABALC, grupoClientes, minuto);
		Eventos.adicionar(evento);
	}
	
	public GrupoClientes getProximoGrupoASentar(int minuto) {
		GrupoClientes grupo = this.gruposDeClientes.remove(0);
		this.qtdGruposNaFila--;
		
		Evento evento = new Evento(TipoEvento.GRUPO_SAIU_FILABALC, grupo, minuto);
		Eventos.adicionar(evento);		
		
		return grupo;
	}
	
	public void removerClientesDaFila(GrupoClientes grupoDeClientesARemover, int minuto) { 
		this.gruposDeClientes.remove(grupoDeClientesARemover);
		this.qtdGruposNaFila--;
	}
	
	public int getQtdGruposNaFila() {
		return this.qtdGruposNaFila;
	}
	
	public boolean temGente() {
		if(qtdGruposNaFila > 0)
			return true;
		return false;
	}
}

