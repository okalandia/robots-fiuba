package modelo.red_neuronal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Scanner;

import modelo.Constantes;
import modelo.logger.LoggerTaTeTi;

import org.apache.log4j.Logger;
import org.joone.engine.FullSynapse;
import org.joone.engine.Layer;
import org.joone.engine.LinearLayer;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.net.NeuralNet;

public class RedNeuronalTaTeTi implements Serializable, NeuralNetListener {

	private static final long serialVersionUID = 1L;

  private static Logger logger;
  private NeuralNet nnet;
  private Layer input;
  private Layer output;
  private TeachingSynapse trainer;
	
	public RedNeuronalTaTeTi() {
		logger= LoggerTaTeTi.getInstancia().getLogger(this);
	}
	
	public void iniciarRedNeuronal() {
    //Layers
		input= new LinearLayer();
    SigmoidLayer hidden1= new SigmoidLayer();
    SigmoidLayer hidden2= new SigmoidLayer();
    SigmoidLayer hidden3= new SigmoidLayer();
    output= new SigmoidLayer();
    input.setLayerName("input");
    hidden1.setLayerName("hidden1");
    hidden2.setLayerName("hidden2");
    hidden3.setLayerName("hidden3");
    output.setLayerName("output");
    //Dimensiones layers
    input.setRows(9);
    hidden1.setRows(27);
    hidden2.setRows(27);
    hidden3.setRows(27);    
    output.setRows(9);
    //Synapses
    FullSynapse synapse_IH= new FullSynapse(); 	/* input   -> hidden11 */
    FullSynapse synapse_HO= new FullSynapse(); 	/* hidden3 -> output  */
    FullSynapse synapse_Int1= new FullSynapse(); /* hidden1 -> hidden2 */
    FullSynapse synapse_Int2= new FullSynapse(); /* hidden2 -> hidden3 */
    synapse_IH.setName("IH");
    synapse_HO.setName("HO");
    synapse_Int1.setName("Int1");
    synapse_Int2.setName("Int2"); 
    //Conexion entre input layer con el hidden layer 1
    input.addOutputSynapse(synapse_IH);
    hidden1.addInputSynapse(synapse_IH);
    //Conexion entre el hidden layer 3 con el output layer
    hidden3.addOutputSynapse(synapse_HO);
    output.addInputSynapse(synapse_HO);
    //Conexion entre el hidden layer 1 con el hidden layer 2
    hidden1.addOutputSynapse(synapse_Int1);
    hidden2.addInputSynapse(synapse_Int1);
    //Conexion entre el hidden layer 2 con el hidden layer 3
    hidden2.addOutputSynapse(synapse_Int2);
    hidden3.addInputSynapse(synapse_Int2); 
    //Entrenador
    trainer= new TeachingSynapse();
    output.addOutputSynapse(trainer);
    //Agregamos esta estrucutra a un objeto Red Neuronal
    //La Red Neuronal
    nnet= new NeuralNet();
    nnet.addLayer(input, NeuralNet.INPUT_LAYER);
    nnet.addLayer(hidden1, NeuralNet.HIDDEN_LAYER);
    nnet.addLayer(hidden2, NeuralNet.HIDDEN_LAYER);
    nnet.addLayer(hidden3, NeuralNet.HIDDEN_LAYER);
    nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
    nnet.setTeacher(trainer); 
    nnet.addNeuralNetListener(this);
	}

	public void entrenar(String pathInputFile) {
		File inputFile= new File(pathInputFile);
    //Archivo Entrada
		FileInputSynapse inputStream= new FileInputSynapse();
    inputStream.setInputFile(inputFile);
		inputStream.setAdvancedColumnSelector("1-9");
		input.addInputSynapse(inputStream);
    //Salida Deseada
    FileInputSynapse outputDesired= new FileInputSynapse();
    outputDesired.setInputFile(inputFile);
    outputDesired.setAdvancedColumnSelector("10-18");
    trainer.setDesired(outputDesired);
		//Monitor
    Monitor monitor= nnet.getMonitor();
    monitor.setLearningRate(Constantes.LEARNING_RATE);
    monitor.setMomentum(Constantes.MOMENTUM);
    monitor.setTrainingPatterns(getNumberOfLines(inputFile));
    monitor.setSupervised(true);
    monitor.setUseRMSE(true);
    monitor.setTotCicles(Constantes.CICLES_TRAINIG);
    monitor.setLearning(true);
    long initms= System.currentTimeMillis();
    //La red se corre en single-thread, synchronized mode
    nnet.getMonitor().setSingleThreadMode(true);
    nnet.go(true);
    logger.info("Tiempo total entrenamiento= "+(System.currentTimeMillis() - initms)+" ms");
	}
	
