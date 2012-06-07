/*    */ package logic;
/*    */ 
/*    */ import model.Board;
/*    */ import model.Player;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class Competition
/*    */ {
/* 10 */   private static Logger log = Logger.getLogger(Competition.class);
/*    */ 
/* 12 */   private BoardEvaluator eval = TicEvaluator.getInstance();
/*    */ 
/*    */   public Player competition(Player one, Player two, Board board)
/*    */   {
/* 16 */     log.debug("Competition starting.");
/*    */ 
/* 18 */     int i = 0;
/*    */ 
/* 21 */     while (i++ < 1000)
/*    */     {
/* 23 */       if (this.eval.isGameOver(board)) break;
/* 24 */       one.makeNextMove(board);
/*    */ 
/* 28 */       log.debug(board);
/*    */ 
/* 30 */       if (this.eval.isGameOver(board)) break;
/* 31 */       two.makeNextMove(board);
/*    */ 
/* 35 */       log.debug(board);
/*    */     }
/*    */ 
/* 38 */     int winner = this.eval.findWinner(board);
/*    */ 
/* 40 */     log.info(board);
/* 41 */     log.info("Competition ended. Winner is player=" + winner);
/*    */ 
/* 43 */     if (one.getValue() == winner)
/* 44 */       return one;
/* 45 */     if (two.getValue() == winner) {
/* 46 */       return two;
/*    */     }
/* 48 */     return null;
/*    */   }
/*    */ }

