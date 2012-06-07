/*    */ package neural;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class Network
/*    */ {
/*    */   private InputLayer input;
/*    */   private OutputLayer output;
/*    */   private List<Layer> layers;
/* 12 */   private int generation = 0;
/* 13 */   private int value = 0;
/*    */ 
/* 15 */   public int winsOne = 0;
/* 16 */   public int drwsOne = 0;
/* 17 */   public int lossOne = 0;
/* 18 */   public int winsTwo = 0;
/* 19 */   public int drwsTwo = 0;
/* 20 */   public int lossTwo = 0;
/*    */ 
/*    */   public InputLayer getInput() {
/* 23 */     return this.input;
/*    */   }
/*    */ 
/*    */   public OutputLayer getOutput() {
/* 27 */     return this.output;
/*    */   }
/*    */ 
/*    */   public List<Layer> getLayers() {
/* 31 */     return this.layers;
/*    */   }
/*    */ 
/*    */   public void setLayers(List<Layer> layers)
/*    */   {
/* 36 */     this.input = ((InputLayer)layers.get(0));
/* 37 */     this.output = ((OutputLayer)layers.get(layers.size() - 1));
/*    */ 
/* 39 */     this.layers = layers;
/*    */   }
/*    */ 
/*    */   public void resetNeurons() {
/* 43 */     for (Layer layer : this.layers)
/* 44 */       for (Neuron neuron : layer.getNeurons())
/* 45 */         neuron.setValue(0.0D);
/*    */   }
/*    */ 
/*    */   public void increaseGeneration()
/*    */   {
/* 51 */     this.generation += 1;
/*    */   }
/*    */ 
/*    */   public int getGeneration() {
/* 55 */     return this.generation;
/*    */   }
/*    */ 
/*    */   public void setGeneration(int generation) {
/* 59 */     this.generation = generation;
/*    */   }
/*    */ 
/*    */   public int getValue() {
/* 63 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(int value) {
/* 67 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 73 */     String result = "\n";
/*    */ 
/* 75 */     for (Layer layer : this.layers) {
/* 76 */       result = result + "[" + layer + "]\n";
/*    */     }
/* 78 */     return result;
/*    */   }
/*    */ }
