/*    */ package logic;
/*    */ 
/*    */ import neural.Layer;
/*    */ import neural.Network;
/*    */ import neural.Neuron;
/*    */ import neural.Synaps;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PopulationStats
/*    */ {
/* 14 */   private static Logger log = Logger.getLogger(PopulationStats.class);
/*    */ 
/*    */   public static void logNetwork(Network network)
/*    */   {
/* 18 */     Logger netlog = Logger.getLogger("networks");
/*    */ 
/* 20 */     if (!netlog.isDebugEnabled()) {
/* 21 */       return;
/*    */     }
/* 23 */     String result = "Network value=" + network.getValue();
/*    */ 
/* 25 */     for (Layer layer : network.getLayers()) {
/* 26 */       result = result + "\n[";
/* 27 */       for (Neuron neuron : layer.getNeurons()) {
/* 28 */         result = result + "  \n[";
/* 29 */         for (Synaps synaps : neuron.getSynapses()) {
/* 30 */           result = result + "[" + synaps.getWeight() + "];";
/*    */         }
/* 32 */         result = result + "]";
/*    */       }
/* 34 */       result = result + "]";
/*    */     }
/*    */ 
/* 37 */     netlog.debug(result);
/*    */   }
/*    */ 
/*    */   public static void logStats(List<Network> population)
/*    */   {
/* 42 */     int min = 2147483647;
/* 43 */     int avg = 0;
/* 44 */     int max = -2147483648;
/*    */ 
/* 48 */     for (Network network : population) {
/* 49 */       int tmp = network.getValue();
/*    */ 
/* 51 */       min = tmp < min ? tmp : min;
/* 52 */       avg += tmp;
/* 53 */       max = tmp > max ? tmp : max;
/*    */     }
/*    */ 
/* 56 */     avg /= population.size();
/*    */ 
/* 58 */     log.info("Population stats;min=" + min + ";avg=" + avg + ";max=" + max);
/*    */   }
/*    */ 
/*    */   public static void logGenerations(List<Network> population)
/*    */   {
/* 63 */     String result = ";";
/*    */ 
/* 65 */     for (Network network : population) {
/* 66 */       result = result + network.getGeneration() + ";";
/*    */     }
/*    */ 
/* 69 */     log.info("Generation survival rates " + result);
/*    */   }
/*    */ 
/*    */   public static void logGamestats(List<Network> population)
/*    */   {
/* 74 */     for (Network network : population)
/*    */     {
/* 76 */       String netStats = "Best network stats=";
/* 77 */       netStats = netStats + network.winsOne + ";";
/* 78 */       netStats = netStats + network.drwsOne + ";";
/* 79 */       netStats = netStats + network.lossOne + ";";
/* 80 */       netStats = netStats + network.winsTwo + ";";
/* 81 */       netStats = netStats + network.drwsTwo + ";";
/* 82 */       netStats = netStats + network.lossTwo + ";";
/*    */ 
/* 84 */       log.info(netStats);
/*    */     }
/*    */   }
/*    */ }