	public void entrenar(double[][] inputArray) {
		//Entrada
		MemoryInputSynapse inputSynapse= new MemoryInputSynapse();
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1-9");
		input.removeAllInputs();
		input.addInputSynapse(inputSynapse);
	   //Salida Deseada
		MemoryInputSynapse outputDesired= new MemoryInputSynapse();
		outputDesired.setInputArray(inputArray);
    outputDesired.setAdvancedColumnSelector("10-18");
    trainer.setDesired(outputDesired);
		//Monitor
    Monitor monitor= nnet.getMonitor();
    monitor.setLearningRate(Constantes.LEARNING_RATE);
    monitor.setMomentum(Constantes.MOMENTUM);
    monitor.setTrainingPatterns(1);
    monitor.setSupervised(true);
    monitor.setUseRMSE(true);
    monitor.setTotCicles(Constantes.CICLES_TRAINING_PLAYING);
    monitor.setLearning(true);
    long initms= System.currentTimeMillis();
    //La red se corre en single-thread, synchronized mode
    nnet.getMonitor().setSingleThreadMode(true);
    nnet.go(true);
    logger.info("Tiempo total entrenamiento= "+(System.currentTimeMillis() - initms)+" ms");

	}
	
	public boolean salvarRedNeuronal(String filename) {
		boolean save= true;
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(filename);
			try {
				ObjectOutputStream out= new ObjectOutputStream(stream);
				out.writeObject(nnet);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				save= false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			save= false;
		}
		return save;
	}
	
	public boolean restaurarRedNeuronal(String filename) {  
		FileInputStream stream;
		boolean restore= true;		
		try {
			stream = new FileInputStream(filename);
			try {
				ObjectInputStream in= new ObjectInputStream(stream);
				try {
					nnet= (NeuralNet) in.readObject();
					input= nnet.getInputLayer();
					output= nnet.findOutputLayer();
					trainer= (TeachingSynapse) nnet.getTeacher();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					restore= false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				restore= false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			restore= false;
		}
		return restore;
	}
	
	public BigDecimal[] preguntar(double[][] inputArray) {
		
		System.out.println("Vector pregunta");
		for (int i = 0; i < 9; i++) {
			System.out.print(inputArray[0][i] + " ");
		}
		System.out.println();
		
		MemoryInputSynapse inputSynapse= new MemoryInputSynapse();
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1-9");
		input.removeAllInputs();
		input.addInputSynapse(inputSynapse);
		//Archivo salida con resultados
		MemoryOutputSynapse memOut= new MemoryOutputSynapse();
		output.addOutputSynapse(memOut);
    //Monitor
    Monitor monitor= nnet.getMonitor();
    monitor.setTrainingPatterns(Constantes.TRAINING_PATTERNS);
    monitor.setTotCicles(Constantes.CICLES_NOT_TRAINING);
    monitor.setLearning(false);
    if(nnet != null) {
    	nnet.removeAllListeners();
    	nnet.getMonitor().setSingleThreadMode(true);
      nnet.go();
    }
    double[] pattern = memOut.getNextPattern();
    BigDecimal[] results= new BigDecimal[9];
    for(int i = 0; i < 9; i++) {
    	System.out.println(pattern[i]);
    	results[i]= new BigDecimal(pattern[i]);
    }
    return results;
	}
	
	@Override
	public void netStarted(NeuralNetEvent e) {
    Monitor mon = (Monitor)e.getSource();
    String msj= "La Red Neuronal comenzo para ";
    
    if (mon.isLearning())
    		msj+= "entrenarse.";
    else
    		msj+= "responder.";
    logger.info(msj);
	}

	@Override
	public void netStopped(NeuralNetEvent e) {
    Monitor mon = (Monitor)e.getSource();
    logger.info("La Red Neuronal se detuvo. Ultimo RMSE="+mon.getGlobalError()+"; Ciclos="+mon.getTotCicles());
	}

	@Override
	public void netStoppedError(NeuralNetEvent e, String error) {
		logger.error("La red se interrumpio debido al siguiente error: "+ error);
	}
	
	@Override
	public void cicleTerminated(NeuralNetEvent e) {	}

	@Override
	public void errorChanged(NeuralNetEvent e) {
    Monitor mon = (Monitor)e.getSource();
    logger.info("La red cambio. Ultimo RMSE="+mon.getGlobalError());
	}
	
	private int getNumberOfLines(File inputFile) {
		int lines= 0;
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
			for(; scanner.hasNextLine(); scanner.next()) lines++;
		} catch (FileNotFoundException e) {
			logger.error("Error al contar lineas RN");
		}
		return lines;
	}
}


