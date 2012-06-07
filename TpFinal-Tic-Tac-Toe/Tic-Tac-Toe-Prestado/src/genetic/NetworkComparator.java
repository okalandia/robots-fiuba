/*    */ package genetic;
/*    */ 
/*    */ import neural.Network;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class NetworkComparator
/*    */   implements Comparator<Network>
/*    */ {
/*    */   public int compare(Network netOne, Network netTwo)
/*    */   {
/* 15 */     if (netOne.getGeneration() > 6)
/* 16 */       return 1;
/* 17 */     if (netTwo.getGeneration() > 6) {
/* 18 */       return 1;
/*    */     }
/* 20 */     if (netOne.getValue() > netTwo.getValue())
/* 21 */       return -1;
/* 22 */     if (netOne.getValue() < netTwo.getValue()) {
/* 23 */       return 1;
/*    */     }
/* 25 */     return 0;
/*    */   }
/*    */ }
