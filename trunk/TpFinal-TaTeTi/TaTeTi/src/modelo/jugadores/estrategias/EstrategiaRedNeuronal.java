package modelo.jugadores.estrategias;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public EstrategiaRedNeuronal(Ficha ficha, RedNeuronalTaTeTi redNeuronal) {
		rn= redNeuronal;
		this.ficha= ficha;
	}
	
	@Override
	public int generarJugada(Tablero tablero) {
		double[][] tabl= new double[1][18];
		for (int i = 0; i < 9; i++) {
			//double valor= 0.0;
			//if(tablero.getCasillero(i) != Ficha.VACIO) {
			//	if((tablero.getCasillero(i).compareTo(ficha)) == 0) valor= 1.0;
			//	else valor= -1.0;
			//}
			//tabl[0][i]= valor;
			tabl[0][i] = 0;
			tabl[0][i + 9] = 0;
			if ((tablero.getCasillero(i).compareTo(ficha)) == 0)
				tabl[0][i] = 1;
			else if ((tablero.getCasillero(i).compareTo(Ficha.VACIO)) != 0)
				tabl[0][i + 9] = 1;
					
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
		for(int i = 0; i < tableroIngresado[0].length; i++) {
			if(tableroAnterior[0][i] != tableroIngresado[0][i]) {
				iguales= false;
				break;
			}
		}
		return iguales;
	}

	private void cargarJugadas(BigDecimal[] resultados) {
		crearMap(resultados);
		ordenaryCargarJugadasEnVector();
	}
	
	private void crearMap(BigDecimal[] resultados) {
		jugadas= new LinkedHashMap<Integer, BigDecimal>(9);
		for (int i = 0; i < resultados.length; i++)
			jugadas.put(new Integer(i), resultados[i]);		
	}

	private void ordenaryCargarJugadasEnVector() {
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
		cargarEnVector(newMap);	
	}

	private void cargarEnVector(LinkedHashMap<Integer, BigDecimal> newMap) {
		Iterator<Entry<Integer, BigDecimal>> ite= newMap.entrySet().iterator();
		jugadasA= new int[9];
		int i= 8;
		while (ite.hasNext()) {
			Map.Entry<Integer, BigDecimal> e= (Map.Entry<Integer, BigDecimal>)ite.next();
			jugadasA[i]= (Integer) e.getKey();
			i--;
		}			
	}
}
