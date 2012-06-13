package modelo;

import modelo.excepciones.JugadaInvalida;

public class Tablero {

	private Ficha casilleros[];
	private int ancho;
	
	public void crearTablero(int ancho) {
		this.ancho= ancho;
		casilleros = new Ficha[getCantidadCasilleros()];
		for(int i = 0; i < getCantidadCasilleros(); i++)
			casilleros[i] = Ficha.VACIO;
	}

	private boolean estaOcupado(int posicion){
		return (casilleros[posicion] != Ficha.VACIO);
	}
	
	public void agregarFicha(int posicion, Ficha ficha) throws JugadaInvalida {
		if(posicion < 0 || posicion > getCantidadCasilleros())
			throw new JugadaInvalida("La posicion no es valida.");
		if(estaOcupado(posicion))
			throw new JugadaInvalida("La posicion se encuentra ocupada.");
		casilleros[posicion]= ficha;
	}
	
	public Ficha getCasillero(int posicion){
		return casilleros[posicion];
	}
	
	public int getCantidadCasilleros() {
		return ancho*ancho;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public boolean tableroCompleto() {
		for(int i = 0; i < getCantidadCasilleros(); i++)
			if(casilleros[i] == Ficha.VACIO)
				return false;
		return true;
	}
	
	public void limpiarTablero() {
		for(int i = 0; i < getCantidadCasilleros(); i++)
			casilleros[i]= Ficha.VACIO;
	}
}
