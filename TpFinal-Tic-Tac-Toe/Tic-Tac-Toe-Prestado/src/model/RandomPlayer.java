/*    */ package model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RandomPlayer
/*    */   implements Player
/*    */ {
/* 10 */   private static Logger log = Logger.getLogger(RandomPlayer.class);
/*    */   private Random r;
/*    */   private int value;
/*    */ 
/*    */   public RandomPlayer(int value)
/*    */   {
/* 17 */     this.value = value;
/* 18 */     this.r = new Random();
/*    */   }
/*    */ 
/*    */   public Board makeNextMove(Board board)
/*    */   {
/* 24 */     log.debug("Requesting next move for player " + this.value);
/*    */ 
/* 26 */     ArrayList<Integer> legalMoves = board.getLegalMoves();
/*    */ 
/* 28 */     int random = this.r.nextInt(legalMoves.size());
/*    */ 
/* 30 */     int move = ((Integer)legalMoves.get(random)).intValue();
/*    */ 
/* 32 */     log.debug("Next move is: " + move);
/*    */ 
/* 34 */     board.makeMove(move);
/*    */ 
/* 36 */     return board;
/*    */   }
/*    */ 
/*    */   public int getValue()
/*    */   {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(int value)
/*    */   {
/* 48 */     this.value = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Vero\Desktop\
 * Qualified Name:     ee.ut.aa.neuraltic.model.RandomPlayer
 * JD-Core Version:    0.6.0
 */