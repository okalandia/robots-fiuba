/*     */ package genetic;
/*     */ 
/*     */ import logic.PopulationStats;
/*     */ import neural.Layer;
/*     */ import neural.Network;
/*     */ import neural.Neuron;
/*     */ import neural.Synaps;
/*     */ import neural.TicNetwork;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Knowledge
/*     */ {
/*  19 */   private static Logger log = Logger.getLogger(Knowledge.class);
/*     */ 
/*  21 */   Random random = new Random();
/*     */ 
/*     */   public Network chooseMate(List<Network> population)
/*     */   {
/*  25 */     return (Network)population.get(this.random.nextInt(10));
/*     */   }
/*     */ 
/*     */   public Network mate(Network father, Network mother)
/*     */   {
/*  30 */     Network child = new TicNetwork();
/*     */ 
/*  32 */     int nrOfSynaps = 0;
/*     */ 
/*  34 */     for (int i = 0; i < father.getLayers().size() - 1; i++)
/*     */     {
/*  36 */       int thisLayer = ((Layer)father.getLayers().get(i)).getNeurons().size();
/*  37 */       int nextLayer = ((Layer)father.getLayers().get(i + 1)).getNeurons().size();
/*     */ 
/*  39 */       nrOfSynaps += thisLayer * nextLayer;
/*     */     }
/*     */ 
/*  42 */     double[] fatherGenes = new double[nrOfSynaps];
/*  43 */     double[] motherGenes = new double[nrOfSynaps];
/*  44 */     double[] childGenes = new double[nrOfSynaps];
/*     */ 
/*  46 */     int indx = 0;
/*  47 */     for (Layer layer : father.getLayers()) {
/*  48 */       for (Neuron neuron : layer.getNeurons()) {
/*  49 */         for (Synaps synaps : neuron.getSynapses()) {
/*  50 */           fatherGenes[(indx++)] = synaps.getWeight();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  55 */     indx = 0;
/*  56 */     for (Layer layer : mother.getLayers()) {
/*  57 */       for (Neuron neuron : layer.getNeurons()) {
/*  58 */         for (Synaps synaps : neuron.getSynapses()) {
/*  59 */           motherGenes[(indx++)] = synaps.getWeight();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  64 */     for (int i = 0; i < nrOfSynaps; i++)
/*     */     {
/*  66 */       if (this.random.nextDouble() < 0.5D)
/*  67 */         childGenes[i] = fatherGenes[i];
/*     */       else {
/*  69 */         childGenes[i] = motherGenes[i];
/*     */       }
/*     */     }
/*  72 */     indx = 0;
/*  73 */     for (Layer layer : child.getLayers()) {
/*  74 */       for (Neuron neuron : layer.getNeurons()) {
/*  75 */         for (Synaps synaps : neuron.getSynapses()) {
/*  76 */           synaps.setWeight(childGenes[(indx++)]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  81 */     if (log.isDebugEnabled())
/*     */     {
/*  83 */       log.info("Father genes=" + Arrays.toString(fatherGenes));
/*  84 */       log.info("Mother genes=" + Arrays.toString(motherGenes));
/*  85 */       log.info("Child genes=" + Arrays.toString(childGenes));
/*     */     }
/*     */ 
/*  88 */     return child;
/*     */   }
/*     */ 
/*     */   public void mutate(Network child)
/*     */   {
/*  93 */     for (Layer layer : child.getLayers())
/*  94 */       for (Neuron neuron : layer.getNeurons())
/*  95 */         for (Synaps synaps : neuron.getSynapses())
/*  96 */           if (this.random.nextDouble() > 0.8D)
/*  97 */             synaps.setWeight(Math.random() * 2.0D - 1.0D);
/*     */   }
/*     */ 
/*     */   public List<Network> naturalSelection(List<Network> newPop)
/*     */   {
/* 102 */     log.debug("Starting natural selection.");
/*     */ 
/* 104 */     String debug = "";
/* 105 */     if (log.isDebugEnabled()) {
/* 106 */       for (Network network : newPop) {
/* 107 */         debug = debug + network.getValue() + ";";
/*     */       }
/* 109 */       log.debug("Values of networks( " + newPop.size() + "): " + debug);
/*     */     }
/*     */ 
/* 112 */     Collections.sort(newPop, new NetworkComparator());
/*     */ 
/* 114 */     newPop = newPop.subList(0, 10);
/*     */ 
/* 116 */     debug = "";
/* 117 */     if (log.isDebugEnabled()) {
/* 118 */       for (Network network : newPop) {
/* 119 */         debug = debug + network.getValue() + ";";
/*     */       }
/* 121 */       log.debug("Sorted newpop of networks: " + debug);
/*     */     }
/*     */ 
/* 124 */     PopulationStats.logStats(newPop);
/* 125 */     PopulationStats.logGenerations(newPop);
/* 126 */     PopulationStats.logGamestats(newPop);
/*     */ 
/* 128 */     for (Network network : newPop) {
/* 129 */       network.setValue(0);
/* 130 */       network.increaseGeneration();
/* 131 */       network.winsOne = 0;
/* 132 */       network.drwsOne = 0;
/* 133 */       network.lossOne = 0;
/* 134 */       network.winsTwo = 0;
/* 135 */       network.drwsTwo = 0;
/* 136 */       network.lossTwo = 0;
/*     */     }
/*     */ 
/* 139 */     debug = "";
/* 140 */     if (log.isDebugEnabled()) {
/* 141 */       for (Network network : newPop) {
/* 142 */         debug = debug + network.getValue() + ";";
/*     */       }
/* 144 */       log.debug("Reset values of networks: " + debug);
/*     */     }
/*     */ 
/* 147 */     return newPop;
/*     */   }
/*     */ }

