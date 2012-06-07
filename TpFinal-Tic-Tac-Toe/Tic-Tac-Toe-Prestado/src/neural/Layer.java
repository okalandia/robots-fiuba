/*    */ package neural;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class Layer
/*    */ {
/* 10 */   private static Logger log = Logger.getLogger(Layer.class);
/*    */   private Layer nextLayer;
/*    */   private List<Neuron> neurons;
/*    */ 
/*    */   public Layer(Layer nextLayer)
/*    */   {
/* 17 */     log.debug("Initializing layer.");
/*    */ 
/* 19 */     this.nextLayer = nextLayer;
/*    */   }
/*    */ 
/*    */   public void initNeurons(int size)
/*    */   {
/* 24 */     log.debug("Initializing layer neurons.");
/*    */ 
/* 26 */     this.neurons = new ArrayList<Neuron>();
/*    */ 
/* 30 */     for (int i = 0; i < size; i++)
/*    */     {
/* 32 */       Neuron neuron = new Neuron();
/* 33 */       this.neurons.add(neuron);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void initFeedForward()
/*    */   {
/* 39 */     log.debug("Initializing layer feed-forward.");
/*    */ 
/* 41 */     for (Neuron neuron : this.neurons)
/*    */     {
/* 43 */       if (neuron.getValue() < 0.0D) {
/*    */         continue;
/*    */       }
/* 46 */       for (Synaps synaps : neuron.getSynapses())
/*    */       {
/* 48 */         Neuron dest = synaps.getNeuron();
/* 49 */         double oldValue = dest.getValue();
/*    */ 
/* 52 */         dest.setValue(oldValue + neuron.getValue() * synaps.getWeight());
/*    */       }
/*    */     }
/*    */ 
/* 56 */     if (this.nextLayer != null)
/* 57 */       this.nextLayer.initFeedForward();
/*    */   }
/*    */ 
/*    */   public Layer getNextLayer() {
/* 61 */     return this.nextLayer;
/*    */   }
/*    */ 
/*    */   public void setNextLayer(Layer nextLayer) {
/* 65 */     this.nextLayer = nextLayer;
/*    */   }
/*    */ 
/*    */   public List<Neuron> getNeurons() {
/* 69 */     return this.neurons;
/*    */   }
/*    */ 
/*    */   public void setNeurons(List<Neuron> neurons) {
/* 73 */     this.neurons = neurons;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 79 */     String result = "\n";
/*    */ 
/* 81 */     for (Neuron neuron : this.neurons) {
/* 82 */       result = result + "  [" + neuron + "];\n";
/*    */     }
/* 84 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\Vero\Desktop\
 * Qualified Name:     ee.ut.aa.neuraltic.neural.Layer
 * JD-Core Version:    0.6.0
 */