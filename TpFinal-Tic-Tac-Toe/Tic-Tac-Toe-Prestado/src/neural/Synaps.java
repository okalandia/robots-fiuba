/*    */ package neural;
/*    */ 
/*    */ public class Synaps
/*    */ {
/*    */   private Neuron neuron;
/*    */   private double weight;
/*    */ 
/*    */   public Synaps(Neuron neuron)
/*    */   {
/* 11 */     this.weight = (Math.random() * 2.0D - 1.0D);
/* 12 */     this.neuron = neuron;
/*    */   }
/*    */ 
/*    */   public Neuron getNeuron() {
/* 16 */     return this.neuron;
/*    */   }
/*    */ 
/*    */   public void setNeuron(Neuron neuron) {
/* 20 */     this.neuron = neuron;
/*    */   }
/*    */ 
/*    */   public double getWeight() {
/* 24 */     return this.weight;
/*    */   }
/*    */ 
/*    */   public void setWeight(double weight) {
/* 28 */     this.weight = weight;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 34 */     String rounded = String.format("%.5f", new Object[] { Double.valueOf(this.weight) });
/*    */ 
/* 37 */     return " " + rounded;
/*    */   }
/*    */ }

