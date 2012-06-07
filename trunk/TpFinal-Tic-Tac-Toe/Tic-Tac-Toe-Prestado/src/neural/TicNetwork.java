/*    */ package neural;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class TicNetwork extends Network
/*    */ {
/*    */   public TicNetwork()
/*    */   {
/* 11 */     OutputLayer output = new OutputLayer(null);
/*    */ 
/* 14 */     Layer hidden3 = new HiddenLayer(output, 5);
/* 15 */     Layer hidden2 = new HiddenLayer(hidden3, 17);
/* 16 */     Layer hidden1 = new HiddenLayer(hidden2, 43);
/* 17 */     InputLayer input = new InputLayer(hidden1);
/*    */ 
/* 19 */     ArrayList<Layer> layers = new ArrayList<Layer>();
/*    */ 
/* 21 */     layers.add(input);
/* 22 */     layers.add(hidden1);
/* 23 */     layers.add(hidden2);
/* 24 */     layers.add(hidden3);
/*    */ 
/* 26 */     layers.add(output);
/*    */ 
/* 28 */     for (int i = 0; i < layers.size() - 1; i++)
/*    */     {
/* 30 */       Layer layerOne = (Layer)layers.get(i);
/* 31 */       Layer layerTwo = (Layer)layers.get(i + 1);
/* 32 */       for (Neuron neuronOne : layerOne.getNeurons()) {
/* 33 */         for (Neuron neuronTwo : layerTwo.getNeurons()) {
/* 34 */           neuronOne.addSynaps(new Synaps(neuronTwo));
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 39 */     setLayers(layers);
/*    */   }
/*    */ }
