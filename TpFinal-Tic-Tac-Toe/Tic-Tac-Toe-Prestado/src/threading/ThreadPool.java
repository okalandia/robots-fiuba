/*     */ package threading;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class ThreadPool extends ThreadGroup
/*     */ {
/*     */   private boolean isAlive;
/*     */   private LinkedList<Runnable> taskQueue;
/*     */   private int threadID;
/*     */   private static int threadPoolID;
/*     */ 
/*     */   public ThreadPool(int numThreads)
/*     */   {
/*  23 */     super("ThreadPool-" + threadPoolID++);
/*  24 */     setDaemon(true);
/*     */ 
/*  26 */     this.isAlive = true;
/*     */ 
/*  28 */     this.taskQueue = new LinkedList<Runnable>();
/*  29 */     for (int i = 0; i < numThreads; i++)
/*  30 */       new PooledThread().start();
/*     */   }
/*     */ 
/*     */   public synchronized void runTask(Runnable task)
/*     */   {
/*  48 */     if (!this.isAlive) {
/*  49 */       throw new IllegalStateException();
/*     */     }
/*  51 */     if (task != null) {
/*  52 */       this.taskQueue.add(task);
/*  53 */       notify();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized Runnable getTask() throws InterruptedException
/*     */   {
/*  59 */     while (this.taskQueue.size() == 0) {
/*  60 */       if (!this.isAlive) {
/*  61 */         return null;
/*     */       }
/*  63 */       wait();
/*     */     }
/*  65 */     return (Runnable)this.taskQueue.removeFirst();
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/*  74 */     if (this.isAlive) {
/*  75 */       this.isAlive = false;
/*  76 */       this.taskQueue.clear();
/*  77 */       interrupt();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void join()
/*     */   {
/*  88 */     synchronized (this) {
/*  89 */       this.isAlive = false;
/*  90 */       notifyAll();
/*     */     }
/*     */ 
/*  94 */     Thread[] threads = new Thread[activeCount()];
/*  95 */     int count = enumerate(threads);
/*  96 */     for (int i = 0; i < count; i++)
/*     */       try {
/*  98 */         threads[i].join();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   private class PooledThread extends Thread
/*     */   {
/*     */     public PooledThread()
/*     */     {
/* 111 */       super("PooledThread-" + ThreadPool.this.threadID++);
/*     */     }
/*     */ 
/*     */     public void run() {
/* 115 */       while (!isInterrupted())
/*     */       {
/* 118 */         Runnable task = null;
/*     */         try {
/* 120 */           task = ThreadPool.this.getTask();
/*     */         }
/*     */         catch (InterruptedException localInterruptedException)
/*     */         {
/*     */         }
/*     */ 
/* 126 */         if (task == null) {
/* 127 */           return;
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 132 */           task.run();
/*     */         } catch (Throwable t) {
/* 134 */           ThreadPool.this.uncaughtException(this, t);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

