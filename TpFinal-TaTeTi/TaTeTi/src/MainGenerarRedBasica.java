import modelo.Constantes;
import modelo.red_neuronal.RedNeuronalTaTeTi;

public class MainGenerarRedBasica {

	private static final int RONDAS = 9000;
	
	public static void main(String[] args) {
		RedNeuronalTaTeTi red = new RedNeuronalTaTeTi();
		red.iniciarRedNeuronal();
		for (int ronda = 0; ronda < RONDAS; ronda++){
			System.out.println(ronda);
			//int posicion = (int) (Math.random() * 9);
			int posicion = ronda % 9;
			double[][] entrada = new double[1][27];
			for (int i = 18; i < 27; i++)
				entrada[0][i] = 1.0 / 8;
			entrada[0][18 + posicion] = 0;
			for(int i = 0; i < 9; i++){
				if (i != posicion)
					//-1, 0 o !
					//entrada[0][i] = (int) (Math.random() * 3) -1;
					entrada[0][i] = 0;
				else
					//-1 o 1
					//entrada[0][i] = 2 * (int) (Math.random() * 2) - 1;
					entrada[0][i] = 1;
			}
			for (int i = 0; i < 27; i++)
				System.out.print(entrada[0][i] + " ");
			System.out.print("\n");
			red.entrenar(entrada);
			entrada[0][posicion] = 0;
			entrada[0][9 + posicion] = 1;
			for (int i = 0; i < 27; i++)
				System.out.print(entrada[0][i] + " ");
			System.out.print("\n");
			red.entrenar(entrada);
		}
		red.salvarRedNeuronal(Constantes.ARCH_RN_TATETI);
	}

}
