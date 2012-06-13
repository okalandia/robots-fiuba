package modelo.entrenamiento.ag;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

@SuppressWarnings("serial")
public class FuncionAptitudTaTeTi extends FitnessFunction {

	private double fitness;

  public FuncionAptitudTaTeTi() {
      fitness= 0;
  }
	
	@Override
	protected double evaluate(IChromosome cromosoma) {
		verificarRepetidos(cromosoma);
		return fitness;
	}

	private void verificarRepetidos(IChromosome cromosoma) {
    Integer c0= (Integer) cromosoma.getGene(0).getAllele();
    Integer c1= (Integer) cromosoma.getGene(1).getAllele();
    Integer c2= (Integer) cromosoma.getGene(2).getAllele();
    Integer c3= (Integer) cromosoma.getGene(3).getAllele();
    Integer c4= (Integer) cromosoma.getGene(4).getAllele();

    if((((c0.compareTo(c1)!=0) && (c0.compareTo(c2)!=0)) && ((c0.compareTo(c3)!=0) && (c0.compareTo(c4)!=0))) 
    		&&
      (((c1.compareTo(c0)!=0) && (c1.compareTo(c2)!=0)) && ((c1.compareTo(c3)!=0) && (c1.compareTo(c4)!=0))) 
        &&
      (((c2.compareTo(c0)!=0) && (c2.compareTo(c1)!=0)) && ((c2.compareTo(c3)!=0) && (c2.compareTo(c4)!=0))) 
        &&
      (((c3.compareTo(c0)!=0) && (c3.compareTo(c1)!=0)) && ((c3.compareTo(c2)!=0) && (c3.compareTo(c4)!=0)))  
        &&
      (((c4.compareTo(c0)!=0) && (c4.compareTo(c1)!=0)) && ((c4.compareTo(c2)!=0) && (c4.compareTo(c3)!=0))))
        fitness=fitness+1;
	}
}
