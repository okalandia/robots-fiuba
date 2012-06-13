package modelo.entrenamiento;

import modelo.red_neuronal.RedNeuronalTaTeTi;

public class Entrenador {

	private RedNeuronalTaTeTi RN_TaTeTi;
	
	public Entrenador() {
		RN_TaTeTi= new RedNeuronalTaTeTi();
	}
	
	public void entrenarRedNeuronal(String pathInputFile) {
		RN_TaTeTi.iniciarRedNeuronal();
		RN_TaTeTi.entrenar(pathInputFile);
	}

	public void salvarRedNeuronal(String pathSafeFile) {
		RN_TaTeTi.salvarRedNeuronal(pathSafeFile);
	}
}
