/*     */ package model;
/*     */ 
/*     */ import logic.TicEvaluator;


/*     */ import neural.Network;

import java.util.ArrayList;
/*     */ import java.util.Arrays;

import org.apache.log4j.Logger;
/*     */ 
/*     */ public class NeuralPlayer
/*     */   implements Player
/*     */ {
/*  13 */   private static Logger log = Logger.getLogger(NeuralPlayer.class);
/*     */   public static final int DEPTH = 2;
/*     */   private int value;
/*     */   private Network network;
/*     */   private TicEvaluator evaluator;
/*     */ 
/*     */   public NeuralPlayer(int value, Network network)
/*     */   {
/*  24 */     this.value = value;
/*  25 */     this.network = network;
/*  26 */     this.evaluator = TicEvaluator.getInstance();
/*     */   }
/*     */ 
/*     */   public Board makeNextMove(Board board)
/*     */   {
/*  32 */     log.debug("Requesting next move for player " + this.value);
/*     */ 
/*  34 */     int[] childFields = (int[])board.getFields().clone();
/*  35 */     int nextPlayer = board.getNextPlayer();
/*     */     Board child;
/*  40 */     if (this.value == -1)
/*     */     {
/*  42 */       log.debug("Reversing board fields");
/*     */ 
/*  44 */       for (int i = 0; i < board.getSize(); i++) {
/*  45 */         childFields[i] = (-1 * childFields[i]);
/*     */       }
/*     */ 
/*  48 */       nextPlayer = 1;
/*     */ 
/*  50 */       child = new TicBoard(childFields, nextPlayer);
/*     */     }
/*     */     else
/*     */     {
/*  54 */       child = board;
/*     */     }
/*     */ 
/*  57 */     int bestMove = miniMax(child);
/*     */ 
/*  59 */     log.debug("Next move is: " + bestMove);
/*     */ 
/*  61 */     board.makeMove(bestMove);
/*     */ 
/*  63 */     log.debug("New board state is: " + Arrays.toString(board.getFields()));
/*     */ 
/*  65 */     return board;
/*     */   }
/*     */ 
/*     */   private int miniMax(Board board)
/*     */   {
/*  70 */     log.debug("Entering minimax");
/*     */ 
/*  72 */     ArrayList<Integer> legalMoves = board.getLegalMoves();
/*     */ 
/*  74 */     int bestMove = ((Integer)legalMoves.get(0)).intValue();
/*  75 */     double bestValue = (-1.0D / 0.0D);
/*     */ 
/*  77 */     log.debug("WTF - bestValue=" + bestValue);
/*     */ 		

			  for (Integer legalMove : legalMoves) {
				 int[] childFields = (int[])board.getFields().clone();
/*  82 */       int nextPlayer = board.getNextPlayer();
/*     */ 
/*  84 */       Board child = new TicBoard(childFields, nextPlayer);
/*  85 */       child.makeMove(legalMove.intValue());
/*     */ 
/*  87 */       double childValue = -miniMaxRec(child, 0);
/*     */ 
/*  89 */       if (log.isDebugEnabled()) {
/*  90 */         log.debug("childValue=" + String.format("%.5f", new Object[] { Double.valueOf(childValue) }) + " for move=" + legalMove + 
/*  91 */           " on board=" + Arrays.toString(child.getFields()));
/*     */       }
/*     */ 
/*  95 */       if (childValue <= bestValue)
/*     */         continue;
/*  97 */       log.debug("Founimport java.util.List;d a better value childvalue=" + String.format("%.5f", new Object[] { Double.valueOf(childValue) }) + " bestValue=" + 
/*  98 */         String.format("%.5f", new Object[] { Double.valueOf(bestValue) }));
/*  99 */       bestMove = legalMove.intValue();
/* 100 */       bestValue = childValue;
/*     */     }
/*     */ 
/* 104 */     return bestMove;
/*     */   }
/*     */ 
/*     */   private double miniMaxRec(Board board, int depth)
/*     */   {
/* 109 */     log.debug("Entering recursive minimax, depth=" + depth);
/*     */ 
/* 111 */     if ((depth >= 2) || (this.evaluator.isGameOver(board))) {
/* 112 */       return evaluateBoard(board, depth);
/*     */     }
/* 114 */     log.debug("Game not over yet, proceeding...");
/*     */ 
/* 116 */     double bestValue = (-1.0D / 0.0D);
/*     */ 
/*     */ 		
				ArrayList<Integer> legalMoves = board.getLegalMoves();	

				for (Integer legalMove : legalMoves) {
/* 124 */       int[] childFields = (int[])board.getFields().clone();
/* 125 */       int nextPlayer = board.getNextPlayer();
/*     */ 
/* 127 */       Board child = new TicBoard(childFields, nextPlayer);
/* 128 */       child.makeMove(legalMove.intValue());
/*     */ 
/* 130 */       double childValue = -miniMaxRec(child, depth + 1);
/*     */ 
/* 132 */       bestValue = Math.max(childValue, bestValue);
/*     */ 
/* 136 */       log.debug("Depth=" + depth + ", best value so far = " + bestValue);
/*     */     }
/*     */ 
/* 139 */     return bestValue;
/*     */   }
/*     */ 
/*     */   private double evaluateBoard(Board board, int depth)
/*     */   {
/* 144 */     int[] fields = (int[])board.getFields().clone();
/*     */ 
/* 146 */     log.debug("Evaluating board, original fields=" + Arrays.toString(fields));
/*     */ 
/* 149 */     if (depth % 2 == 0) {
/* 150 */       for (int i = 0; i < board.getSize(); i++) {
/* 151 */         fields[i] = (-1 * fields[i]);
/*     */       }
/*     */ 
/* 154 */       log.debug("Evaluating board as for opponent, reversed fields=" + Arrays.toString(fields));
/*     */     }
/*     */ 
/* 157 */     double[] networkInput = new double[this.network.getInput().getNeurons().size()];
/* 158 */     double[] networkOutput = new double[this.network.getOutput().getNeurons().size()];
/*     */ 
/* 160 */     generateInput(networkInput, fields);
/*     */ 
/* 162 */     this.network.getInput().feedInput(networkInput);
/* 163 */     this.network.getInput().initFeedForward();
/*     */ 
/* 165 */     networkOutput = this.network.getOutput().retreiveOutput();
/*     */ 
/* 167 */     this.network.resetNeurons();
/*     */ 
/* 169 */     if (depth % 2 == 1) {
/* 170 */       log.debug("Evaluated board (self) to output=" + networkOutput[0]);
/* 171 */       return networkOutput[0];
/*     */     }
/* 173 */     log.debug("Evaluated board (oppo) to output=" + networkOutput[0]);
/* 174 */     return networkOutput[0];
/*     */   }
/*     */ 
/*     */   private void generateInput(double[] ni, int[] b)
/*     */   {
/* 190 */     for (int i = 0; i < b.length; i++)
/* 191 */       if (b[i] == 1) {
/* 192 */         ni[(i * 2)] = 1.0D;
/* 193 */         ni[(i * 2 + 1)] = -1.0D;
/* 194 */       } else if (b[i] == -1) {
/* 195 */         ni[(i * 2)] = -1.0D;
/* 196 */         ni[(i * 2 + 1)] = 1.0D;
/*     */       } else {
/* 198 */         ni[(i * 2)] = -1.0D;
/* 199 */         ni[(i * 2 + 1)] = -1.0D;
/*     */       }
/*     */   }
/*     */ 
/*     */   public int getValue()
/*     */   {
/* 207 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(int value)
/*     */   {
/* 212 */     this.value = value;
/*     */   }
/*     */ }
