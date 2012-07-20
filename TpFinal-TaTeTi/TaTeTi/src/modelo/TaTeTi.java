package modelo;

import java.util.Observable;

import modelo.excepciones.JugadaInvalida;
import modelo.jugadores.Jugador;
import modelo.jugadores.estrategias.Estrategia;
import modelo.jugadores.estrategias.EstrategiaHumano;
import modelo.jugadores.estrategias.EstrategiaRedNeuronal;
import modelo.red_neuronal.Entrenador;

public class TaTeTi extends Observable {
	
	private Tablero tablero;
	private Jugador jugadorCruz;
	private Jugador jugadorCirculo;
	private Jugador jugadorGanador;
	private Entrenador entrenador;
	
	private static final TaTeTi instancia = new TaTeTi();
	
	private TaTeTi() {
		setTablero(new Tablero());
		entrenador= new Entrenador();
		entrenador.iniciarRN();
	}
	
	public static TaTeTi getInstancia() {
		return instancia;
	}

	public Tablero getTablero() {
		return tablero;
	}

	private void setTablero(Tablero tablero) {
		this.tablero= tablero;
	}
	
	public void crearJugador(Ficha ficha, Estrategia estrategia) {
		if(ficha == Ficha.CRUZ)
			jugadorCruz= new Jugador(ficha, estrategia);
		else
			jugadorCirculo= new Jugador(ficha, estrategia);			
	}
	
	public void jugar() {
		Jugador jugadorDeTurno= jugadorCruz;
		Jugador jugadorAnterior= jugadorCirculo;
		Jugador temp;  
		int jugada;
		do{
			if(jugadorDeTurno.getEstrategia() instanceof EstrategiaHumano) {
				setChanged();
				notifyObservers("Ingrese su jugada: ");
			}
			jugada= jugadorDeTurno.jugar();
			System.out.println("Proxima Jugada: " + jugada);
			try {
				tablero.agregarFicha(jugada, jugadorDeTurno.getFicha());
				entrenador.agregarPosicionJugada(jugada);
				setChanged();
				notifyObservers();
				temp = jugadorDeTurno;
				jugadorDeTurno = jugadorAnterior;
				jugadorAnterior = temp;
			} catch (JugadaInvalida e) {
				if(jugadorDeTurno.getEstrategia() instanceof EstrategiaHumano) {
					setChanged();
					notifyObservers("Jugada invalida. ");
				}
			}
		} while(!juegoTerminado() && !tablero.tableroCompleto());
		if((jugadorCruz.getEstrategia() instanceof EstrategiaRedNeuronal
				|| jugadorCirculo.getEstrategia() instanceof EstrategiaRedNeuronal)) {
			int turnoNN = getTurnoNN(); 
			int turnoRival = (turnoNN + 1) % 2;
			boolean resultadoNN = getResultadoNN();
			boolean resultadoRival = getResultadoRival();
			if (resultadoNN || resultadoRival){
				for (int i = 0; i < 10; i++){
					entrenador.entrenarRedNeuronalConMemoria(turnoNN, resultadoNN);
					entrenador.entrenarRedNeuronalConMemoria(turnoRival, resultadoRival);
				}
			}
			entrenador.salvarRedNeuronal(Constantes.ARCH_RN_TATETI);
		}
		setChanged();
		notifyObservers();
		entrenador.reiniciarJugada();	
	}
	
	private boolean getResultadoNN() {
		boolean gano;
		if(jugadorCruz.getEstrategia() instanceof EstrategiaRedNeuronal)
			gano= gano(jugadorCruz.getFicha());
		else
			gano= gano(jugadorCirculo.getFicha());;
		return gano;
	}

	private boolean getResultadoRival(){
		boolean gano;
		if(jugadorCruz.getEstrategia() instanceof EstrategiaRedNeuronal)
			gano= gano(jugadorCirculo.getFicha());
		else
			gano= gano(jugadorCruz.getFicha());;
		return gano;		
	}
	
	private int getTurnoNN() {
		int turno= -1;
		if(jugadorCruz.getEstrategia() instanceof EstrategiaRedNeuronal)
			turno= 0;
		if(jugadorCirculo.getEstrategia() instanceof EstrategiaRedNeuronal)
			turno= 1;
		return turno;
	}

	private boolean juegoTerminado() {
		if(gano(Ficha.CRUZ)) {
			jugadorGanador= jugadorCruz;
			return true;
		}
		else if(gano(Ficha.CIRCULO)){
			jugadorGanador= jugadorCirculo;
			return true;
		}
		else if (tablero.tableroCompleto()){
			jugadorGanador = null;
			return true;
		}
		else return false;
	}
	
	private boolean gano(Ficha ficha) {
		for(int i= 0; i<tablero.getCantidadCasilleros(); i+=3) {
			if((tablero.getCasillero(i) == tablero.getCasillero(i+1)) && (tablero.getCasillero(i) == tablero.getCasillero(i+2)) ) {
				if(ficha.toString() == tablero.getCasillero(i).toString()) return true;
			}
		}

		for(int j= 0; j<tablero.getAncho(); j++) {
			if((tablero.getCasillero(j) == tablero.getCasillero(j+3)) && (tablero.getCasillero(j) == tablero.getCasillero(j+6)) ) {
				if(ficha.toString() == tablero.getCasillero(j).toString()) return true;
			}
		}
		
		if(tablero.getCasillero(4).toString() == ficha.toString()) {
			if((tablero.getCasillero(0) == tablero.getCasillero(4)) && (tablero.getCasillero(0) == tablero.getCasillero(8)) )
				return true;
	
			if((tablero.getCasillero(2) == tablero.getCasillero(4)) && (tablero.getCasillero(2) == tablero.getCasillero(6)) )
				return true;
		}
		
		return false;
	}
	
	public Jugador getJugadorGanador() {
		return jugadorGanador;
	}

	public void guardar() {
		entrenador.salvarRedNeuronal(Constantes.ARCH_RN_TATETI);
	}
}
