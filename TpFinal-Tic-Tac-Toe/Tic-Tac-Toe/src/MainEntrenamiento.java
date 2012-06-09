import modelo.entrenamiento.Entrenador;


public class MainEntrenamiento {

	public static void main(String[] args) {
		Entrenador entrenador= new Entrenador();
		entrenador.cargar();
		entrenador.evolucionar();
	}

}
