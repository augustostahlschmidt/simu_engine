package restaurante;

public class Pedido {
	private int numero;
	private String nome;
	private GrupoClientes grupoClientes;
	
	public Pedido(GrupoClientes grupoClientes) {
		this.grupoClientes = grupoClientes;
		this.numero = grupoClientes.getNumero();
	}
	
	public Pedido(int numero, String nome) {
		this.numero = numero;
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public GrupoClientes getGrupoClientes() {
		return this.grupoClientes;
	}
}
