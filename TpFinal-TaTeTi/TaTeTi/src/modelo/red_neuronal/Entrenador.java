package modelo.red_neuronal;

import java.io.File;
import java.util.Arrays;

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
		File fichero= new File(Constantes.ARCH_RN_TATETI);
		if(!fichero.exists()) {
			System.out.println("Archivo no existe - Sin restauracion");
			RN_TaTeTi.iniciarRedNeuronal();
			RN_TaTeTi.salvarRedNeuronal(Constantes.ARCH_RN_TATETI);
		} else
			if(RN_TaTeTi.restaurarRedNeuronal(Constantes.ARCH_RN_TATETI))
				System.out.println("Restauracion Correcta");
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
					imprimir(guardar);
					System.out.println("GUARDO");
					guardar[0][posicion] = 0;
					guardar[0][9+posicion]= 0;
				} else {
					guardar[0][posicion]= valor;
				 imprimir(guardar);
				}
			}		
		}
	}
	
	private void entrenar2(int turnoInicialRN, boolean gano) {
		double[][] guardar= inicializarJugada();
		int posicion, valor, turno= 0;
  		if(gano) {
    		for(int i= 0; i < 9; i++) {
    			if(jugadas[i] != -1) {
    				turno= i;
    				posicion= jugadas[turno];
    				valor= obtenerValor(turnoInicialRN, turno);
    				if(valor == 1) {
    					guardar[0][9+posicion]= 0.5;
    					RN_TaTeTi.entrenar(guardar);
    					imprimir(guardar);
    					System.out.println("GUARDO");
    					guardar[0][posicion] = 1;
    					guardar[0][9+posicion]= 0;
    				} else {
    					guardar[0][posicion]= valor;
    				 imprimir(guardar);
    				}
    			}		
    		}
  		} else {
  			System.out.print("Jugadas Peligrosas");
  			obtenerParesPeligrosos(turnoInicialRN);
		}
	}
	
	private void obtenerParesPeligrosos(int turnoInicialRN) {
		int inicio;
		if(turnoInicialRN == 0) inicio= 1;
		else inicio= 0;
		Arrays.sort(jugadas);
		//Verifico si se jugo o no una ficha ahi
		int pos[]= inicializarPosiciones();
		for(int i= inicio; i < pos.length; i+=2) {
			pos[i]= Arrays.binarySearch(jugadas, i);
		}
		//Pares peligrosos en Filas
		controlarFila(pos[0], pos[1], pos[2], 0);
		controlarFila(pos[3], pos[4], pos[5], 3);
		controlarFila(pos[6], pos[7], pos[8], 6);
		//Pares peligrosos en Columnas
		controlarColumna(pos[0], pos[3], pos[6], 0);
		controlarColumna(pos[1], pos[4], pos[7], 1);
		controlarColumna(pos[2], pos[5], pos[8], 2);
		//Pares peligrosos en Diagonales
		controlarDiagonal1(pos[0], pos[4], pos[8], 0);
		controlarDiagonal2(pos[2], pos[4], pos[6], 2);
	}

	private int[] inicializarPosiciones() {
		int pos[]= new int[9];
		for(int i= 0; i < pos.length; i++) 
			pos[i]= -1;
		return pos;
	}

	private void controlarDiagonal2(int pos0, int pos1, int pos2, int posInicial) {
		double[][] guardar;
		if((pos0!=-1) && (pos1!=-1) && (pos2!=-1)) {
			if((pos0!=-1) && (pos1!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicial]= -1;
				guardar[0][posInicial+2]= -1;	
				guardar[0][9+posInicial+4]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else if ((pos0!=-1) && (pos2!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicial]= -1;
				guardar[0][posInicial+4]= -1;	
				guardar[0][9+posInicial+2]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else {
				guardar= inicializarJugada();
				guardar[0][posInicial+2]= -1;
				guardar[0][posInicial+4]= -1;	
				guardar[0][9+posInicial]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			}
		}
	}

	private void controlarDiagonal1(int pos0, int pos1, int pos2, int posInicial) {
		double[][] guardar;
		if((pos0!=-1) && (pos1!=-1) && (pos2!=-1)) {
			if((pos0!=-1) && (pos1!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicial]= -1;
				guardar[0][posInicial+4]= -1;	
				guardar[0][9+posInicial+8]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else if ((pos0!=-1) && (pos2!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicial]= -1;
				guardar[0][posInicial+8]= -1;	
				guardar[0][9+posInicial+4]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else {
				guardar= inicializarJugada();
				guardar[0][posInicial+4]= -1;
				guardar[0][posInicial+8]= -1;	
				guardar[0][9+posInicial]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			}
		}
	}

	private void controlarColumna(int pos0, int pos1, int pos2, int posInicialColumna) {
		double[][] guardar;
		if((pos0!=-1) && (pos1!=-1) && (pos2!=-1)) {
			if((pos0!=-1) && (pos1!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicialColumna]= -1;
				guardar[0][posInicialColumna+3]= -1;	
				guardar[0][9+posInicialColumna+6]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else if ((pos0!=-1) && (pos2!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicialColumna]= -1;
				guardar[0][posInicialColumna+6]= -1;	
				guardar[0][9+posInicialColumna+3]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else {
				guardar= inicializarJugada();
				guardar[0][posInicialColumna+3]= -1;
				guardar[0][posInicialColumna+6]= -1;	
				guardar[0][9+posInicialColumna]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			}
		}
	}

	private void controlarFila(int pos0, int pos1, int pos2, int posInicialFila) {
		double[][] guardar;
		if((pos0!=-1) && (pos1!=-1) && (pos2!=-1)) {
			if((pos0!=-1) && (pos1!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicialFila]= -1;
				guardar[0][posInicialFila+1]= -1;	
				guardar[0][9+posInicialFila+2]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else if ((pos0!=-1) && (pos2!=-1)) {
				guardar= inicializarJugada();
				guardar[0][posInicialFila]= -1;
				guardar[0][posInicialFila+2]= -1;	
				guardar[0][9+posInicialFila+1]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			} else {
				guardar= inicializarJugada();
				guardar[0][posInicialFila+1]= -1;
				guardar[0][posInicialFila+2]= -1;	
				guardar[0][9+posInicialFila]= 0.5;
				RN_TaTeTi.entrenar(guardar);
				imprimir(guardar);
			}
		}
	}

	private void imprimir(double[][] guardar) {
		for (int j = 0; j < 18; j++) {
			System.out.print(guardar[0][j] + " ");
			if(j==8)
				System.out.print(" | ");
	}
		System.out.println();
	}

	private double generarPuntaje(boolean gano) {
		if(gano)
			return 0.5;
		return 0.01;
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
	
	public RedNeuronalTaTeTi getRedNeuronal() {
		return RN_TaTeTi;
	}
}
