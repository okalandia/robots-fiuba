package modelo.jugadores.estrategias.red_neuronal;

import java.io.File;

import org.joone.engine.FullSynapse;
import org.joone.engine.LinearLayer;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import org.joone.net.NeuralNet;

public class RedNeuronalTicTacToe implements NeuralNetListener {

  private static String inputFile = "org/joone/samples/engine/xor/xor.txt";
  private static String outputFile = "/tmp/xorout.txt";
	
	private NeuralNet nnet;
	
	public RedNeuronalTicTacToe() {

	}
	
	public void inicialRedNeuronal() {
    //Layers
		LinearLayer input = new LinearLayer();
    SigmoidLayer hidden1 = new SigmoidLayer();
    SigmoidLayer hidden2 = new SigmoidLayer();
    SigmoidLayer hidden3 = new SigmoidLayer();
    SigmoidLayer output = new SigmoidLayer();
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
    FullSynapse synapse_IH = new FullSynapse(); 	/* input   -> hidden1 */
    FullSynapse synapse_HO = new FullSynapse(); 	/* hidden3 -> output  */
    FullSynapse synapse_Int1 = new FullSynapse(); /* hidden1 -> hidden2 */
    FullSynapse synapse_Int2 = new FullSynapse(); /* hidden2 -> hidden3 */
    synapse_IH.setName("IH");
    synapse_HO.setName("HO");
    synapse_Int1.setName("Int1");
    synapse_Int1.setName("Int2"); 
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
    //FileInput
    FileInputSynapse inputStream = new FileInputSynapse();
    inputStream.setAdvancedColumnSelector("1,1");
    //Archivo que contiene el input data
    inputStream.setInputFile(new File(inputFile));
    input.addInputSynapse(inputStream);
    //Archivo que contiene las salidas deseadas
    FileInputSynapse samples = new FileInputSynapse();
    samples.setInputFile(new File(inputFile));
    samples.setAdvancedColumnSelector("3");
    //Entrenador
    TeachingSynapse trainer= new TeachingSynapse();
    trainer.setDesired(samples);
    
    //Archivo de salida para errores
    FileOutputSynapse error = new FileOutputSynapse();
    error.setFileName(outputFile);
    trainer.addResultSynapse(error);
    
    //Agregamos esta estrucutra a un objeto Red Neuronal
    output.addOutputSynapse(trainer);
    nnet = new NeuralNet();
    nnet.addLayer(input, NeuralNet.INPUT_LAYER);
    nnet.addLayer(hidden1, NeuralNet.HIDDEN_LAYER);
    nnet.addLayer(hidden2, NeuralNet.HIDDEN_LAYER);
    nnet.addLayer(hidden3, NeuralNet.HIDDEN_LAYER);
    nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
    nnet.setTeacher(trainer);
    nnet.addNeuralNetListener(this);
	}

	public void entrenar() {
		//Entradas
    /*inputSynapse.setInputArray(inputArray);
    inputSynapse.setAdvancedColumnSelector("1,2");
    */
    // set the desired outputs
    /*desiredOutputSynapse.setInputArray(desiredOutputArray);
    desiredOutputSynapse.setAdvancedColumnSelector("1");
    */
		
    //Monitor
    Monitor monitor = nnet.getMonitor();
    monitor.setLearningRate(0.7);
    monitor.setMomentum(0.6);
    monitor.setTrainingPatterns(4); //cantidad de entradssssssssssss
    monitor.setTotCicles(500);  //epochs
    monitor.setLearning(true);
    nnet.go();
	}
	
	@Override
	public void cicleTerminated(NeuralNetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorChanged(NeuralNetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void preguntar() {
    // set the inputs
   /* inputSynapse.setInputArray(inputArray);
    inputSynapse.setAdvancedColumnSelector("1,2");*/
    Monitor monitor=nnet.getMonitor();
    monitor.setTrainingPatterns(4); //constanteeeeeeeee
    monitor.setTotCicles(1);
    monitor.setLearning(false);
    FileOutputSynapse foutput=new FileOutputSynapse();
    foutput.setFileName("/tmp/xorOut.txt");
    if(nnet!=null) {
        nnet.addOutputSynapse(foutput);
        System.out.println(nnet.check());
//        nnet.getMonitor().setSingleThreadMode(singleThreadMode);
        nnet.go();
    }
	}
	
	@Override
	public void netStarted(NeuralNetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netStopped(NeuralNetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netStoppedError(NeuralNetEvent arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
}
