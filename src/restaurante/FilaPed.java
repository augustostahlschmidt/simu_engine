package restaurante;

import java.util.ArrayList;
import java.util.List;

import test.Evento;
import test.Eventos;
import test.TipoEvento;

public class FilaPed {
	private int qtdPedidosNaFila;
	private List<Pedido> pedidos;
	
	public FilaPed() {
		this.qtdPedidosNaFila = 0;
		this.pedidos = new ArrayList<Pedido>();
	}
	
	public void adicionarPedidoNaFila(Pedido pedido, int minuto) {
		this.qtdPedidosNaFila++;
		this.pedidos.add(pedido);
		
		Evento evento = new Evento(TipoEvento.PEDIDO_ADICIONADO_FILAPED, pedido.getGrupoClientes(), pedido, minuto); 
		Eventos.adicionar(evento);
	}
	
	public Pedido getProximoPedidoASerPreparado(int minuto) {
		this.qtdPedidosNaFila--;
		Pedido pedido = this.pedidos.remove(0);	
		
		Evento evento = new Evento(TipoEvento.PEDIDO_SAIU_FILA, pedido.getGrupoClientes(), pedido, minuto);
		Eventos.adicionar(evento);
		
		return pedido;
	}	
	
	public int getQtdPedidossNaFila() {
		return this.qtdPedidosNaFila;
	}

	public boolean temPedidos() {
		if(this.qtdPedidosNaFila > 0)
			return true;
		return false;
	}
	
	public String toString() {
		String str = "";
		for(Pedido p : this.pedidos)
			str += p.getNumero() + " ";
		return str;
	}
}
