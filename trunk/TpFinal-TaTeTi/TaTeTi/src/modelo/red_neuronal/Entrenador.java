package modelo.red_neuronal;

import java.io.File;

import modelo.Constantes;

public class Entrenador {

	private RedNeuronalTaTeTi RN_TaTeTi;
	private int nro_jugada;
	private int[] jugadas;
	int orden_fichas[];

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
		int posicion, valor = 0;
		for(int turno= 0; turno < 9; turno++) {
			if(jugadas[turno] != -1) {				
				posicion= jugadas[turno];
				if (gano)
					guardar[0][18 + posicion] = 1.0;
				else{
					//Si perdi distribuyo la probabilidad entre las demas casillas libres
					double puntaje = 1.0 / (9 - (turno + 1));
					for (int i = 0; i < 9; i++)
						if (guardar[0][i] == 0 && guardar[0][9+i] == 0 && i != posicion)
							guardar[0][18+i] = puntaje;
				}
				boolean miTurno = (turnoInicialRN == 0) && ((turno % 2) == 0) || (turnoInicialRN == 1) && ((turno % 2) == 1);
				int movimientoRival = 0;
				if (miTurno){
					imprimir(guardar);
					RN_TaTeTi.entrenar(guardar);					
				}
				else
					movimientoRival = 9;
				guardar[0][movimientoRival + posicion] = 1.0;
				for (int i = 18; i < 27; i++)
					guardar[0][i] = 0;
				/*valor= obtenerValor(turnoInicialRN, turno);
				if(valor == 1) {
					guardar[0][18 + posicion]= generarPuntaje(gano);
					RN_TaTeTi.entrenar(guardar);
					imprimir(guardar);
					System.out.println("GUARDO");
					guardar[0][posicion] = 0;
					guardar[0][9+posicion]= 0;
				} else {
					guardar[0][posicion]= valor;
				 imprimir(guardar);
				}*/
			}		
		}
	}
	
	private void entrenar2(int turnoInicialRN, boolean gano) {
		orden_fichas= inicializarFichas(turnoInicialRN);
		if(!gano){
 			corregirParGanador();
		}
	}
	
	private void corregirParGanador() {
		//Pares peligrosos en Filas
		System.out.println("----------------Fila1");
		corregir(0, 1, 2);
		System.out.println("----------------Fila2");
		corregir(3, 4, 5);
		System.out.println("----------------Fila3");
		corregir(6, 7, 8);
		//Pares peligrosos en Columnas
		System.out.println("----------------Columna1");
		corregir(0, 3, 6);
		System.out.println("----------------Columna2");
		corregir(1, 4, 7);
		System.out.println("----------------Columna3");
		corregir(2, 5, 8);
		//Pares peligrosos en Diagonales
		System.out.println("----------------Diagonal1");
		corregir(0, 4, 8);
		System.out.println("----------------Diagonal2");
		corregir(2, 4, 6);
	}

	private int[] inicializarFichas(int turnoInicialRN) {
		int fichas[]= new int[9];
		for(int i= 0; i < fichas.length; i++) {
			fichas[i]= -1;
			if(((turnoInicialRN + i) % 2) == 0)
				fichas[i]= 1;
		}
		return fichas;
	}
	
	//TODO: Situacion que no contempla pares peligrosos que ya no sirve para
	//ganar, por ejemplo si en la fila 1 habia una cruz y luego se jugaron
	//2 circulos.
	private void corregir(int pos0, int pos1, int pos2) {
		double[][] guardar= inicializarJugada();
		boolean lugar0, lugar1, lugar2, aprendio;
		lugar0= false;
		lugar1= false;
		lugar2= false;
		aprendio= false;
		for(int i= 0; i < jugadas.length; i++) {
			System.out.println("Jugada nro: " + i + " Posicion: " + jugadas[i]);
			if(jugadas[i] != -1) {			
  			if((lugar0 && lugar1) || (lugar0 && lugar2) || (lugar1 && lugar2)) {
  				int lugar= pos0;
  				if(lugar0 && lugar1)
  					lugar= pos2;
  				else if(lugar0 && lugar2)
  					lugar= pos1;
  			  if(jugadas[i] != lugar) {
  			  	// castigo sino evito el ta-te-ti
    				guardar[0][9+jugadas[i]]= -0.1;
    				RN_TaTeTi.entrenar(guardar);
    				imprimir(guardar);
    				// la proxima debe evitar el ta-te-ti
  					guardar[0][9+jugadas[i]]= 0;
  					guardar[0][9+lugar]= 0.25; 
  					RN_TaTeTi.entrenar(guardar);
  					imprimir(guardar);
  					aprendio= true;
  			  }
  			}
  			if((pos0 == jugadas[i]) && (orden_fichas[i] == -1))
  				lugar0= true;
  			else if(pos1 == jugadas[i] && (orden_fichas[i] == -1))
  				lugar1= true;
  			else if(pos2 == jugadas[i] && (orden_fichas[i] == -1))
  				lugar2= true;
  			guardar[0][jugadas[i]]= orden_fichas[i];
			}
			if(aprendio)
				break;
		}
	}
	
	private void imprimir(double[][] guardar) {
		for (int j = 0; j < 27; j++) {
			System.out.print(guardar[0][j] + " ");
			if(j==8 || j == 17)
				System.out.print(" | ");
	}
		System.out.println();
	}

	private double generarPuntaje(boolean gano) {
		if(gano)
			return 1.0;
		return 0.0;
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
		if(turnoActual % 2 == 0)
			valor= valorPar;
		else
			valor= valorImpar;
		return valor;
	}

	private double[][] inicializarJugada() {
		double[][] guardar= new double[1][27];
		for(int i = 0; i < 27; i++)
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
