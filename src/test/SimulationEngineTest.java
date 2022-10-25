package test;

import java.util.ArrayList;
import java.util.List;

import restaurante.Caixa;
import restaurante.GrupoClientes;
import restaurante.Restaurante;

/* Modelo
 
1. Regras do modelo:

O restaurante possui duas caixas, cada um com uma fila;
A cada 3 minutos (exponencial) chega um grupo de clientes de 1 a 4 pessoas (random);
O grupo escolhe a menor fila e faz o seu pedido e pagamento, processo que leva 2 a 8 minutos (normal);

Após pedir e pagar, dependendo do tamanho do grupo:

- 1 cliente, tenta sentar no banco do balcão. 
	- se não houver banco disponível, cliente aguarda na FilaBalc;
	
- 2 a 4 clientes, vão para as mesas
	- se 2 clientes, tentam sentar nas mesas de 2 lugares;
		- se não houver mesa de 2 lugares, clientes aguardam na FilaMesas;
	- se 4 clientes, tentam sentar nas mesas de 4 lugares;
		- se não houver mesa de 4 lugares, clientes aguardam na FilaMesas;

O tempo de preparo de refeições é de 5 a 14 minutos (normal);
Até 3 refeições podem ser preparadas simultâneamente pela cozinha; 
Pedidos que chegarem quando já houver 3 pedidos sendo preparados aguardam na FilaPed;

O cliente leva 8 a 20 minutos (normal) para terminar a refeição;
Após isso o grupo vai embora liberando sua mesa ou banco;
s
!!! Problema a ser resolvido: como tratar o caso da refeição ficar pronta para um grupo que ainda agurada numa Fila (FilaBalc ou Fila Mesas). 
Alguma solução deve ser adotada para tratar esta situação. ----> SOLUÇÃO Food Takeaway (Remove refeição da cozinha e remove grupo da fila);

2. Estatísticas a serem contabilizadas:

- gerar grupos de clientes por 3 horas
- número de total de pessoas que passaram pelo restaurante durante a simulação;
- tempo total de simulação;
- tamanho médio de cada uma das filas do modelo
- tempo médio de espera nas filas FilaBalc e FilaMesas

Elaborar gráficos para:
- tamanho de cada uma das filas do modelo;
- quantidade de pessoas realizando a refeição (isto é, comendo) ao longo da simulação;

*/

public class SimulationEngineTest {
	public static void main(String[] args) {	
		Gerador.gerar();
		Restaurante restaurante = new Restaurante();	
		restaurante.abrir();
		if(restaurante.estaVazio())
			restaurante.fechar();
	}	
}
