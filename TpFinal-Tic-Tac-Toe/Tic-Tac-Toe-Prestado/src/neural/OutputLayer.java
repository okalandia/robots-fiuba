/*    */ package neural;
/*    */ 
/*    */ import java.util.Arrays;

/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class OutputLayer extends Layer
/*    */ {
/*  9 */   private static Logger log = Logger.getLogger(OutputLayer.class);
/*    */ 
/*    */   public OutputLayer(Layer nextLayer)
/*    */   {
/* 13 */     super(nextLayer);
/* 14 */     initNeurons(1);
/*    */   }
/*    */ 
/*    */   public double[] retreiveOutput()
/*    */   {
/* 19 */     double[] result = new double[getNeurons().size()];
/*    */ 
/* 21 */     int i = 0;
/* 22 */     for (Neuron neuron : getNeurons()) {
/* 23 */       result[(i++)] = neuron.getValue();
/*    */     }
/*    */ 
/* 26 */     log.debug("Returning output layer result=" + Arrays.toString(result));
/*    */ 
/* 28 */     return result;
/*    */   }
/*    */ }

