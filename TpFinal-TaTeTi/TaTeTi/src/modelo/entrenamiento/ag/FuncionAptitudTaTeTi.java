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
		
		return fitness;
	}
}
