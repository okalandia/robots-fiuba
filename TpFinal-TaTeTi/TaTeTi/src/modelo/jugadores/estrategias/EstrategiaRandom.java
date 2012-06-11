package modelo.jugadores.estrategias;

import java.util.Random;
import modelo.Tablero;

public class EstrategiaRandom extends EstrategiaComputadora {

	private Random random;
	
	public EstrategiaRandom() {
		random= new Random();
	}
	
	@Override
	public int generarJugada(Tablero tablero) {
		int posicion= random.nextInt(tablero.getCantidadCasilleros());
		return posicion;
	}
}
