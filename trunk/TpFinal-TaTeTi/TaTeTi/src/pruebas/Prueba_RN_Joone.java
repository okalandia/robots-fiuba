package pruebas;

import static org.junit.Assert.*;

import modelo.jugadores.estrategias.red_neuronal.RedNeuronalTaTeTi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Prueba_RN_Joone {

	private RedNeuronalTaTeTi RN_TaTeTi;
	
	private static String INPUT_FILE= "data/Archivo-Entrenamiento-NN.txt";
	private static String SAVE_RN= "data/RN.ser";
	private static String TRAINING_FILE= "data/Prueba.txt";
  
	@Before
	public void setUp() throws Exception {
		RN_TaTeTi= new RedNeuronalTaTeTi();
	}

	@After
	public void tearDown() throws Exception {
		RN_TaTeTi= null;
	}

	@Test
	public void todoRN() {
		RN_TaTeTi.inicialRedNeuronal(INPUT_FILE);
		RN_TaTeTi.entrenar(INPUT_FILE);
		RN_TaTeTi.preguntar(TRAINING_FILE);
	}
	
	@Test
	public void guardarRN() {
		RN_TaTeTi.inicialRedNeuronal(INPUT_FILE);
		RN_TaTeTi.entrenar(INPUT_FILE);
		boolean guardado= RN_TaTeTi.salvarRedNeuronal(SAVE_RN);
		assertTrue(guardado);
	}
	
	@Test
	public void recuperarRN() {
		boolean recuperado= RN_TaTeTi.restaurarRedNeuronal(SAVE_RN);
		assertTrue(recuperado);
	}
	
	@Test
	public void preguntarRN() {
		if(RN_TaTeTi.restaurarRedNeuronal(SAVE_RN)) {
			RN_TaTeTi.preguntar(TRAINING_FILE);
		}
	}
} 
