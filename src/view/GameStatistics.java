package view;

import java.io.Serializable;

import javax.swing.JOptionPane;

import model.ComputerPlayer;
import model.OurObserver;
import model.TicTacToeGame;

public class GameStatistics implements Serializable{

	private int HumanScore;
	private int ComputerScore;
	private int Ties; 
	
	public GameStatistics(int humanscore, int computerscore, int ties){
		HumanScore = humanscore;
		ComputerScore = computerscore;
		Ties =ties;
	}
	public int getHumanScore(){
		return HumanScore;
	}
	public int getComputerScore(){
		return ComputerScore;
		
	}
	public int getTies(){
		return Ties;
	}


}
