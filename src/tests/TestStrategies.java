package tests;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import model.ComputerPlayer;
import model.RandomAI;
import model.TicTacToeGame;
import model.IGotNowhereToGoException;

import org.junit.Test;

public class TestStrategies {
 
  @Test(expected = IGotNowhereToGoException.class)
  public void showWhatHappensWhenA() {
    TicTacToeGame theGame = new TicTacToeGame();

    ComputerPlayer playerWithRanomStrategy = new ComputerPlayer("Random");
    playerWithRanomStrategy.setStrategy(new RandomAI());

    // Make one move
    Point aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);

    // and another
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and another
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and another
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and another
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and another 
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and another
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and an eight 
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);
    // and the ninth
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
    theGame.choose(aRandomSquare.x, aRandomSquare.y);

    System.out.println("Show the game board has no where a player can pick:");
    System.out.println(theGame.toString());
    // This should throw an exception since the board is filled 
    aRandomSquare = playerWithRanomStrategy.desiredMove(theGame);
  }

  @Test
  public void run1000TicTacToeGames() {
    ComputerPlayer random = new ComputerPlayer("Random");
    random.setStrategy(new RandomAI());
    ComputerPlayer AStopper = new ComputerPlayer("Random Two");
    AStopper.setStrategy(new RandomAI());

    int randomWins = 0;
    int intermediateWins = 0;
    int ties = 0;

    // 
    for (int game = 1; game <= 500; game++) {
      char winner = playOneGame(AStopper, random);
      if (winner == 'X')
        randomWins++;
      if (winner == 'O')
        intermediateWins++;
      if (winner == 'T')
        ties++;
    }

    for (int game = 1; game <= 500; game++) {
      char winner = playOneGame(random, AStopper);
      if (winner == 'X')
        intermediateWins++;
      if (winner == 'O')
        randomWins++;
      if (winner == 'T')
        ties++;
    }

    System.out.println("Two Computer Players with the same strategy, ");
    System.out.println("going first an the same number of times,");
    System.out.println("should have about the same number of wins.");
    System.out.println("===========================================");
    System.out.println("RandomAI wins: " + randomWins);
    System.out.println("Stopper wins: " + intermediateWins);
    System.out.println("Ties: " + ties);
    
    // Using the same strategy, these should be close.  Within 100? This could fail!
    assertTrue(100 + intermediateWins > randomWins);
  }

  private char playOneGame(ComputerPlayer first, ComputerPlayer second) {
    TicTacToeGame theGame = new TicTacToeGame();

    while (true) {
      Point firstsMove = first.desiredMove(theGame);
      assertTrue(theGame.choose(firstsMove.x, firstsMove.y));

      if (theGame.tied())
        return 'T';

      if (theGame.didWin('X'))
        return 'X';
      if (theGame.didWin('O'))
        return 'O';

      Point secondsMove = second.desiredMove(theGame);
      assertTrue(theGame.choose(secondsMove.x, secondsMove.y));

      if (theGame.tied())
        return 'T';

      if (theGame.didWin('X'))
        return 'X';
      if (theGame.didWin('O'))
        return 'O';
    }
  }
}