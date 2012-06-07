/*    */ package genetic;
/*    */ 
/*    */ import neural.Network;
/*    */ import neural.TicNetwork;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class Brain
/*    */ {
/* 13 */   private static Logger log = Logger.getLogger(Brain.class);
/*    */   public static final int POPULATION = 10;
/*    */   public static final int MAXPOP = 100;
/*    */   public static final double MUTATION = 0.8D;
/* 19 */   private Knowledge knowledge = new Knowledge();
/* 20 */   private List<Network> population = new ArrayList<Network>(100);
/*    */ 
/*    */   public void initPopulation()
/*    */   {
/* 24 */     log.debug("Initializing population.");
/*    */ 
/* 26 */     for (int i = 0; i < 10; i++)
/* 27 */       this.population.add(new TicNetwork());
/*    */   }
/*    */ 
/*    */   public void iteration()
/*    */   {
/* 32 */     ArrayList<Network> newPop = new ArrayList<Network>(100);
/*    */ 
/* 36 */     this.population = this.knowledge.naturalSelection(this.population);
/*    */ 
/* 38 */     for (Network father : this.population) {
/* 39 */       for (Network mother : this.population)
/*    */       {
/* 41 */         if (father == mother)
/*    */         {
/*    */           continue;
/*    */         }
/*    */ 
/* 47 */         Network child = this.knowledge.mate(father, mother);
/*    */ 
/* 49 */         if (Math.random() > 0.8D) {
/* 50 */           this.knowledge.mutate(child);
/*    */         }
/* 52 */         newPop.add(child);
/*    */       }
/*    */     }
/*    */ 
/* 56 */     this.population.addAll(newPop);
/*    */   }
/*    */ 
/*    */   public Knowledge getKnowledge() {
/* 60 */     return this.knowledge;
/*    */   }
/*    */ 
/*    */   public void setKnowledge(Knowledge knowledge) {
/* 64 */     this.knowledge = knowledge;
/*    */   }
/*    */ 
/*    */   public List<Network> getPopulation() {
/* 68 */     return this.population;
/*    */   }
/*    */ 
/*    */   public void setPopulation(ArrayList<Network> population) {
/* 72 */     this.population = population;
/*    */   }
/*    */ }

