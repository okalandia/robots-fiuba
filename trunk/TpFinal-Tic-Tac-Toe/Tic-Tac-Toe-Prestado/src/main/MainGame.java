package main;

import logic.Competition;
import model.Board;
import model.HumanPlayer;
import model.NeuralPlayer;
import model.Player;
import model.TicBoard;
import neural.Layer;
import neural.Neuron;
import neural.Synaps;
import neural.TicNetwork;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import org.apache.log4j.PropertyConfigurator;
import org.jconfig.utils.ResourceLocator;

public class MainGame
{
  public static void main(String[] args)
    throws IOException
  {
    Properties logProperties = new Properties();

    ResourceLocator locator = new ResourceLocator("");
    InputStream log4jStream = locator.findResource("log4j.properties");
    try
    {
      logProperties.load(log4jStream);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace(); 
    }
    PropertyConfigurator.configure(logProperties);

    String intro = "Gameboard positions are marked as follows:\n";

    intro = intro + " 0 | 1 | 2 \n";
    intro = intro + "-----------\n";
    intro = intro + " 3 | 4 | 5 \n";
    intro = intro + "-----------\n";
    intro = intro + " 6 | 7 | 8 \n\n";

    System.out.println(intro);

    Random r = new Random();
    String playMore;
    do { int neuralNetIndex = r.nextInt(10);

      String neuralNetFilename = "ann_n_";

      neuralNetFilename = neuralNetFilename.replace("_n_", Integer.toString(neuralNetIndex));

     InputStream io = locator.findResource(neuralNetFilename);

      Scanner in = new Scanner(io);
      Locale loc = new Locale("en", "US");

      in.useLocale(loc);
      in.useDelimiter(";");

      TicNetwork network = new TicNetwork();

      List<Layer> layers = network.getLayers();
      
      for (Layer layer : layers) {
        for (Neuron neuron : layer.getNeurons()) {
          for (Synaps synaps : neuron.getSynapses())
          {
        	  synaps.setWeight(in.nextDouble());
          }
        }
      }

      in = new Scanner(System.in);

      System.out.println("Who will start the game (1-player, 0-computer):");
      int order = in.nextInt();

      Competition comp = new Competition();

      Object board = new TicBoard();
      Object two;
      int human;
      Player one;
      if (order == 1)
      {
        one = new HumanPlayer();
        one.setValue(1);
        human = 1;

        two = new NeuralPlayer(-1, network);
      }
      else
      {
        two = new HumanPlayer();
        ((Player)two).setValue(-1);
        human = -1;

        one = new NeuralPlayer(1, network);
      }

      Player winner = comp.competition(one, (Player)two, (Board)board);

      if (winner != null)
      {
        String result = winner.getValue() == human ? " Player" : " Computer";
        System.out.println(result + " won the game!");
      }
      else {
        System.out.println(" Game ended with draw!");
      }System.out.println(board);

      System.out.println("\nDo you want to play another game?(y/n)");
      playMore = in.next();
    }
    while (playMore.equals("y"));
  }
}
