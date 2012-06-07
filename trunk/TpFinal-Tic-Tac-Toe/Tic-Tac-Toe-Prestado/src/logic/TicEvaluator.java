/*     */ package logic;
/*     */ 
/*     */ import model.Board;

/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TicEvaluator
/*     */   implements BoardEvaluator
/*     */ {
/*   9 */   private static Logger log = Logger.getLogger(TicEvaluator.class);
/*     */   private static TicEvaluator eval;
/*     */ 
/*     */   public static TicEvaluator getInstance()
/*     */   {
/*  19 */     if (eval == null) {
/*  20 */       eval = new TicEvaluator();
/*     */     }
/*     */ 
/*  23 */     return eval;
/*     */   }
/*     */ 
/*     */   public int findWinner(Board board)
/*     */   {
/*  29 */     int[] fields = board.getFields();
/*     */ 
/*  31 */     for (int i = 0; i < board.getSize(); i++)
/*     */     {
/*  33 */       if (isWinningSpot(fields, i)) {
/*  34 */         return fields[i];
/*     */       }
/*     */     }
/*  37 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isGameOver(Board board)
/*     */   {
/*  43 */     int[] fields = board.getFields();
/*     */ 
/*  45 */     log.debug("Evaluating board.");
/*     */ 
/*  47 */     if (board.getLegalMoves().size() == 0) {
/*  48 */       return true;
/*     */     }
/*     */ 
/*  52 */     if ((fields[0] == 1) && (fields[1] == 1) && (fields[2] == 1)) return true;
/*  53 */     if ((fields[3] == 1) && (fields[4] == 1) && (fields[5] == 1)) return true;
/*  54 */     if ((fields[6] == 1) && (fields[7] == 1) && (fields[8] == 1)) return true;
/*     */ 
/*  56 */     if ((fields[0] == -1) && (fields[1] == -1) && (fields[2] == -1)) return true;
/*  57 */     if ((fields[3] == -1) && (fields[4] == -1) && (fields[5] == -1)) return true;
/*  58 */     if ((fields[6] == -1) && (fields[7] == -1) && (fields[8] == -1)) return true;
/*     */ 
/*  61 */     if ((fields[0] == 1) && (fields[3] == 1) && (fields[6] == 1)) return true;
/*  62 */     if ((fields[1] == 1) && (fields[4] == 1) && (fields[7] == 1)) return true;
/*  63 */     if ((fields[2] == 1) && (fields[5] == 1) && (fields[8] == 1)) return true;
/*     */ 
/*  65 */     if ((fields[0] == -1) && (fields[3] == -1) && (fields[6] == -1)) return true;
/*  66 */     if ((fields[1] == -1) && (fields[4] == -1) && (fields[7] == -1)) return true;
/*  67 */     if ((fields[2] == -1) && (fields[5] == -1) && (fields[8] == -1)) return true;
/*     */ 
/*  70 */     if ((fields[0] == 1) && (fields[4] == 1) && (fields[8] == 1)) return true;
/*  71 */     if ((fields[2] == 1) && (fields[4] == 1) && (fields[6] == 1)) return true;
/*     */ 
/*  73 */     if ((fields[0] == -1) && (fields[4] == -1) && (fields[8] == -1)) return true;
/*  74 */     if ((fields[2] == -1) && (fields[4] == -1) && (fields[6] == -1)) return true;
/*     */ 
/*  86 */     log.debug("Game is not over.. returning FALSE");
/*     */ 
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isWinningSpot(int[] fields, int index)
/*     */   {
/*  93 */     int size = 9;
/*  94 */     int width = 3;
/*     */ 
/*  96 */     int player = fields[index];
/*     */ 
/*  98 */     if (player == 0) {
/*  99 */       return false;
/*     */     }
/* 101 */     log.debug("Checking if spot index=" + index + " is winning spot for player=" + player);
/*     */ 
/* 103 */     boolean leToRi = leftToRight(fields, index, player, size, width);
/*     */ 
/* 105 */     boolean toToBo = topToBottom(fields, index, player, size, width);
/*     */ 
/* 107 */     boolean diagon = diagonal(fields, index, player, size, width);
/*     */ 
/* 109 */     boolean diaRev = diagReverse(fields, index, player, size, width);
/*     */ 
/* 111 */     if ((leToRi) || (toToBo) || (diagon) || (diaRev)) {
/* 112 */       return true;
/*     */     }
/* 114 */     log.debug("Did not find any winning combination for player=" + player);
/*     */ 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean leftToRight(int[] fields, int index, int player, int size, int width)
/*     */   {
/* 121 */     int maxLength = 0;
/*     */ 
/* 124 */     for (int i = index; (i < index + 3) && (i < size); i++)
/*     */     {
/* 126 */       if (fields[i] == player) {
/* 127 */         maxLength++;
/*     */       }
/*     */     }
/* 130 */     log.debug("Max length on left-to-right= " + maxLength);
/*     */ 
/* 135 */     return (maxLength >= 3) && (width - index % width >= 3);
/*     */   }
/*     */ 
/*     */   private boolean topToBottom(int[] fields, int index, int player, int size, int width)
/*     */   {
/* 142 */     int maxLength = 0;
/*     */ 
/* 144 */     for (int i = index; (i < index + 3 * width) && (i < size); i += width) {
/* 145 */       if (fields[i] == player) {
/* 146 */         maxLength++;
/*     */       }
/*     */     }
/* 149 */     log.debug("Max length on top-to-bottom= " + maxLength);
/*     */ 
/* 154 */     return maxLength >= 3;
/*     */   }
/*     */ 
/*     */   private boolean diagonal(int[] fields, int index, int player, int size, int width)
/*     */   {
/* 161 */     int maxLength = 0;
/*     */ 
/* 163 */     for (int i = index; (i < index + 3 * width + width) && (i < size); i = i + 1 + width) {
/* 164 */       if (fields[i] != player) break;
/* 165 */       maxLength++;
/*     */     }
/*     */ 
/* 170 */     log.debug("Max length on diagonal= " + maxLength);
/*     */ 
/* 175 */     return (maxLength >= 3) && (width - index % width >= 3);
/*     */   }
/*     */ 
/*     */   private boolean diagReverse(int[] fields, int index, int player, int size, int width)
/*     */   {
/* 182 */     int maxLength = 0;
/*     */ 
/* 184 */     for (int i = index; (i < index + 3 * width + width) && (i < size); i = i - 1 + width) {
/* 185 */       if (fields[i] != player) break;
/* 186 */       maxLength++;
/*     */     }
/*     */ 
/* 191 */     log.debug("Max length on reverse diagonal= " + maxLength);
/*     */ 
/* 197 */     return (maxLength >= 3) && (index % width >= 2);
/*     */   }
/*     */ }

/* Location:           C:\Users\Vero\Desktop\
 * Qualified Name:     ee.ut.aa.neuraltic.logic.TicEvaluator
 * JD-Core Version:    0.6.0
 */