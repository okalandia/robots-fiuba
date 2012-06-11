package modelo.jugadores.estrategias;

import java.util.Scanner;

public class EstrategiaHumano implements Estrategia {

	private Scanner in;
	
	public EstrategiaHumano() {
		in= new Scanner(System.in);
	}
	
	@Override
	public int getJugada() {
		return in.nextInt();
	}
}
