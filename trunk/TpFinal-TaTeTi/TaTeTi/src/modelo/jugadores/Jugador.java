package modelo.jugadores;

import modelo.Ficha;
import modelo.jugadores.estrategias.Estrategia;

public class Jugador {
	
	private Ficha ficha;
	private Estrategia estrategia;
	
	public Jugador(Ficha ficha, Estrategia estrategia) {
		this.setFicha(ficha);
		this.estrategia= estrategia;
	}
	
	public int jugar() {
		return estrategia.getJugada();
	}

	public Ficha getFicha() {
		return ficha;
	}

	private void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}
	
	public Estrategia getEstrategia() {
		return estrategia;
	}
}
