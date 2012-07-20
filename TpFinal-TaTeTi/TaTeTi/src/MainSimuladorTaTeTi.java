import controlador.ControladorTaTeTi;

public class MainSimuladorTaTeTi {

	public static void main(String[] args) {
		ControladorTaTeTi controlador= new ControladorTaTeTi();
		controlador.cargarTaTeTi();
		controlador.simularPartidas(100, ControladorTaTeTi.ESTRATEGIA_RED_NEURONAL, ControladorTaTeTi.ESTRATEGIA_RANDOM);
	}
}
