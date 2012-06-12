package controlador;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import vista.TableroVista;
import modelo.Ficha;
import modelo.TaTeTi;
import modelo.jugadores.Jugador;
import modelo.jugadores.estrategias.EstrategiaHumano;
import modelo.jugadores.estrategias.EstrategiaRandom;
import modelo.jugadores.estrategias.EstrategiaRedNeuronal;

public class ControladorTaTeTi implements Observer {
	
	private TableroVista tableroVista;
	private TaTeTi tateti;
	
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
			crearJugadores();
			tateti.jugar();
			terminarJuego();
			seguirJugando= hayOtraProximaPartida();
		} while(seguirJugando);
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

	private void crearJugadores() {
		tableroVista.mostrar("Desea comenzar? (s/n):\n");
		Ficha fichaHumano= Ficha.CIRCULO;
		Ficha fichaComputadora= Ficha.CRUZ;
		if((tableroVista.obtenerRespuesta().compareTo("s")) == 0) {
			fichaHumano= Ficha.CRUZ;
			fichaComputadora= Ficha.CIRCULO;
		}
		tateti.crearJugador(fichaHumano, new EstrategiaHumano());
			
		tableroVista.mostrar("Elija adversario:\n");
		tableroVista.mostrar("Random -> r\n");
		tableroVista.mostrar("Red Neuronal -> rn\n");
		if((tableroVista.obtenerRespuesta().compareTo("r")) == 0)
			tateti.crearJugador(fichaComputadora, new EstrategiaRandom());
		else
			tateti.crearJugador(fichaComputadora, new EstrategiaRedNeuronal(Ficha.CIRCULO));			
	}

	private void terminarJuego() {
		tableroVista.mostrar("-- Juego Terminado --\n");
		Jugador jugadorGanador= tateti.getJugadorGanador(); 
		if(jugadorGanador != null) {
			String ficha= jugadorGanador.getFicha().name();
			tableroVista.mostrar("Jugador " + ficha + " gano la partida.\n");
		} else {
			tableroVista.mostrar("La partida termino empatado.\n");			
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
