package restaurante;

import java.util.List;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

import java.util.ArrayList;

public class Caixa {
	private int numero;
	private int qtdPessoasNaFila;
	private List<GrupoClientes> grupoClientesNaFila;
	
	public Caixa(int numero) {
		this.numero = numero;
		this.qtdPessoasNaFila = 0;
		this.grupoClientesNaFila = new ArrayList<GrupoClientes>();
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public int getQtdPessoasNaFila() {
		return this.qtdPessoasNaFila;
	}
	
	public void adicionarClientesNaFila(GrupoClientes grupoClientes, int minuto) {
		this.qtdPessoasNaFila += grupoClientes.getQtdClientes();
		this.grupoClientesNaFila.add(grupoClientes);
		
		Evento evento = new Evento(TipoEvento.GRUPO_CHEGOU, grupoClientes, this, minuto);  
		evento.setMinutoEspecial(grupoClientes.getMinutoGrupoSairFilaCaixa());
		Eventos.adicionar(evento);
		
		evento = new Evento(TipoEvento.GRUPO_TERMINA_PEDIR_EM, grupoClientes, this, grupoClientes.getMinutoGrupoSairFilaCaixa());
		Eventos.adicionar(evento); 
	} 
	
	public void removerClientesDaFila(GrupoClientes grupoClientesARemover) {	
		for(int i = 0; i < this.grupoClientesNaFila.size(); i++) {
			if(this.grupoClientesNaFila.get(i).getNumero() == grupoClientesARemover.getNumero()) {
				this.qtdPessoasNaFila -= this.grupoClientesNaFila.get(i).getQtdClientes();
				this.grupoClientesNaFila.remove(i);
			}
		}
	}
}
