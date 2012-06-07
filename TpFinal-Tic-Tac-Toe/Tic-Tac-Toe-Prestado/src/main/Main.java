/*    */ package main;
/*    */ 
/*    */ import logic.GeneticTrainer;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ 
/*    */ public class Main
/*    */ {
/* 17 */   private static Logger log = Logger.getLogger(Main.class);
/*    */ 
/*    */   public static void main(String[] args) throws FileNotFoundException, IOException
/*    */   {
/* 21 */     Properties logProperties = new Properties();
/*    */ 
/* 24 */     logProperties.load(new FileInputStream("etc/log4j.properties"));
/* 25 */     PropertyConfigurator.configure(logProperties);
/*    */ 
/* 27 */     log.info("Hello world!");
/*    */ 
/* 29 */     log.info("Starting training with following parameters:");
/* 30 */     log.info("population=10");
/* 31 */     log.info("mutation=0.8");
/*    */ 
/* 33 */     String resultval = "win=" + GeneticTrainer.WINVALUE;
/* 34 */     resultval = resultval + ";draw=" + GeneticTrainer.DRAWVALUE;
/* 35 */     resultval = resultval + ";loss=" + GeneticTrainer.LOSSVALUE;
/*    */ 
/* 37 */     log.info(resultval);
/*    */ 
/* 39 */     log.info("minmax depth=2");
/*    */ 
/* 41 */     GeneticTrainer trainer = new GeneticTrainer();
/*    */ 
/* 43 */     trainer.startTraining(2147483647);
/*    */   }
/*    */ }
