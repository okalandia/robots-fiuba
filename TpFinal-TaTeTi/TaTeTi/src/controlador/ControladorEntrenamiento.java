package controlador;

import modelo.Constantes;
import modelo.entrenamiento.Entrenador;

public class ControladorEntrenamiento {
	
	private Entrenador entrenador;
	
	public ControladorEntrenamiento() {
		entrenador= new Entrenador();
	}
	
	public void entrenarRedNeuronalTaTeTi() {
		entrenador.entrenarRedNeuronal(Constantes.ARCH_ENTRENAMIENTO_RN_TATETI);		
	}

	public void salvarRedNeuronalTaTeTi() {
		entrenador.salvarRedNeuronal(Constantes.ARCH_RN_TATETI);
	}
}
