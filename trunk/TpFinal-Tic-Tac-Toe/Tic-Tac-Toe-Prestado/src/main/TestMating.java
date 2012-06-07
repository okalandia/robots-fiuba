/*    */ package main;
/*    */ 
/*    */ import genetic.Knowledge;
/*    */ import neural.Network;
/*    */ import neural.TicNetwork;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ 
/*    */ public class TestMating
/*    */ {
/* 17 */   private static Logger log = Logger.getLogger(TestMating.class);
/*    */ 
/*    */   public static void main(String[] args) throws FileNotFoundException, IOException
/*    */   {
/* 21 */     Properties logProperties = new Properties();
/*    */ 
/* 24 */     logProperties.load(new FileInputStream("etc/log4j.properties"));
/* 25 */     PropertyConfigurator.configure(logProperties);
/*    */ 
/* 27 */     Network father = new TicNetwork();
/* 28 */     Network mother = new TicNetwork();
/*    */ 
/* 30 */     log.debug(father);
/* 31 */     log.debug(mother);
/*    */ 
/* 33 */     Knowledge knowledge = new Knowledge();
/*    */ 
/* 35 */     Network child = knowledge.mate(father, mother);
/*    */ 
/* 37 */     log.debug(child);
/*    */   }
/*    */ }
