package test;

import restaurante.AssentoBalcao;
//import restaurante.Balcao;
import restaurante.Caixa;
import restaurante.GrupoClientes;
import restaurante.Mesa;
import restaurante.Pedido;

public class Evento {
	private TipoEvento tipoEvento;
	private int minuto;
	private GrupoClientes grupoClientes;
	private Pedido pedido;
	private Caixa caixa;
	private Mesa mesa;
	private AssentoBalcao assentoBalcao;
	
	// GRUPO_CHEGA, GRUPO_ADICIONADO_FILABALC, GRUPO_ADICIONADO_FILAMESAS
	public Evento(TipoEvento tipoEvento, GrupoClientes grupoClientes, int minuto) {
		this.tipoEvento = tipoEvento;
		this.minuto = minuto;
		this.grupoClientes = grupoClientes;
	}
	
	// GRUPO_ENTROU_CAIXA, GRUPO_SAIU_CAIXA
	public Evento(TipoEvento tipoEvento, GrupoClientes grupoClientes, Caixa caixa, int minuto) {
		this.tipoEvento = tipoEvento;
		this.minuto = minuto;
		this.caixa = caixa;
		this.grupoClientes = grupoClientes;
	}
	
	// 	PEDIDO_ADICIONADO_FILA, COZINHA_FAZENDO_PEDIDO, COZINHA_TERMINA_PEDIDO,
	public Evento(TipoEvento tipoEvento, GrupoClientes grupoClientes, Pedido pedido, int minuto) {
		this.tipoEvento = tipoEvento;
		this.minuto = minuto;
		this.grupoClientes = grupoClientes;
		this.pedido = pedido;
	}
	
	// GRUPO_SENTOU_BALCAO, GRUPO_LIBEROU_BALCAO,
	public Evento(TipoEvento tipoEvento, GrupoClientes grupoClientes, AssentoBalcao assentoBalcao, int minuto) {
		this.tipoEvento = tipoEvento;
		this.minuto = minuto;
		this.grupoClientes = grupoClientes;
		this.assentoBalcao = assentoBalcao;
	}
	
	//	GRUPO_SENTOU_MESA, GRUPO_LIBEROU_MESA;
	public Evento(TipoEvento tipoEvento, GrupoClientes grupoClientes, Mesa mesa, int minuto) {
		this.tipoEvento = tipoEvento;
		this.minuto = minuto;
		this.grupoClientes = grupoClientes;
		this.mesa = mesa;
	}
	
	public int getMinuto() {
		return this.minuto;
	}
	
	public TipoEvento getTipoEvento() {
		return this.tipoEvento;
	}
	
	public GrupoClientes getGrupoClientes() {
		return this.grupoClientes;
	}
	
	public Pedido getPedido() {
		return this.pedido;
	}
	
	public Caixa getCaixa() {
		return this.caixa;
	}
	
	public Mesa getMesa() {
		return this.mesa;
	}
	
	public AssentoBalcao getAssentoBalcao() {
		return this.assentoBalcao;
	}
	
	private int minutoEspecial;
	public void setMinutoEspecial(int minutoEspecial) {
		this.minutoEspecial = minutoEspecial;
	}
	public int getMinutoEspecial() {
		return this.minutoEspecial;
	}
	
}
