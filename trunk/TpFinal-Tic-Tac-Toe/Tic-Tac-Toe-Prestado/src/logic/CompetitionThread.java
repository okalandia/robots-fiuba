/*    */ package logic;
/*    */ 
/*    */ import model.Board;
/*    */ import model.NeuralPlayer;
/*    */ import model.Player;
/*    */ import model.TicBoard;
/*    */ import neural.Network;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CompetitionThread extends Thread
/*    */ {
/* 15 */   private static Logger log = Logger.getLogger(CompetitionThread.class);
/*    */ 
/* 17 */   private BoardEvaluator eval = TicEvaluator.getInstance();
/*    */   private Network one;
/*    */   private List<Network> pop;
/*    */ 
/*    */   public CompetitionThread(Network one, List<Network> pop)
/*    */   {
/* 24 */     this.one = one;
/* 25 */     this.pop = pop;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 30 */     log.debug("Starting competition in thread.");
/* 31 */     for (Network netTwo : this.pop)
/*    */     {
/* 33 */       NeuralPlayer plOne = new NeuralPlayer(1, this.one);
/* 34 */       NeuralPlayer plTwo = new NeuralPlayer(-1, netTwo);
/*    */ 
/* 36 */       Player winner = competition(plOne, plTwo, new TicBoard());
/*    */ 
/* 38 */       if (winner == plOne) {
/* 39 */         this.one.setValue(this.one.getValue() + GeneticTrainer.WINVALUE);
/* 40 */         netTwo.setValue(netTwo.getValue() + GeneticTrainer.LOSSVALUE);
/* 41 */       } else if (winner == plTwo) {
/* 42 */         this.one.setValue(this.one.getValue() + GeneticTrainer.LOSSVALUE2);
/* 43 */         netTwo.setValue(netTwo.getValue() + GeneticTrainer.WINVALUE2);
/*    */       } else {
/* 45 */         this.one.setValue(this.one.getValue() + GeneticTrainer.DRAWVALUE);
/* 46 */         netTwo.setValue(netTwo.getValue() + GeneticTrainer.DRAWVALUE2);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private Player competition(Player pOne, Player pTwo, Board board)
/*    */   {
/* 54 */     log.debug("Competition starting.");
/*    */ 
/* 56 */     int i = 0;
/*    */ 
/* 59 */     while (i++ < 1000)
/*    */     {
/* 61 */       if (this.eval.isGameOver(board)) break;
/* 62 */       pOne.makeNextMove(board);
/*    */ 
/* 66 */       log.debug(board);
/*    */ 
/* 68 */       if (this.eval.isGameOver(board)) break;
/* 69 */       pTwo.makeNextMove(board);
/*    */ 
/* 73 */       log.debug(board);
/*    */     }
/*    */ 
/* 76 */     int winner = this.eval.findWinner(board);
/*    */ 
/* 78 */     log.debug("Competition ended. Winner is player=" + winner);
/*    */ 
/* 80 */     if (pOne.getValue() == winner)
/* 81 */       return pOne;
/* 82 */     if (pTwo.getValue() == winner) {
/* 83 */       return pTwo;
/*    */     }
/* 85 */     return null;
/*    */   }
/*    */ }

