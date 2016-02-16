package model;

// Use Java's Point class to store two ints: an x and a y
import java.awt.Point;

/**
 * 
 * Defines an interface to encapsulate a strategy for the game
 * Tic-Tac-Toe.
 *  
 * @author Mercer
 * 
 */
public interface TicTacToeStrategy {

	// The ComputerPlayer has access to "seeing" anything about
	// the game when it is given the game as an argument.
	public Point desiredMove(TicTacToeGame theGame);
}