package restaurante;

import java.util.ArrayList;
import java.util.List;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

public class Cozinha {
	private int qtdMaximaPedidosAoMesmoTempo;
	private int qtdPedidosSendoPreparados;
	private List<Pedido> pedidosSendoPreparados;
	
	public Cozinha() {
		this.qtdMaximaPedidosAoMesmoTempo = 3;
		this.qtdPedidosSendoPreparados = 0;
		this.pedidosSendoPreparados = new ArrayList<Pedido>();
	}
	
	public boolean estaCheia() {
		if(this.qtdPedidosSendoPreparados == this.qtdMaximaPedidosAoMesmoTempo)
			return true;
		return false;
	}
	
	public void adicionarPedido(Pedido pedido, int minuto) { 	
		int minutoQueFicaPronto = minuto + pedido.getGrupoClientes().getTempoParaPrepararPedido();	
		pedido.getGrupoClientes().setPedidoProntoNoMinuto(minutoQueFicaPronto);
		
		Evento evento = new Evento(TipoEvento.COZINHA_PREPARANDO_PEDIDO, pedido.getGrupoClientes(), pedido, minuto); 
		evento.setMinutoEspecial(minutoQueFicaPronto);
		Eventos.adicionar(evento);	
		
		evento = new Evento(TipoEvento.COZINHA_TERMINA_PEDIDO_EM, pedido.getGrupoClientes(), pedido, minutoQueFicaPronto);
		Eventos.adicionar(evento);
		
		this.qtdPedidosSendoPreparados++;
		this.pedidosSendoPreparados.add(pedido);
	}
	 
	public Pedido removerPedido(GrupoClientes grupoClientes, Pedido pedidoARemover, int minuto) {
		Pedido pedido = null;
		
		for(int i = 0; i < this.pedidosSendoPreparados.size(); i++) {
			if(this.pedidosSendoPreparados.get(i).getNumero() == pedidoARemover.getNumero()) {
				this.qtdPedidosSendoPreparados--;
				pedido = this.pedidosSendoPreparados.remove(i);
			}
		}		
		
		Evento evento = new Evento(TipoEvento.COZINHA_TERMINOU_PEDIDO, grupoClientes, pedidoARemover, minuto); 
		Eventos.adicionar(evento);
		
		return pedido;
	}
}
