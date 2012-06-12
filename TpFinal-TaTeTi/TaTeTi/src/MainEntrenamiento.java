import java.math.BigDecimal;

import modelo.Constantes;
import modelo.red_neuronal.RedNeuronalTaTeTi;
import controlador.ControladorEntrenamiento;

public class MainEntrenamiento {

	public static void main(String[] args) {
		ControladorEntrenamiento controlador= new ControladorEntrenamiento();
		controlador.entrenarRedNeuronalTaTeTi();
		controlador.salvarRedNeuronalTaTeTi();
		
		//Prueba BORRAR!
		RedNeuronalTaTeTi rn= new RedNeuronalTaTeTi();
		rn.restaurarRedNeuronal(Constantes.ARCH_RN_TATETI);
		double[][] inputArray= new double[][] {
				{-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}  	
		};
		BigDecimal[] resultados= rn.preguntar(inputArray);
		for (int i = 0; i < resultados.length; i++) {
			System.out.println("Posicion " + i + " prob " + resultados[i]);
		}
		
		int mejor= 0;
		for (int i = 0; i < resultados.length; i++) {
			if(resultados[mejor].compareTo(resultados[i]) < 0) mejor= i;
		}
		System.out.println("Mejor: " + mejor);
	}
}
