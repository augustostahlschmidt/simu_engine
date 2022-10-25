package test;

public class Timesheet {
	public int chave;
	public int tempo1;
	public int tempo2;
	
	public int minuto;
	public int valor;
	
	public Timesheet(int chave, int minutoInicial) {
		this.chave = chave;
		this.tempo1 = minutoInicial;
	}
	
	public Timesheet(int chave, int minuto, int valor) {
		this.chave = chave;
		this.minuto = minuto;
		this.valor = valor;
	}
}
