package modelo.jugadores.estrategias;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import modelo.Constantes;
import modelo.Ficha;
import modelo.Tablero;
import modelo.red_neuronal.RedNeuronalTaTeTi;

public class EstrategiaRedNeuronal extends EstrategiaComputadora {

	private RedNeuronalTaTeTi rn;
	private Ficha ficha;
	private double[][] tableroAnterior;
	private LinkedHashMap<Integer,BigDecimal> jugadas;
	private int posicion;
	private int[] jugadasA;
	
	public EstrategiaRedNeuronal(Ficha ficha) {
		rn= new RedNeuronalTaTeTi();
		rn.restaurarRedNeuronal(Constantes.ARCH_RN_TATETI);
		this.ficha= ficha;
	}
	
	@Override
	public int generarJugada(Tablero tablero) {
		double[][] tabl= new double[1][9];
		double valor= 0.0;
		for (int i = 0; i < 9; i++) {
			if(tablero.getCasillero(i) != Ficha.VACIO) {
				if((tablero.getCasillero(i).compareTo(ficha)) == 0) valor= 1.0;
				else valor= -1.0;
			}
			tabl[0][i]= valor;
		}	
		if(!esTableroAnterior(tabl)) {
			BigDecimal[] resultados= rn.preguntar(tabl);
			cargarJugadas(resultados);
			tableroAnterior= tabl;
			posicion= 0;
		} else posicion++;
		return jugadasA[posicion];
	}

	private boolean esTableroAnterior(double[][] tableroIngresado) {
		if(tableroAnterior == null) return false;
		boolean iguales= true;
		for(int i = 0; i < tableroIngresado.length; i++) {
			if(tableroAnterior[0][i] != tableroIngresado[0][i]) {
				iguales= false;
				break;
			}
		}
		return iguales;
	}

	private void cargarJugadas(BigDecimal[] resultados) {
		crearMap(resultados);
		ordenarJugadas();
		cargarJugadasEnVector();
	}
	
	private void crearMap(BigDecimal[] resultados) {
		jugadas= new LinkedHashMap<Integer, BigDecimal>(9);
		for (int i = 0; i < resultados.length; i++)
			jugadas.put(new Integer(i), resultados[i]);		
	}

	private void ordenarJugadas() {
		LinkedHashMap<Integer,BigDecimal> newMap= new LinkedHashMap<Integer,BigDecimal>();
		ArrayList<BigDecimal> values= new ArrayList<BigDecimal>(jugadas.values());
		Collections.sort(values);
		Iterator<BigDecimal> it= values.iterator();
		BigDecimal tmp= BigDecimal.ZERO;
		while(it.hasNext()) {
			tmp = it.next();
			for(Map.Entry<Integer,BigDecimal> k : jugadas.entrySet()) {
				if(tmp==k.getValue()) newMap.put(k.getKey(), k.getValue());
			}
		}
	}
	
	private void cargarJugadasEnVector() {
		jugadasA= new int[9];
		ArrayList<Integer> values= new ArrayList<Integer>(jugadas.keySet());
		Iterator<Integer> it= values.iterator();
		int i= 0;
		for (it= values.iterator(); it.hasNext(); i++) {
			jugadasA[i]= (it.next()).intValue();
		}
	}
}
