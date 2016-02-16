package model;

import java.awt.Point;

/**
 * This class allows a Tic-Tac-Toe player to play games against a variety of
 * AIs. It completely relies on the TicTacToeStrategy for its next move with the
 * desiredMove method that can "see" the game.
 * 
 * @author Mercer
 * 
 */
public class ComputerPlayer {

	private TicTacToeStrategy myStrategy;

	private String name;
	private RandomAI RandomStrategy;
	private StopperAI StopperStrategy;

	public ComputerPlayer(String name) {
		this.name = name;
		RandomStrategy = new RandomAI();
		StopperStrategy = new StopperAI();

		// This default TicTacToeStrategy can be changed with setStrategy
		myStrategy = RandomStrategy;
	}

	/**
	 * Change the AI for this ComputerPlayer
	 * 
	 * @param stategy
	 */
	public void setStrategy(TicTacToeStrategy strategy) {
		myStrategy = strategy;
	}

	/**
	 * Delegate to my strategy, which can "see" the game for my next move
	 * 
	 * @param theGame
	 *            The current state of the game when asked for a move
	 * 
	 * @return A java.awt.Point that store two ints: an x and a y
	 */
	public Point desiredMove(TicTacToeGame theGame) {
		return myStrategy.desiredMove(theGame);
	}

	/**
	 * Yes, you know what this does.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void ChangeStrategy() {
		if (myStrategy.equals(RandomStrategy))
			setStrategy(StopperStrategy);
		else {
			setStrategy(RandomStrategy);
		}
	}
}