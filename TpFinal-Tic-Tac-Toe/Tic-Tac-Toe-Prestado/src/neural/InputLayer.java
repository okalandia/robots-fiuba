/*    */ package neural;
/*    */ 
/*    */ import java.util.Arrays;

/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class InputLayer extends Layer
/*    */ {
/*  9 */   private static Logger log = Logger.getLogger(InputLayer.class);
/*    */ 
/*    */   public InputLayer(Layer nextLayer)
/*    */   {
/* 13 */     super(nextLayer);
/* 14 */     initNeurons(18);
/*    */   }
/*    */ 
/*    */   public void feedInput(double[] values)
/*    */   {
/* 19 */     log.debug("Feeding input layer with values=" + Arrays.toString(values));
/*    */ 
/* 21 */     int length = values.length;
/*    */ 
/* 23 */     for (int i = 0; i < length; i++)
/*    */     {
/* 25 */       ((Neuron)getNeurons().get(i)).setValue(values[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void initFeedForward()
/*    */   {
/* 32 */     log.debug("Initializing input layer feed-forward.");
/* 33 */     super.initFeedForward();
/*    */   }
/*    */ }

