package restaurante;

import java.util.ArrayList;
import java.util.List;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

public class FilaMesas {
	private int qtdGruposNaFila;
	private List<GrupoClientes> gruposDeClientes;
	
	public FilaMesas() {
		this.qtdGruposNaFila = 0;
		this.gruposDeClientes = new ArrayList<GrupoClientes>();
	}
	
	public void adicionarClientesNaFila(GrupoClientes grupoClientes, int minuto) {
		this.qtdGruposNaFila++;
		this.gruposDeClientes.add(grupoClientes);
		
		Evento evento = new Evento(TipoEvento.GRUPO_ADICIONADO_FILAMESAS, grupoClientes, minuto);
		Eventos.adicionar(evento);
	}
	
	public void removerClientesDaFila(GrupoClientes grupoDeClientesARemover, int minuto) {
		this.gruposDeClientes.remove(grupoDeClientesARemover);
		this.qtdGruposNaFila--;
		
		Evento evento = new Evento(TipoEvento.GRUPO_SAIU_FILAMESAS, grupoDeClientesARemover, minuto);
		Eventos.adicionar(evento);
	}
	
	public GrupoClientes getProximoGrupoASentar(int minuto) {
		GrupoClientes grupo = this.gruposDeClientes.remove(0);
		this.qtdGruposNaFila--;
		
		Evento evento = new Evento(TipoEvento.GRUPO_SAIU_FILAMESAS, grupo, minuto);
		Eventos.adicionar(evento);		
		
		return grupo;
	}
	
	public int getQtdGruposNaFila() {
		return this.qtdGruposNaFila;
	}
	
	public boolean temGente() {
		if(qtdGruposNaFila > 0)
			return true;
		return false;
	}

	public boolean temGrupoMesaDe2() {
		if(temGente()) {
			for(GrupoClientes gc : gruposDeClientes) {
				if(gc.getQtdClientes() == 2) {
					return true;	
				}
			}
		}
		return false;
	}

	public boolean temGrupoMesaDe4() {
		if(temGente()) {
			for(GrupoClientes gc : gruposDeClientes) {
				if(gc.getQtdClientes() > 2) {
					return true;	
				}
			}
		}
		return false;
	}
	
	public GrupoClientes getProximoGrupoASentar(int numeroDeClientesNoGrupo, int minuto) {
		int index = 0;
		for(int i = 0; i < gruposDeClientes.size(); i++) {
			GrupoClientes gc = gruposDeClientes.get(i);
			
			if(gc.getQtdClientes() == numeroDeClientesNoGrupo) {
				index = i;
				break;
			}
		}
		
		GrupoClientes retorno = gruposDeClientes.remove(index);
		this.qtdGruposNaFila--;
		
		Evento evento = new Evento(TipoEvento.GRUPO_SAIU_FILAMESAS, retorno, minuto);
		Eventos.adicionar(evento);		
		
		return retorno;
	}
}
