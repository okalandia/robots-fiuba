/*     */ package model;
/*     */ 
/*     */ import java.util.ArrayList;
import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TicBoard
/*     */   implements Board
/*     */ {
/*   9 */   private static Logger log = Logger.getLogger(TicBoard.class);
/*     */ 
/*  11 */   private int[] fields = new int[9];
/*  12 */   private int size = 9;
/*     */ 
/*  14 */   private int nextPlayer = 1;
/*     */ 
/*     */   public TicBoard()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TicBoard(int[] fields, int nextPlayer) {
/*  21 */     this.fields = fields;
/*  22 */     this.size = fields.length;
/*  23 */     this.nextPlayer = nextPlayer;
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  29 */     return this.size;
/*     */   }
/*     */ 
/*     */   public int getValue(int index)
/*     */   {
/*  35 */     int value = this.fields[index];
/*     */ 
/*  37 */     log.debug("Returning value " + value + " at index \t" + index);
/*     */ 
/*  39 */     return value;
/*     */   }
/*     */ 
/*     */   public void setValue(int index, int value)
/*     */   {
/*  45 */     log.debug("Setting value " + value + " at index \t" + index);
/*     */ 
/*  47 */     this.fields[index] = value;
/*     */   }
/*     */ 
/*     */   public void makeMove(int index)
/*     */   {
/*  53 */     setValue(index, this.nextPlayer);
/*     */ 
/*  56 */     this.nextPlayer = (this.nextPlayer == 1 ? -1 : 1);
/*     */   }
/*     */ 
/*     */   public int[] getFields()
/*     */   {
/*  61 */     log.debug("Returning board fields.");
/*     */ 
/*  63 */     return this.fields;
/*     */   }
/*     */ 
/*     */   public void setFields(int[] fields)
/*     */   {
/*  69 */     this.fields = fields;
/*     */   }
/*     */ 
/*     */   public int getNextPlayer()
/*     */   {
/*  74 */     return this.nextPlayer;
/*     */   }
/*     */ 
/*     */   public void setNextPlayer(int nextPlayer)
/*     */   {
/*  79 */     this.nextPlayer = nextPlayer;
/*     */   }
/*     */ 
/*     */   public boolean isLegalMove(int index)
/*     */   {
/*  85 */     boolean result = this.fields[index] == 0;
/*     */ 
/*  87 */     log.debug("Checking legal move for index " + index + ", result = " + result);
/*     */ 
/*  89 */     return result;
/*     */   }
/*     */ 
/*     */   public ArrayList<Integer> getLegalMoves()
/*     */   {
/*  95 */     ArrayList<Integer> result = new ArrayList<Integer>();
/*     */ 
/*  97 */     for (int i = 0; i < this.fields.length; i++)
/*     */     {
/*  99 */       if (this.fields[i] == 0) {
/* 100 */         result.add(Integer.valueOf(i));
/*     */       }
/*     */     }
/* 103 */     log.debug("Returning legal moves: " + result);
/*     */ 
/* 105 */     return result;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 111 */     String result = "\n";
/*     */ 
/* 113 */     result = result + this.fields[0] + "|" + this.fields[1] + "|" + this.fields[2] + "\n";
/* 114 */     result = result + "-----------\n";
/* 115 */     result = result + this.fields[3] + "|" + this.fields[4] + "|" + this.fields[5] + "\n";
/* 116 */     result = result + "-----------\n";
/* 117 */     result = result + this.fields[6] + "|" + this.fields[7] + "|" + this.fields[8];
/*     */ 
/* 119 */     result = result.replaceAll("0", "   ");
/* 120 */     result = result.replaceAll("-1", " o ");
/* 121 */     result = result.replaceAll("1", " x ");
/*     */ 
/* 123 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\Vero\Desktop\
 * Qualified Name:     ee.ut.aa.neuraltic.model.TicBoard
 * JD-Core Version:    0.6.0
 */