import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import controlador.ControladorTaTeTi;

public class MainSimuladorTaTeTi {

	public static void main(String[] args) {
		//Seleccion contrario y cantidad de partidas
		System.out.println("--- Simulacion de Partidas Ta-Te-Ti ---");
		Scanner in= new Scanner(System.in);
		System.out.println("Elija el rival:");
		System.out.println("1- Humano");
		System.out.println("2- Random");
		System.out.println("3- Procedural");
		System.out.print("Opcion elegida: ");
		int rival= in.nextInt();
		int estrategia;
		switch (rival) {
		case 1:
			estrategia= ControladorTaTeTi.ESTRATEGIA_HUMANO;
			break;
		case 2:
			estrategia= ControladorTaTeTi.ESTRATEGIA_RANDOM;
			break;
		case 3:
			estrategia= ControladorTaTeTi.ESTRATEGIA_PROCEDURAL;
			break;
		default:
			estrategia= ControladorTaTeTi.ESTRATEGIA_RANDOM;
			break;
		}
		
		System.out.print("Elija la cantidad de partidas: ");
		int cantPartidas= in.nextInt();
		System.out.println();
		System.out.println("Simulacion comenzando...");
		
		//Direcciono las salidas por pantalla a un archivo
		if(estrategia != 1) {
			PrintStream out;
			try {
				out = new PrintStream(new FileOutputStream("data/output.txt"));
				System.setOut(out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		ControladorTaTeTi controlador= new ControladorTaTeTi();
		controlador.cargarTaTeTi();
		controlador.simularPartidas(cantPartidas, ControladorTaTeTi.ESTRATEGIA_RED_NEURONAL, estrategia);
		
	}
}