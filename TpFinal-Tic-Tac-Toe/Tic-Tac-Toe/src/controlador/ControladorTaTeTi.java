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
		tateti.crearJugador(Ficha.CRUZ, new EstrategiaHumano());
		tateti.crearJugador(Ficha.CIRCULO, new EstrategiaRandom());
	}
	
	public void jugarTaTeTi() {
		tateti.jugar();
		terminarJuego();
	}

	private void terminarJuego() {
		tableroVista.mostrar("-- Juego Terminado --\n");
		Jugador jugadorGanador= tateti.getJugadorGanador(); 
		if(jugadorGanador != null) {
			String ficha= jugadorGanador.getFicha().name();
			tableroVista.mostrar("Jugador " + ficha + " gano la partida.");
		} else {
			tableroVista.mostrar("La partida termino empatado.");			
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
