package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Log {

	public static void writeEventFile() {
        File fout = new File("src/events_log.txt");       
		FileOutputStream fos = open(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for(String str : Eventos.todosEventos) {
        	write(bw, str);
        	newline(bw);
        }
        
        newline(bw);
        close(bw);	
	}
	
	public static void writeGroupReportFile() {
        File fout = new File("src/group_report_log.txt");       
		FileOutputStream fos = open(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for(EventosGrupo eg : Estatisticas.eventosGrupo) {
        	write(bw, "--> Grupo " + eg.getGrupo() + ": "); newline(bw);
        	
            for(String str : eg.eventos) {
            	write(bw, str);
            	newline(bw);
            }
            
            newline(bw);
        }
        
        newline(bw);
        close(bw);	
	}
	
	public static void writeStatisticsFile() {
        File fout = new File("src/statistics_log.txt");       
		FileOutputStream fos = open(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        write(bw, "Estatisticas: ");
        newline(bw); newline(bw);
        write(bw, "- Numero total de pessoas que passaram pelo restaurante durante a simulacao: " + Estatisticas.numeroTotalPessoas);
        newline(bw); newline(bw);
        write(bw, "- Numero de grupos de clientes: " + Estatisticas.numeroGrupos);
        newline(bw); newline(bw);
        write(bw, "- Tempo total de simulacao: " + Estatisticas.tempoTotalSimulacao + " minutos");
        newline(bw); newline(bw);
        write(bw, "- Tamanho medio das filas: "); newline(bw);
        write(bw, "\t - Fila dos Pedidos: " + String.format( "%.2f", Estatisticas.tamanhoMedioFilaPed) ); newline(bw);
        write(bw, "\t - Fila das Mesas: " + String.format( "%.2f", Estatisticas.tamanhoMedioFilaMesas) ); newline(bw);
        write(bw, "\t - Fila do Balcao: " + String.format( "%.2f", Estatisticas.tamanhoMedioFilaBalc) ); newline(bw);        
        
        newline(bw);
        write(bw, "- Tempo medio de espera das filas: "); newline(bw);
        write(bw, "\t - Fila dos Pedidos: " + String.format( "%.2f", Estatisticas.tempoMedioFilaPedidos) ); newline(bw);
        write(bw, "\t - Fila das Mesas: " + String.format( "%.2f", Estatisticas.tempoMedioFilaMesas) ); newline(bw);
        write(bw, "\t - Fila do Balcao: " + String.format( "%.2f", Estatisticas.tempoMedioFilaBalcao) ); newline(bw);               
        
        
        
        close(bw);	
	}	
	
	private static FileOutputStream open(File fout) {
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(fout);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fos;
	}

	private static void close(BufferedWriter bw) {
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void write(BufferedWriter bw, String str) {
		try {
			bw.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void newline(BufferedWriter bw) {
		try {
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
