/*     */ package logic;
/*     */ 
/*     */ import genetic.Brain;
/*     */ import model.NeuralPlayer;
/*     */ import model.Player;
/*     */ import model.RandomPlayer;
/*     */ import model.TicBoard;
/*     */ import neural.Network;
/*     */ import threading.ThreadPool;
/*     */ import java.util.List;
import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GeneticTrainer
/*     */ {
/*  18 */   private static Logger log = Logger.getLogger(GeneticTrainer.class);
/*     */ 
/*  23 */   public static int WINVALUE = 10;
/*  24 */   public static int DRAWVALUE = 7;
/*  25 */   public static int LOSSVALUE = -10;
/*     */ 
/*  28 */   public static int WINVALUE2 = 10;
/*  29 */   public static int DRAWVALUE2 = 7;
/*  30 */   public static int LOSSVALUE2 = -10;
/*     */ 
/*  32 */   public static int NO_OF_THREADS = 2;
/*     */   private static final int RANDOMGAMES = 100;
/*     */   Brain brain;
/*     */   Competition comp;
/*     */ 
/*     */   public GeneticTrainer()
/*     */   {
/*  41 */     this.brain = new Brain();
/*  42 */     this.brain.initPopulation();
/*  43 */     this.comp = new Competition();
/*     */   }
/*     */ 
/*     */   public void startTraining(int nrOfIterations)
/*     */   {
/*  48 */     log.info("Starting training.");
/*     */ 
/*  50 */     int i = 0;
/*  51 */     while (i < nrOfIterations)
/*     */     {
/*  53 */       int f = 0;
/*     */ 
/*  55 */       log.info("Training iteration=" + i++);
/*     */ 
/*  57 */       log.debug("Starting brain iteration.");
/*     */ 
/*  59 */       this.brain.iteration();
/*     */ 
/*  61 */       List<Network> pop = this.brain.getPopulation();
/*     */ 
/*  63 */       PopulationStats.logNetwork((Network)pop.get(0));
/*     */ 
/*  65 */       log.debug("Starting training with popluation of size=" + pop.size());
/*     */ 
/*  69 */       ThreadPool pool = new ThreadPool(NO_OF_THREADS);
/*     */ 
	
				for (Network netOne : pop)
/*     */       {
/*  73 */         log.debug("Training network=" + f++);
/*     */ 
/*  76 */         pool.runTask(new CompetitionThread(netOne, pop));
/*     */       }
/*     */ 
/* 111 */       pool.join();
/* 112 */       log.info("pool finished");
/*     */ 
/* 114 */       PopulationStats.logStats(pop);
/*     */ 
/* 118 */       if (log.isDebugEnabled())
/*     */         continue;
/* 120 */       quicTest(new NeuralPlayer(1, (Network)pop.get(0)), new RandomPlayer(-1));
/* 121 */       quicTest(new RandomPlayer(1), new NeuralPlayer(-1, (Network)pop.get(0)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void quicTest(Player plOne, Player plTwo)
/*     */   {
/* 128 */     Logger netlog = Logger.getLogger("networks");
/*     */ 
/* 130 */     Competition comp = new Competition();
/*     */ 
/* 132 */     int wins = 0;
/* 133 */     int drws = 0;
/* 134 */     int loss = 0;
/*     */ 
/* 136 */     for (int i = 0; i < 100; i++)
/*     */     {
/* 138 */       Player winner = comp.competition(plOne, plTwo, new TicBoard());
/*     */ 
/* 140 */       if (winner == plOne)
/* 141 */         wins++;
/* 142 */       else if (winner == plTwo)
/* 143 */         loss++;
/*     */       else {
/* 145 */         drws++;
/*     */       }
/*     */     }
/* 148 */     String result = "Network result against random player wins=" + wins + ";drws=" + drws + ";loss=" + loss;
/*     */ 
/* 150 */     netlog.debug(result);
/* 151 */     log.info(result);
/*     */   }
/*     */ }
