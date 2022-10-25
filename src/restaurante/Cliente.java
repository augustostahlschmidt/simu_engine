package restaurante;

public class Cliente {
	private int numero;
	private int numeroDoGrupo;
	
	public Cliente(int numero, int numeroDoGrupo) {
		this.numeroDoGrupo = numeroDoGrupo;
		this.numero = numero;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNumeroDoGrupo() {
		return this.numeroDoGrupo;
	}
}
