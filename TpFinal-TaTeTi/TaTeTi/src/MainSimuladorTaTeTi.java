import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import controlador.ControladorTaTeTi;

public class MainSimuladorTaTeTi {

	public static void main(String[] args) {
		//Direcciono las salidas por pantalla a un archivo
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("data/output.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ControladorTaTeTi controlador= new ControladorTaTeTi();
		controlador.cargarTaTeTi();
		controlador.simularPartidas(200, ControladorTaTeTi.ESTRATEGIA_RED_NEURONAL, ControladorTaTeTi.ESTRATEGIA_PROCEDURAL);
	}
}