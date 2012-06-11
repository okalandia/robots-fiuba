package modelo.entrenamiento;

import modelo.entrenamiento.ag.ConstantesAG;
import modelo.entrenamiento.ag.FuncionAptitudTaTeTi;
import modelo.entrenamiento.ag.GeneTaTeTi;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;

public class Entrenador {

	private Configuration configuracion;
	private FitnessFunction myFunc;
	private Genotype poblacion;
	private boolean cargaSatisfactoria;
	
	public Entrenador() {
		//Configuramos JGAP por Default
		configuracion= new DefaultConfiguration();
    myFunc= new FuncionAptitudTaTeTi();
		cargaSatisfactoria= false;
	}
	
	public void cargar() {	
		//Algoritmo genético
    try {
    	configuracion.setFitnessFunction(myFunc);
      //Creamos una codificacion de 9 genes que nos servira para nuestros individuos (fenotipo)
      //Valores Cruz-Circulo 
    	Gene[] gen= new Gene[9];
      gen[0] = new GeneTaTeTi(configuracion);
      gen[1] = new GeneTaTeTi(configuracion);
      gen[2] = new GeneTaTeTi(configuracion);
      gen[3] = new GeneTaTeTi(configuracion);
      gen[4] = new GeneTaTeTi(configuracion);
      gen[5] = new GeneTaTeTi(configuracion);
      gen[6] = new GeneTaTeTi(configuracion);
      gen[7] = new GeneTaTeTi(configuracion);
      gen[8] = new GeneTaTeTi(configuracion);
       
      //Creamos un individuo con los genes anterior
      Chromosome cromosomaTaTeTi= new Chromosome(configuracion, gen); 
      //Un ejemplo de como seran los individuos
      configuracion.setSampleChromosome(cromosomaTaTeTi);
      //Creamos nuestra poblacion inicial
      configuracion.setPopulationSize(ConstantesAG.POBLACION_INICIAL);
      //Creamos el genotipo de la poblacion
      //Recordemos que el genotipo se determina del fenotipo = la configuracion de los genes para un cromosoma particular
      poblacion= Genotype.randomInitialGenotype(configuracion);
      cargaSatisfactoria= true;
    } catch (InvalidConfigurationException e) {
    	System.out.println("No se pudo ejecutar el AG");
    	cargaSatisfactoria= false;
		}
	}
	
	public void evolucionar() {
    //Comienza a iterar el algoritmo
    for(int m = 0 ; m < ConstantesAG.ITERACIONES ; m++) {
    	poblacion.evolve();
      System.out.println("Iteracion #"+m);
      IChromosome mejor_individuo = poblacion.getFittestChromosome(); //Obtenemos el mejor individuo para esta generacion
      System.out.println("Mejor Individuo de la generacion "+m+" :");
      System.out.println("Valor de aptitud obtenido:"+mejor_individuo.getFitnessValue());
    }

    //Al final de las iteraciones, obtenemos el mejor individuo,
    IChromosome bestSolutionSoFar = poblacion.getFittestChromosome(); //mejor individuo obtenido
    System.out.println("Este es el mejor individuo encontrado para el cuadrado magico de 3x3 despues de 50 generaciones:");
    System.out.println("Valor de aptitud obtenido:"+bestSolutionSoFar.getFitnessValue()); //Mostramos el valor obtenido en la función de aptitud para el mejor individuo
	}
	
	public boolean esCargaSatisfactoria() {
		return cargaSatisfactoria;
	}
}
