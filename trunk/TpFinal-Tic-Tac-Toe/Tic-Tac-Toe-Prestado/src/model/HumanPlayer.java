/*    */ package model;
/*    */ 

/*    */ import java.util.ArrayList;

/*    */ import java.util.Scanner;
import org.apache.log4j.Logger;
/*    */ 
/*    */ public class HumanPlayer
/*    */   implements Player
/*    */ {
/* 10 */   private static Logger log = Logger.getLogger(HumanPlayer.class);
/*    */ 
/* 12 */   private int value = -1;
/*    */ 
/* 14 */   private Scanner in = new Scanner(System.in);
/*    */ 
/*    */   public Board makeNextMove(Board board)
/*    */   {
/* 19 */     log.debug("Requesting next move for player " + this.value);
/*    */ 
/* 21 */     ArrayList<Integer> legalMoves = board.getLegalMoves();
/*    */ 
/* 23 */     System.out.println("Board:");
/* 24 */     System.out.println(board);
/* 25 */     System.out.println("\nLegal moves:");
/* 26 */     System.out.println(legalMoves);
/* 27 */     System.out.println("Enter your move: ");
/*    */ 
/* 29 */     int move = this.in.nextInt();
/*    */ 
/* 31 */     System.out.println("Your move was: " + move);
/*    */ 
/* 33 */     log.debug("Next move is: " + move);
/*    */     try
/*    */     {
/* 36 */       board.makeMove(move);
/*    */     } catch (Exception e) {
/* 38 */       System.err.println("You screwed up this move - Initializing move request.");
/* 39 */       makeNextMove(board);
/*    */     }
/*    */ 
/* 42 */     if (legalMoves.size() == board.getLegalMoves().size()) {
/* 43 */       System.err.println("You screwed up this move - not even trying to fix this one, this game is done.");
/* 44 */       for (int i = 0; i < board.getSize(); i++) {
/* 45 */         board.setValue(i, 1);
/*    */       }
/*    */     }
/*    */ 
/* 49 */     return board;
/*    */   }
/*    */ 
/*    */   public int getValue()
/*    */   {
/* 55 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(int value)
/*    */   {
/* 61 */     this.value = value;
/*    */   }
/*    */ }
