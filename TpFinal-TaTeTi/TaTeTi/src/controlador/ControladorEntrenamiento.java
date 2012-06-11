package controlador;

import modelo.entrenamiento.Entrenador;

public class ControladorEntrenamiento {
	
	private static String PATH_INPUT_FILE= "data/Archivo-Entrenamiento-Prueba.txt";
	private static String PATH_SAFE_FILE= "data/Red-Neuronal-TaTeTi.ser";
	
	private Entrenador entrenador;
	
	public ControladorEntrenamiento() {
		entrenador= new Entrenador();
	}
	
	public void entrenarRedNeuronalTaTeTi() {
		entrenador.entrenarRedNeuronal(PATH_INPUT_FILE);		
	}

	public void salvarRedNeuronalTaTeTi() {
		entrenador.salvarRedNeuronal(PATH_SAFE_FILE);
	}
}
