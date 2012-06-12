package modelo.jugadores.estrategias;

import java.math.BigDecimal;

import modelo.Constantes;
import modelo.Ficha;
import modelo.Tablero;
import modelo.red_neuronal.RedNeuronalTaTeTi;

public class EstrategiaRedNeuronal extends EstrategiaComputadora {

	private RedNeuronalTaTeTi rn;
	private Ficha ficha;
	
	public EstrategiaRedNeuronal(Ficha ficha) {
		rn= new RedNeuronalTaTeTi();
		rn.restaurarRedNeuronal(Constantes.ARCH_RN_TATETI);
		this.ficha= ficha;
	}
	
	@Override
	public int generarJugada(Tablero tablero) {
		double[][] inputArray= new double[1][9];
		double valor= 0.0;
		for (int i = 0; i < 9; i++) {
			if(tablero.getCasillero(i) != Ficha.VACIO) {
				if((tablero.getCasillero(i).compareTo(ficha)) == 0) valor= 1.0;
				else valor= -1.0;
			}
			inputArray[0][i]= valor;
		}	
		BigDecimal[] resultados= rn.preguntar(inputArray);
		return getMejorPosicion(resultados);
	}

	//TODO: mejorar!!!!!!!
	private int getMejorPosicion(BigDecimal[] resultados) {
		int mejor= 0;
		for (int i= 0; i < resultados.length; i++) {
			if(resultados[mejor].compareTo(resultados[i]) < 0) mejor= i;
		}
		return mejor;
	}
}
