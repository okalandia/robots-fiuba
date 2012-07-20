package modelo.red_neuronal;

import java.io.File;

import modelo.Constantes;

public class Entrenador {

	private RedNeuronalTaTeTi RN_TaTeTi;
	private int nro_jugada;
	private int[] jugadas;
	
	public Entrenador() {
		RN_TaTeTi= new RedNeuronalTaTeTi();
		jugadas= new int[9];
		nro_jugada= 0;
		reiniciarJugada();
	}
	
	public void reiniciarJugada() {
		nro_jugada= 0;
		for(int i= 0; i < jugadas.length; i++)
			jugadas[i]= -1;
	}
	
	public void agregarPosicionJugada(int lugar) {
		jugadas[nro_jugada]= lugar;
		nro_jugada++;
	}
	
	public void iniciarRN() {
		RN_TaTeTi.iniciarRedNeuronal();
		File fichero= new File(Constantes.ARCH_RN_TATETI);
		if(!fichero.exists()) {
			System.out.println("Archivo no existe");
			RN_TaTeTi.salvarRedNeuronal(Constantes.ARCH_RN_TATETI);
		}	
	}
	
	public void entrenarRedNeuronalConArchivo(String pathInputFile) {
		System.out.println("-- ENTRENANDO RED --");
		RN_TaTeTi.iniciarRedNeuronal();
		//TODO:descomentar
		//RN_TaTeTi.entrenar(pathInputFile);
	}
	
	public void entrenarRedNeuronalConMemoria(int turnoInicialRN, boolean gano) {
		entrenar(turnoInicialRN, gano);
	}

	private void entrenar(int turnoInicialRN, boolean gano) {
		double[][] guardar= inicializarJugada();
		int posicion, valor, turno= 0;
		for(int i= 0; i < 9; i++) {
			if(jugadas[i] != -1) {
				turno= i;
				posicion= jugadas[turno];
				valor= obtenerValor(turnoInicialRN, turno);
				if(valor == 1) {
					guardar[0][9+posicion]= generarPuntaje(gano);
					RN_TaTeTi.entrenar(guardar);
					//System.out.println("A GUARDAR");
					//for (int j= 0; j < 18; j++) {
					//	System.out.print(guardar[0][j] + ",");
					//}
					//System.out.println();
					guardar[0][posicion] = 1;
					guardar[0][9+posicion]= 0;
				} else
					guardar[0][posicion]= valor;
			}
		}
		//Comento porque incentiva a la red a jugar siempre en el lugar donde hubiera bloqueado la victoria rival
		//if(!gano)
		//	reforzar(jugadas[turno], guardar);
	}

	private void reforzar(int posicion, double[][] guardar) {
		guardar[0][posicion]= 0;
		guardar[0][9+posicion]= 10;
		System.out.println("REFORZAR");		
		for (int j= 0; j < 18; j++) {
			System.out.print(guardar[0][j] + ",");
		}
		System.out.println();
		RN_TaTeTi.entrenar(guardar);
	}

	private double generarPuntaje(boolean gano) {
		if(gano)
			return 1.0;
		return -1.0;
	}

	private int obtenerValor(int turnoInicialRN, int turnoActual) {
		int valorPar, valorImpar;
		if(turnoInicialRN == 0) {
			valorPar= 1;
			valorImpar= -1;
		} else {
			valorPar= -1;
			valorImpar= 1;			
		}
		int valor;
		if(turnoActual%2 == 0)
			valor= valorPar;
		else
			valor= valorImpar;
		return valor;
	}

	private double[][] inicializarJugada() {
		double[][] guardar= new double[1][18];
		for(int i = 0; i < 18; i++)
			guardar[0][i]= 0;
		return guardar;
	}
	
	public void salvarRedNeuronal(String pathSafeFile) {
		RN_TaTeTi.salvarRedNeuronal(pathSafeFile);
	}
}
