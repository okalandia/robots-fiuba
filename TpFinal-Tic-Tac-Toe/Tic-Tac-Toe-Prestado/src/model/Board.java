package model;

import java.util.ArrayList;

public abstract interface Board
{
  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = -1;
  public static final int EMPTY_FIELD = 1;

  public abstract int getSize();

  public abstract int getValue(int paramInt);

  public abstract void setValue(int paramInt1, int paramInt2);

  public abstract void makeMove(int paramInt);

  public abstract int[] getFields();

  public abstract void setFields(int[] paramArrayOfInt);

  public abstract int getNextPlayer();

  public abstract void setNextPlayer(int paramInt);

  public abstract boolean isLegalMove(int paramInt);

  public abstract ArrayList<Integer> getLegalMoves();
}