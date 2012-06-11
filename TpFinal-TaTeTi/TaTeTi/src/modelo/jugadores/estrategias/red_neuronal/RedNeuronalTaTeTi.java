package modelo.jugadores.estrategias.red_neuronal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

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
import org.joone.helpers.factory.JooneTools;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetLoader;

public class RedNeuronalTaTeTi implements Serializable, NeuralNetListener {

	private static final long serialVersionUID = 1L;

  private static double	LEARNING_RATE= 0.7;
  private static double	MOMENTUM= 0.6;
  private static int	CICLES_TRAINIG= 10;
  private static int	CICLES_NOT_TRAINING= 1;
  private static int TRAINING_PATTERNS= 1;
  private static String PATH_RESULTS="data/ResultadoTaTeTi.txt";

	private static Logger logger;
  private NeuralNet nnet;
  private Layer input;
  private Layer output;
	
	public RedNeuronalTaTeTi() {
		logger= LoggerTaTeTi.getInstancia().getLogger(this);
	}
	
	public void inicialRedNeuronal(String pathInputFile) {
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
    hidden2.setRows(9);
    hidden3.setRows(3);    
    output.setRows(9);
    //Synapses
    FullSynapse synapse_IH= new FullSynapse(); 	/* input   -> hidden1 */
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
    //Archivo que contiene las salidas deseadas
    FileInputSynapse samples= new FileInputSynapse();
    samples.setInputFile(new File(pathInputFile));
    samples.setAdvancedColumnSelector("10-18");
    //Entrenador
    TeachingSynapse trainer= new TeachingSynapse();
    trainer.setDesired(samples);   
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
		//Archivo-Entrenamiento-NN
		File inputFile= new File(pathInputFile);
    FileInputSynapse inputStream= new FileInputSynapse();
    //Archivo que contiene el input data
		inputStream.setInputFile(inputFile);
		inputStream.setAdvancedColumnSelector("1-9");
    input.addInputSynapse(inputStream);
		//Monitor
    Monitor monitor= nnet.getMonitor();
    monitor.setLearningRate(LEARNING_RATE);
    monitor.setMomentum(MOMENTUM);
    monitor.setTrainingPatterns(getNumberOfLines(inputFile));
    monitor.setSupervised(true);
    monitor.setUseRMSE(true);
    monitor.setTotCicles(CICLES_TRAINIG);
    monitor.setLearning(true);
    long initms= System.currentTimeMillis();
    //La red se corre en single-thread, synchronized mode
    nnet.getMonitor().setSingleThreadMode(true);
    nnet.go(true);
    logger.info("Tiempo total entrenamiento= "+(System.currentTimeMillis() - initms)+" ms");
	}
	
	public boolean salvarRedNeuronal(String filename) {
		boolean save= true;
		File file= new File(filename);
		if(file.exists())
			file.delete();
		try {
			JooneTools.save(nnet, filename);
		} catch (FileNotFoundException e) {
			logger.error("Error al salvar la RN, FileNotFoundException");
			save= false;
		} catch (IOException e) {
			logger.error("Error al salvar la RN, IOException");
			save= false;
		}
		return save;
	}
	
	public boolean restaurarRedNeuronal(String filename) {  
		NeuralNetLoader loader= new NeuralNetLoader(filename);
		nnet= loader.getNeuralNet();
		if(nnet != null) {
			input= nnet.getInputLayer();
			output= nnet.getOutputLayer();
			return true;
		}
		return false;
	}
	
	public void preguntar(String pathTrainingFile) {
		//Datos de entrada provenientes del tablero del ta-te-ti
		File trainingFile= new File(pathTrainingFile);
		FileInputSynapse inputStream= new FileInputSynapse();
		inputStream.setInputFile(trainingFile);
		inputStream.setAdvancedColumnSelector("1-9");
		input.addInputSynapse(inputStream);
    //Archivo salida con resultados
    FileOutputSynapse foutput=new FileOutputSynapse();
    foutput.setFileName(PATH_RESULTS);   
    output.addOutputSynapse(foutput);
    //Monitor
    Monitor monitor= nnet.getMonitor();
    monitor.setTrainingPatterns(TRAINING_PATTERNS);
    monitor.setTotCicles(CICLES_NOT_TRAINING);
    monitor.setLearning(false);
    if(nnet != null) {
        nnet.addOutputSynapse(foutput);
        nnet.getMonitor().setSingleThreadMode(true);
        nnet.go();
    }
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
	
	private int[] getResults() {
		int results[]= null;
		String sCadena= null;
		try {
			BufferedReader bf = new BufferedReader(new FileReader(PATH_RESULTS));
			try {
				while ((sCadena = bf.readLine())!=null)
					System.out.println(sCadena);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
    return results;
	}
}
