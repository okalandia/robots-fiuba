import org.jgap.InvalidConfigurationException;

import modelo.entrenamiento.ag.JugadasTaTeTi;


public class MainCreacionJugadasEntrenamiento {

	public static void main(String[] args) {
		JugadasTaTeTi jugadas= new JugadasTaTeTi();
		try {
			jugadas.cargar();
			jugadas.evolucionar();
		} catch (InvalidConfigurationException e) {
			System.err.println("NO SE PUEDO REALIZAR");
			e.printStackTrace();
		}
	}
}
