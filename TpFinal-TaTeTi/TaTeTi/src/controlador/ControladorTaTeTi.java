package controlador;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import vista.TableroVista;
import modelo.Ficha;
import modelo.TaTeTi;
import modelo.jugadores.Jugador;
import modelo.jugadores.estrategias.Estrategia;
import modelo.jugadores.estrategias.EstrategiaHumano;
import modelo.jugadores.estrategias.EstrategiaProcedural;
import modelo.jugadores.estrategias.EstrategiaRandom;
import modelo.jugadores.estrategias.EstrategiaRedNeuronal;

public class ControladorTaTeTi implements Observer {
	
	private TableroVista tableroVista;
	private TaTeTi tateti;
	boolean comienzaJugador1 = true;

	static final public int ESTRATEGIA_HUMANO = 1;
	static final public int ESTRATEGIA_RANDOM = 2;
	static final public int ESTRATEGIA_RED_NEURONAL = 3;
	static final public int ESTRATEGIA_PROCEDURAL = 4;
	
	public ControladorTaTeTi() {
		tableroVista= new TableroVista(3);
		tateti= TaTeTi.getInstancia();
		tateti.addObserver(this);
	}
	
	public void cargarTaTeTi() {
		tateti.getTablero().crearTablero(3);
		tableroVista.dibujar();
	}
	
	public void jugarTaTeTi() {
		boolean seguirJugando;
		do {
			crearJugadores(ESTRATEGIA_HUMANO, ESTRATEGIA_RED_NEURONAL);
			tateti.jugar();
			terminarJuego();
			seguirJugando= hayOtraProximaPartida();
		} while(seguirJugando);
			tateti.guardar();
	}
	
	public void simularPartidas(int cantidad, int estrategia1, int estrategia2){		
		for (int i = 0; i < cantidad; i++){
			crearJugadores(estrategia1, estrategia2);
			tateti.jugar();
			terminarJuego();
			tateti.getTablero().limpiarTablero();
			System.out.println("------------------------------------------------------------------");
		}
		tateti.guardar();
	}

	private boolean hayOtraProximaPartida() {
		boolean seguirJugando;
		tableroVista.mostrar("Desea jugar de nuevo? (s/n): ");
		if((tableroVista.obtenerRespuesta().compareTo("s")) == 0) {
			seguirJugando= true;
			tateti.getTablero().limpiarTablero();
		} else
			seguirJugando= false;
		return seguirJugando;
	}

	private void crearJugadores(int estrategia1, int estrategia2) {
		//AUTOMATIZO
		//tableroVista.mostrar("Desea comenzar? (s/n):\n");
		//if((tableroVista.obtenerRespuesta().compareTo("s")) == 0) {
		Ficha ficha1= Ficha.CIRCULO;
		Ficha ficha2= Ficha.CRUZ;
		if(comienzaJugador1) {
			ficha1= Ficha.CRUZ;
			ficha2= Ficha.CIRCULO;
			tableroVista.mostrar("Comienza Jugador 1\n");
		}
		else{
			tableroVista.mostrar("Comienza Jugador 2\n");
		}

		comienzaJugador1 = !comienzaJugador1;
		tateti.crearJugador(ficha1, crearEstrategia(estrategia1, ficha1));
			
		//tableroVista.mostrar("Elija adversario:\n");
		//tableroVista.mostrar("Random -> r\n");
		//tableroVista.mostrar("Red Neuronal -> rn\n");
		//if((tableroVista.obtenerRespuesta().compareTo("r")) == 0)
			//tateti.crearJugador(fichaComputadora, new EstrategiaRandom());
		//else
		tateti.crearJugador(ficha2, crearEstrategia(estrategia2, ficha2));	

	}

	private Estrategia crearEstrategia(int estrategia, Ficha ficha){
		if (estrategia == ESTRATEGIA_HUMANO){
			return new EstrategiaHumano();
		}
		else if (estrategia == ESTRATEGIA_RANDOM){
			return new EstrategiaRandom();
		}
		else if (estrategia == ESTRATEGIA_RED_NEURONAL){
			return new EstrategiaRedNeuronal(ficha, tateti.getRedNeuronal());
		}
		else if (estrategia == ESTRATEGIA_PROCEDURAL){
			return new EstrategiaProcedural(ficha);
		}
		return null;
	}
	
	private void terminarJuego() {
		tableroVista.mostrar("-- Juego Terminado --\n");
		Jugador jugadorGanador= tateti.getJugadorGanador(); 
		if(jugadorGanador != null) {
			String ficha= jugadorGanador.getFicha().name();
			tableroVista.mostrar("Jugador " + ficha + " gano la partida.\n");
		} else {
			tableroVista.mostrar("La partida termino empatada.\n");			
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 != null)
			tableroVista.mostrar((String) arg1);
		else
			actualizarVista();
	}
	
	private void actualizarVista() {
		ArrayList<String> casilleros= new ArrayList<String>();
		for(int i= 0; i < tateti.getTablero().getCantidadCasilleros(); i++)
			casilleros.add((tateti.getTablero().getCasillero(i)).toString());
		tableroVista.actualizar(casilleros.iterator());		
	}
}
