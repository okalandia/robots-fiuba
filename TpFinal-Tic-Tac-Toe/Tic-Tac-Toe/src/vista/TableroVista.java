package vista;

import java.util.Iterator;

public class TableroVista {

	private int cant_casilleros;
	
	public TableroVista(int ancho) {
		cant_casilleros= ancho*ancho;
	}
	
	public void dibujar() {
    String intro= "Las posiciones están marcadas de la siguiente manera:\n";
    for(int i= 0; i < cant_casilleros; i++) {
    	intro += " " + i + " |";
    	intro += " " + ++i + " |";
    	intro += " " + ++i + " \n";
    }
    intro += "\n";
    System.out.println(intro);
	}
	
	public void actualizar(Iterator<String> casilleros) {
    String intro= "";
    while(casilleros.hasNext()) {
			intro += " " + casilleros.next() + " |";
    	intro += " " + casilleros.next() + " |";
    	intro += " " + casilleros.next() + " \n";
    }
    intro += "\n";
    System.out.println(intro);
	}
	
	public void mostrar(String mensaje) {
		System.out.print(mensaje);
	}
}
