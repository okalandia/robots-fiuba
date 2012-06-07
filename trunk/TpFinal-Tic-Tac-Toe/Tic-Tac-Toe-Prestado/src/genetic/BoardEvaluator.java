package genetic;

import model.Board;

public abstract interface BoardEvaluator
{
  public abstract boolean isGameOver(Board paramBoard);

  public abstract int findWinner(Board paramBoard);
}