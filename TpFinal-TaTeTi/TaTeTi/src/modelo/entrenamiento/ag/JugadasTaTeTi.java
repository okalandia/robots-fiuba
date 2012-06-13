package modelo.entrenamiento.ag;

import modelo.Constantes;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class JugadasTaTeTi {

	private Configuration configuracion;
	private FitnessFunction myFunc;
	private Genotype poblacion;
	
	public JugadasTaTeTi() {
		configuracion= new DefaultConfiguration();
    myFunc= new FuncionAptitudTaTeTi();
	}

	public void cargar() throws InvalidConfigurationException {	
		configuracion.setFitnessFunction(myFunc);
    //Creamos una codificacion de 9 genes que nos servira para nuestros individuos (fenotipo)
    //Valores Cruz-Circulo 
    	
    Gene[] gen= new Gene[5];
    gen[0]= new IntegerGene(configuracion, 0, 8);
    gen[1]= new IntegerGene(configuracion, 0, 8);
    gen[2]= new IntegerGene(configuracion, 0, 8);
    gen[3]= new IntegerGene(configuracion, 0, 8);
    gen[4]= new IntegerGene(configuracion, 0, 8);
   
    //Creamos un individuo con los genes anterior
    Chromosome cromosomaTaTeTi= new Chromosome(configuracion, gen); 
    //Un ejemplo de como seran los individuos
    configuracion.setSampleChromosome(cromosomaTaTeTi);
    //Creamos nuestra poblacion inicial
    configuracion.setPopulationSize(Constantes.POBLACION_INICIAL);
    //Creamos el genotipo de la poblacion
    //Recordemos que el genotipo se determina del fenotipo = la configuracion de los genes para un cromosoma particular
    poblacion= Genotype.randomInitialGenotype(configuracion);
	}
	
	public void evolucionar() {
    //Comienza a iterar el algoritmo
    for(int m = 0 ; m < Constantes.GENERACIONES ; m++) {
    	poblacion.evolve();
    	System.out.println("Iteracion #"+m);
    	IChromosome mejorIndividuo= poblacion.getFittestChromosome(); //Obtenemos el mejor individuo para esta generacion
    	System.out.println("Mejor Individuo de la generacion "+m+" :");
    	Gene[] genes= mejorIndividuo.getGenes();
    	for (int i = 0; i < genes.length; i++) {
				System.out.println("Gene " + i + ": " + genes[i].getAllele());
			}
    	System.out.println("Valor de aptitud obtenido:"+mejorIndividuo.getFitnessValue());
		}
    //Al final de las iteraciones, obtenemos el mejor individuo,
    IChromosome bestSolutionSoFar = poblacion.getFittestChromosome(); //mejor individuo obtenido
    System.out.println("Este es el mejor individuo encontrado para el tateti:");
    System.out.println("Valor de aptitud obtenido:"+bestSolutionSoFar.getFitnessValue()); //Mostramos el valor obtenido en la función de aptitud para el mejor individuo
	}
}
