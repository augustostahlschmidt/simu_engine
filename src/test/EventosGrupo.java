package test;

import java.util.ArrayList;
import java.util.List;

public class EventosGrupo {
	public int grupo;
	public List<String> eventos = new ArrayList<String>();
	
	public EventosGrupo(int grupo) {
		this.grupo = grupo;
	}
	
	public void adicionar(String evento) {
		eventos.add(evento);
	}

	public int getGrupo() {
		return grupo;
	}

	public List<String> getEventos() {
		return eventos;
	}
}
