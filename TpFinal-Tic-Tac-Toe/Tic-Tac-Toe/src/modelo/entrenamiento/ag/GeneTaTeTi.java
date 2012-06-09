package modelo.entrenamiento.ag;

import java.util.Random;

import modelo.Ficha;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

@SuppressWarnings("serial")
public class GeneTaTeTi extends BaseGene implements Gene {

	private Ficha ficha;
	
	public GeneTaTeTi(Configuration arg0) throws InvalidConfigurationException {
		super(arg0);
	}

	@Override
	public int compareTo(Object o) {
    if(o == null ) {
    	return 1;
    }
    
    if(ficha.toString().equals(((Ficha)o).toString())) {
    	return 0;
    } else
    	return -1;
	}

	@Override
	public String getUniqueID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueIDTemplate(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUniqueIDTemplate(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyMutation(int arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {	}

	@Override
	public Object getAllele() {
		return ficha;
	}

	@Override
	public Object getApplicationData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPersistentRepresentation()
			throws UnsupportedOperationException {
		return null;
	}

	@Override
	public boolean isCompareApplicationData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Gene newGene() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAllele(Object arg0) {
		ficha= (Ficha)arg0;
	}

	@Override
	public void setApplicationData(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompareApplicationData(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConstraintChecker(IGeneConstraintChecker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnergy(double arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setToRandomValue(RandomGenerator arg0) {
		int random= new Random().nextInt(2);
		setAllele(Ficha.getIndex(random));
	}

	@Override
	public void setValueFromPersistentRepresentation(String arg0)
			throws UnsupportedOperationException, UnsupportedRepresentationException {	
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	protected Object getInternalValue() {
		return ficha;
	}

	@Override
	protected Gene newGeneInternal() {
		try {
			return new GeneTaTeTi(getConfiguration());
		} catch (InvalidConfigurationException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}
}
