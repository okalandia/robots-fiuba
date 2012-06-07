/*    */ package neural;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Neuron
/*    */   implements Cloneable
/*    */ {
/*    */   private double value;
/*  9 */   private List<Synaps> synapses = new ArrayList<Synaps>();
/*    */ 
/*    */   public double getValue() {
/* 12 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(double value) {
/* 16 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public void addSynaps(Synaps synaps)
/*    */   {
/* 21 */     this.synapses.add(synaps);
/*    */   }
/*    */ 
/*    */   public List<Synaps> getSynapses() {
/* 25 */     return this.synapses;
/*    */   }
/*    */ 
/*    */   public void setSynapses(List<Synaps> synapses) {
/* 29 */     this.synapses = synapses;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 35 */     String result = "\n     ";
/*    */ 
/* 37 */     for (Synaps synaps : this.synapses) {
/* 38 */       result = result + "[" + synaps + "];";
/*    */     }
/* 40 */     return result;
/*    */   }
/*    */ }

