/*    */ package neural;
/*    */ 
/*    */ public class HiddenLayer extends Layer
/*    */ {
/*    */   public HiddenLayer(Layer nextLayer)
/*    */   {
/*  7 */     this(nextLayer, 9);
/*    */   }
/*    */ 
/*    */   public HiddenLayer(Layer nextLayer, int i) {
/* 11 */     super(nextLayer);
/* 12 */     initNeurons(i);
/*    */   }
/*    */ }

