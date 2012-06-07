package model;

public abstract interface Player
{
  public static final int OPPONENT = -1;
  public static final int SELF = 1;

  public abstract int getValue();

  public abstract void setValue(int paramInt);

  public abstract Board makeNextMove(Board paramBoard);
}

/* Location:           C:\Users\Vero\Desktop\
 * Qualified Name:     ee.ut.aa.neuraltic.model.Player
 * JD-Core Version:    0.6.0
 */