/*    */ package main;
/*    */ 
/*    */ import logic.Competition;
/*    */ import model.HumanPlayer;
/*    */ import model.NeuralPlayer;
/*    */ import model.Player;
/*    */ import model.TicBoard;
/*    */ import neural.Layer;
/*    */ import neural.Neuron;
/*    */ import neural.Synaps;
/*    */ import neural.TicNetwork;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;


import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Properties;
/*    */ import java.util.Scanner;
import org.apache.log4j.PropertyConfigurator;
/*    */ 
/*    */ public class HumanTest
/*    */ {
/*    */   public static void main(String[] args)

			throws FileNotFoundException, IOException
/*    */   {
	/*    */    InputStream io; 
			Properties logProperties = new Properties();
/*    */ 
/* 32 */     logProperties.load(new FileInputStream("etc/log4j.properties"));
/* 33 */     PropertyConfigurator.configure(logProperties);
/*    */     try
/*    */     {
				io = new FileInputStream(args[0]);
/*    */     }
/*    */     catch (FileNotFoundException e)
/*    */     {
/*    */       System.out.println("File not found: " + args[0]);
/* 42 */       return;
/*    */     }
/*    */     Scanner in = new Scanner(io);
/* 46 */     Locale loc = new Locale("en", "US");
/*    */ 
/* 48 */     in.useLocale(loc);
/* 49 */     in.useDelimiter(";");
/*    */ 
/* 51 */     TicNetwork network = new TicNetwork();
/*    */ 
/* 53 */   
/*    */ 
			List<Layer> layers= network.getLayers();
			for (Layer layer : layers) {
/* 56 */       for (Neuron neuron : layer.getNeurons()) {
/* 57 */         for (Synaps synaps : neuron.getSynapses())
/*    */         {
/* 59 */           synaps.setWeight(in.nextDouble());
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 64 */     Competition comp = new Competition();
/*    */ 
/* 66 */     Object human = new HumanPlayer();
/* 67 */     ((Player)human).setValue(-1);
/*    */ 
/* 69 */     comp.competition(new NeuralPlayer(1, network), (Player)human, new TicBoard());
/*    */ 
/* 71 */     human = new HumanPlayer();
/* 72 */     ((Player)human).setValue(1);
/*    */ 
/* 74 */     comp.competition((Player)human, new NeuralPlayer(-1, network), new TicBoard());
/*    */   }
/*    */ }

