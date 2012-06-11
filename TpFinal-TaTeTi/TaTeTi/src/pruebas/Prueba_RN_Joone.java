package pruebas;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import modelo.red_neuronal.RedNeuronalTaTeTi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Prueba_RN_Joone {

	private RedNeuronalTaTeTi RN_TaTeTi;
	
	private static String INPUT_FILE= "data/Archivo-Entrenamiento-Prueba.txt";
	private static String SAVE_RN= "data/RN.snet";
  
	private static double[][] inputArray= new double[][] {
			{0.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0}  	
	};
	
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
		RN_TaTeTi.iniciarRedNeuronal();
		RN_TaTeTi.entrenar(INPUT_FILE);
		RN_TaTeTi.preguntar(inputArray);
	}
	
	@Test
	public void guardarRN() {
		RN_TaTeTi.iniciarRedNeuronal();
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
			BigDecimal[] resultados= RN_TaTeTi.preguntar(inputArray);
			int mejor= mejorPosicion(resultados);
			assertEquals(3, mejor);
		}
	}
	
	private int mejorPosicion(BigDecimal[] resultados) {
		int mejor= 0;
		for (int i = 0; i < resultados.length; i++) {
			if(resultados[mejor].compareTo(resultados[i]) < 0) mejor= i;
		}
		return mejor;
	}
} 
