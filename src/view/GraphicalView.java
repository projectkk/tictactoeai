package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ComputerPlayer;
import model.OurObserver;
import model.TicTacToeGame;

public class GraphicalView  extends JPanel implements OurObserver, SaveScores{
	private int row1Bottom;
	private int row2Bottom;
	private int row3Bottom;
	private int col1right;
	private int col2right;
	private int col3right;
	private int YmenuAndTitleCompensator;
	private int XmenuAndTitleCompensator;
	private Font myFont;
	
	private int ComputerScore=0;
	private int HumanScore = 0;
	private int TiedScore = 0;
	private TicTacToeGame theGame;
	private int width;
	private int height;
	private ComputerPlayer computerPlayer;
	private JLabel[][] board;
	private JButton stateButton= new JButton("Click your move");
	
	public GraphicalView(TicTacToeGame tictactoeGame, int width, int height){
		theGame = tictactoeGame;
		this.width= width;
		this.height = height;
		computerPlayer = theGame.getComputerPlayer();
		initializeGridPanel();
	}



	private void initializeGridPanel() {
		int size = theGame.size();
		JPanel total = new JPanel();
		total.setLayout(new GridLayout(size, size, 5,5));
		Font myFont = new Font("Arial", Font.TRUETYPE_FONT, 40);
		MouseListener listener = new ListenToMouse();
		board = new JLabel[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j<size; j++){
				board[i][j] = new JLabel();
				board[i][j].setFont(myFont);
				board[i][j].addMouseListener(listener);
				board[i][j].setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
				total.add(board[i][j]);
			}
		}
		this.setLayout(null);
		total.setLocation(10, 15);
		total.setSize(width-30, height-110);
		
		this.add(total);

		stateButton.setSize(200, 40);
		stateButton.setFont(new Font("Arial", Font.BOLD, 18));
		stateButton.setEnabled(false);
		stateButton.setBackground(Color.PINK);
		stateButton.setLocation(40, height - 100);
		this.add(stateButton);
		this.repaint();
	}
	
	public void updateLabels(){
		char[][] temp = theGame.getTicTacToeBoard();
		for(int i = 0; i <temp.length; i++){
			for(int j = 0; j<temp.length; j++){
				String text =""+temp[i][j];
				if(text.equals("X")||text.equals("O")){
					board[i][j].setText(text);
					board[i][j].setEnabled(false);
					if(text.equals("X"))
						board[i][j].setBorder(BorderFactory.createEtchedBorder(Color.red, Color.RED));
					else
						board[i][j].setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.BLUE));
				}
		
			
			}
		}
	}//end updateLables
	public void resetLabels(boolean enable){
		for(int i = 0; i < theGame.size(); i++){
			for(int j= 0; j< theGame.size(); j++){
				board[i][j].setText("");
				board[i][j].setBorder(null);
				board[i][j].setEnabled(enable);
				board[i][j].setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
			}
		}
	}
	
	
	
private class ListenToMouse implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel labelClicked = (JLabel) e.getSource();
		
		for(int i = 0; i < board.length; i++){
			for (int j =0; j<board[i].length; j++){
				if(board[i][j] == labelClicked && board[i][j].isEnabled()){
					theGame.choose(i, j);
				}
			}
		}//end loop i
		if (theGame.tied()) {
			stateButton.setText("Tied");
			freezeBoard();
			TiedScore++;
		} else if (theGame.didWin('X')) {
			stateButton.setText("X wins");
			freezeBoard();
			HumanScore++;
		} else {
			// If the game is not over, let the computer player choose
			// This algorithm assumes the computer player always
			// goes after the human player and is represented by 'O', not
			// 'X'
			Point play = computerPlayer.desiredMove(theGame);
			theGame.choose(play.x, play.y);
			if (theGame.didWin('O')) {
				stateButton.setText("O wins");
				freezeBoard();
				ComputerScore++;
			}
		}
		
	}
	private void freezeBoard(){
		char[][] temp = theGame.getTicTacToeBoard();
		for(int i = 0; i <temp.length; i++){
			for(int j = 0; j<temp.length; j++){
				String text =""+temp[i][j];
				if(text.equals("X")||text.equals("O")){
					board[i][j].setText(text);
					board[i][j].setEnabled(false);
					if(text.equals("X"))
						board[i][j].setBorder(BorderFactory.createEtchedBorder(Color.red, Color.RED));
					else
						board[i][j].setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.BLUE));
				}
				else{
					board[i][j].setEnabled(false);
				}
		
			
			}//end loop j
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
		
	}
	
}


	@Override
	public int getHumanScore() {
		return HumanScore;
	}

	@Override
	public int getComputerScore() {
		return ComputerScore;
	}

	@Override
	public int getTiedScore() {
		return TiedScore;
	}



	@Override
	public void update() {
		if (theGame.maxMovesRemaining() == theGame.size() * theGame.size())
			resetLabels(true);

		if (!theGame.stillRunning())
			resetLabels(false);
		else {
			updateLabels();
			stateButton.setText("Click your move");
		}
		
	}
	
	
	
}
