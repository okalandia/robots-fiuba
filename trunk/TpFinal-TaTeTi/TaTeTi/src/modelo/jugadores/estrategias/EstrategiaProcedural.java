package modelo.jugadores.estrategias;

import modelo.Ficha;
import modelo.Tablero;

//Sólo valida para 3*3
public class EstrategiaProcedural extends EstrategiaComputadora {
	
	private static final int[][] posiciones = {{0,1,2},{3,4,5},{6,7,8}};
	private Ficha[] tableroAnterior;
	private int movimientoARealizar;
	private Ficha ficha;
	private int respuestaAnterior;
	private boolean partidaGanada;
	
	public EstrategiaProcedural(Ficha ficha){
		tableroAnterior = new Ficha[9];
		for (int i = 0; i < 9; i++)
			tableroAnterior[i] = Ficha.VACIO;
		this.ficha = ficha;
	}
	
	public int generarJugada(Tablero tablero) {
		int turno = obtenerTurno(tablero);
		int respuesta = 0;
		if (turno == 1)
			respuesta = posiciones[1][1];
		else if (turno == 3)
			respuesta = obtenerEsquinaAlejada(obtenerMovimientoRival(tablero));
		else if (turno == 5){
			if (hayJaque(tablero))
				respuesta = movimientoARealizar;
			else
				//Si se llega aca y no hay jaque, se puede asegurar victoria en turno 7 
				respuesta = asegurarVictoriaEnTurno7(tablero);
		}
		else if (turno == 7){
			if (hayJaque(tablero))
				respuesta = movimientoARealizar;
			else
				//Condenado al empate
				respuesta = obtenerBlanco(tablero);
		}
		else if (turno == 9){
			respuesta = obtenerBlanco(tablero);
		}
		else if (turno == 2){
			int movimientoRival = obtenerMovimientoRival(tablero);
			if (movimientoRival == posiciones[1][1])
				respuesta = posiciones[0][0];
			else
				//No se analizo la posibilidad de que el rival haga la primer jugada en un borde
				respuesta = posiciones[1][1];
		}
		else if (turno == 4){
			if (hayJaque(tablero))
				respuesta = movimientoARealizar;
			else if (diagonalCompleta(tablero)){
				if (tablero.getCasillero(posiciones[1][1]) != ficha)
					respuesta = obtenerEsquinaVacia(tablero);
				else 
					respuesta = posiciones[0][1];
			}
			else
				//Estrategia no analizada
				respuesta = obtenerBlanco(tablero);
		}
		else{
			//No analizado en profundidad
			if (hayJaque(tablero))
				respuesta = movimientoARealizar;
			else
				respuesta = obtenerBlanco(tablero);
		}
		guardarTableroAnterior(tablero, respuesta); 
		return respuesta;
	}
	
	private int obtenerTurno(Tablero tablero){
		int turno = 1;
		for (int i = 0; i < 9; i++){
			if (tablero.getCasillero(i) != Ficha.VACIO){
				turno++;
			}
		}
		return turno;
	}
	
	private int obtenerMovimientoRival(Tablero tablero) {
		int i = 0;
		while (tablero.getCasillero(i) == tableroAnterior[i]){
			i++;
		}
		return i;
	}
	
	private boolean esEsquina(int posicion){
		int i = posicion / 3;
		int j = posicion % 3;
		return (i != 1 && j != 1);		
	}
	
	private int obtenerEsquinaAlejada(int posicion){
		int i = posicion / 3;
		int j = posicion % 3;
		int ires = 0;
		int jres = 0;
		if (i == 0){
			ires = 2;
		}
		if (j == 0){
			jres = 2;
		}
		return posiciones[ires][jres];
	}
	
	//JAQUE: Puedo ganar en este turno o el rival puede hacerlo en el proximo
	private boolean hayJaque(Tablero tablero){
		partidaGanada = false;
		boolean hayJaque = false;
		for(int i = 0; i < 3; i++){
			if (hayJaqueEnLinea(tablero, posiciones[i][0], posiciones[i][1], posiciones[i][2])){
				if (partidaGanada)
					return true;
				hayJaque = true;
			}
			if (hayJaqueEnLinea(tablero, posiciones[0][i], posiciones[1][i], posiciones[2][i])){
				if (partidaGanada)
					return true;
				hayJaque = true;
			}
		}
		if (hayJaqueEnLinea(tablero, posiciones[0][0], posiciones[1][1], posiciones[2][2])){
			if (partidaGanada)
				return true;
			hayJaque = true;
		}
		if (hayJaqueEnLinea(tablero, posiciones[0][2], posiciones[1][1], posiciones[2][0]))
			hayJaque = true;
		return hayJaque;
	}

	private boolean hayJaqueEnLinea(Tablero tablero, int p1, int p2, int p3){
		int[] linea = {p1,p2,p3};
		for (int i = 0; i < 3; i++){
			Ficha f1 = tablero.getCasillero(linea[i]);
			Ficha f2 = tablero.getCasillero(linea[(i + 1) % 3]);	
			Ficha f3 = tablero.getCasillero(linea[(i + 2) % 3]);
			if (f1 == Ficha.VACIO && f2 == f3 && f2 != Ficha.VACIO){
				movimientoARealizar = linea[i];
				if (f2 == ficha)
					partidaGanada = true;
				return true;
			}
		}
		return false;
	}
	
	/*
	 *   |X|O          |X|O 
	 *   |O|    --->   |O|O 
	 *  X| |          X| | 
	 */
	private int asegurarVictoriaEnTurno7(Tablero tablero){
		int i = respuestaAnterior / 3;
		int j = respuestaAnterior % 3;
		int respuesta;
		if (tablero.getCasillero(posiciones[i][1]) == Ficha.VACIO)
			respuesta = posiciones[i][1];
		else
			respuesta = posiciones[1][j];
		return respuesta;
	}
	
	private int obtenerBlanco(Tablero tablero){
		for (int i = 0; i < 9; i++){
			if (tablero.getCasillero(i) == Ficha.VACIO){
				return i;
			}
		}
		return 9;
	}
	
	private boolean diagonalCompleta(Tablero tablero){
		if (tablero.getCasillero(posiciones[0][0]) != Ficha.VACIO && tablero.getCasillero(posiciones[1][1]) != Ficha.VACIO	&& tablero.getCasillero(posiciones[2][2]) != Ficha.VACIO)
			return true;
		if (tablero.getCasillero(posiciones[0][2]) != Ficha.VACIO && tablero.getCasillero(posiciones[1][1]) != Ficha.VACIO	&& tablero.getCasillero(posiciones[2][0]) != Ficha.VACIO)
			return true;
		return false;
	}
	
	private int obtenerEsquinaVacia(Tablero tablero){
		if (tablero.getCasillero(posiciones[0][0]) != Ficha.VACIO)
			return posiciones[0][2];
		return posiciones[0][0];
	}
	
	private void guardarTableroAnterior(Tablero tablero, int respuesta){
		for (int i = 0; i < 9; i++){
			tableroAnterior[i] = tablero.getCasillero(i);
		}
		tableroAnterior[respuesta] = ficha; 
		respuestaAnterior = respuesta;
	}
	
}
