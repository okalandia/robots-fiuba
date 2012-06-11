package modelo.jugadores.estrategias;

import modelo.TaTeTi;
import modelo.Tablero;

public abstract class EstrategiaComputadora implements Estrategia {

	@Override
	public int getJugada() {
		Tablero tablero= TaTeTi.getInstancia().getTablero();
		int jugada= generarJugada(tablero);
		return jugada;
	}
	
	public abstract int generarJugada(Tablero tablero);
}
