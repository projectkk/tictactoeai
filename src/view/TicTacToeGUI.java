package view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.ComputerPlayer;
import model.RandomAI;
import model.StopperAI;
import model.TicTacToeGame;

/**
 * Allows the user to play TicTacToe against the computer.
 * 
 * When you are done with this class, it will have different AIs to play
 * against, different views to see the game through, the ability to start a new
 * game, and the ability to run a tournament, all available as options in the
 * JMenu.
 * 
 * @author mercer
 */
@SuppressWarnings({ "serial", "unused" })
public class TicTacToeGUI extends JFrame implements WindowListener {

	public static void main(String[] args) {
		TicTacToeGUI g = new TicTacToeGUI();
		g.setVisible(true);
	}

	private TicTacToeGame theGame;
	private ButtonView buttonView;
	private GraphicalView graphicalView;
	private GameStatistics GameStats;
	private File myFile = new File("TicTacToe.out");
	private int ComputerScore;
	private int HumanScore;
	private int Ties;
	private boolean isButtonView;
	private boolean isGraphicView;

	public static final int width = 300;
	public static final int height = 360;

	public TicTacToeGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocation(100, 40);
		this.setTitle("Tic Tac Toe");

		setupMenus();
		initializeGameForTheFirstTime();
		setupGameStats();
		buttonView = new ButtonView(theGame, width, height);
		graphicalView = new GraphicalView(theGame, width, height);
		addObservers();
		// Set default view
		add(buttonView);
		isButtonView = true;
		isGraphicView = false;
		addWindowListener(this);
	}

	private void setupGameStats() {
		if (myFile.exists()) {
			try {
				FileInputStream FileIn = new FileInputStream(myFile);
				ObjectInputStream ObjectIn = new ObjectInputStream(FileIn);
				GameStats = (GameStatistics) ObjectIn.readObject();
				ObjectIn.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();

			}
			ComputerScore = GameStats.getComputerScore();
			HumanScore = GameStats.getHumanScore();
			Ties = GameStats.getTies();
		} else {
			ComputerScore = 0;
			HumanScore = 0;
			Ties = 0;
		}

	}

	private GameStatistics getFinalScores() {
		int totalComputerScore = 0;
		int totalHumanScore = 0;
		int totalTiedScore = 0;
		totalComputerScore = buttonView.getComputerScore()
				+ graphicalView.getComputerScore() + ComputerScore;
		totalHumanScore = buttonView.getHumanScore()
				+ graphicalView.getHumanScore() + HumanScore;
		totalTiedScore = buttonView.getTiedScore()
				+ graphicalView.getTiedScore() + Ties;

		return new GameStatistics(totalHumanScore, totalComputerScore,
				totalTiedScore);
	}

	public void saveGameStats() {
		if (!myFile.exists())
			myFile = new File("TicTacToe.out");

		try {
			FileOutputStream fileout = new FileOutputStream(myFile);
			ObjectOutputStream objectout = new ObjectOutputStream(fileout);

			GameStatistics obj = getFinalScores();
			objectout.writeObject(obj);
			objectout.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addObservers() {
		theGame.addObserver(buttonView);
		theGame.addObserver(graphicalView);
	}

	public void initializeGameForTheFirstTime() {
		theGame = new TicTacToeGame();
		// This event driven program will always have
		// a computer player who takes the second turn
		ComputerPlayer computerPlayer = new ComputerPlayer("Random Player");
		computerPlayer.setStrategy(new RandomAI());
		theGame.setComputerPlayer(computerPlayer);
	}

	private void setupMenus() {
		JMenuItem menu = new JMenu("Options");
		// Add two Composites to a Composite
		JMenuItem newGame = new JMenuItem("New Game");
		menu.add(newGame);
		JMenuItem strategy = new JMenu("Change Strategy");

		JMenuItem random = new JMenuItem("Random Strategy");
		strategy.add(random);
		JMenuItem stopper = new JMenuItem("Stopper Strategy");
		strategy.add(stopper);
		menu.add(strategy);

		JMenuItem view = new JMenu("Change View");
		JMenuItem Button = new JMenuItem("Button View");
		view.add(Button);
		JMenuItem Graphics = new JMenuItem("Graphic View");
		view.add(Graphics);
		menu.add(view);
		JMenuItem stats = new JMenuItem("Stats");
		menu.add(stats);
		JMenuItem battle = new JMenuItem("Battle");
		menu.add(battle);

		// TODO Add menu selections

		// Set the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(menu);

		// Add the same listener to all menu items requiring action
		MenuItemListener menuListener = new MenuItemListener();
		Button.addActionListener(menuListener);
		Graphics.addActionListener(menuListener);
		newGame.addActionListener(menuListener);
		random.addActionListener(menuListener);
		stopper.addActionListener(menuListener);
		stats.addActionListener(menuListener);
		battle.addActionListener(menuListener);
		// TODO Add listeners
	}

	public void NewGameSelectionGUI() {
		if (theGame.stillRunning()) {
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this,
					"Quit and Start a new game?", "New Game", dialogButton);
			if (dialogResult == 0) {
				// start new game
				theGame.startNewGame();
			}
		} else {
			theGame.startNewGame();
		}

	}

	public void NewStrategySelectionRandomGUI() {

		theGame.getComputerPlayer().setStrategy(new RandomAI());
		JOptionPane.showMessageDialog(null,
				"Now playing against Random Strategy");

	}

	public void NewStrategySelectionStopperGUI() {
		theGame.getComputerPlayer().setStrategy(new StopperAI());
		JOptionPane.showMessageDialog(null,
				"Now playing against Stopper Strategy");
	}

	public void NewGameStatsSelectionGUI() {
		GameStatistics stats = getFinalScores();
		int totalNumGames = stats.getComputerScore() + stats.getHumanScore()
				+ stats.getTies();
		double percentHumans = (stats.getHumanScore() * 100.0f) / totalNumGames;
		double percentComputer = (stats.getComputerScore() * 100.0f)
				/ totalNumGames;
		double percentTied = (stats.getTies() * 100.0f) / totalNumGames;
		DecimalFormat df = new DecimalFormat("#.##");
		String human = "Human Wins: " + stats.getHumanScore() + " ("
				+ df.format(percentHumans) + "%) \n";
		String computer = "Computer Wins: " + stats.getComputerScore() + " ("
				+ df.format(percentComputer) + "%) \n";
		String tied = "Ties: " + stats.getTies() + " ("
				+ df.format(percentTied) + "%) ";

		JOptionPane.showMessageDialog(null, "Games Played: " + totalNumGames
				+ "\n" + human + computer + tied);
	}

	private void NewBattleSelectionGUI() {
		int tied = 0;
		int RandomWin = 0;
		int StopperWin = 0;
		boolean gameover = false;

		TicTacToeGame Battle = new TicTacToeGame();
		ComputerPlayer Player = new ComputerPlayer("Random Player");
		ComputerPlayer Player2 = new ComputerPlayer("Stopper Player");
		Player.setStrategy(new RandomAI());
		Player2.setStrategy(new StopperAI());

		Battle.setComputerPlayer(Player);

		for (int i = 0; i < 500; i++) {
			gameover = false;
			while (gameover == false) {

				Point p2 = Player2.desiredMove(Battle);
				Battle.choose(p2.x, p2.y);

				if (Battle.tied()) {
					tied++;
					gameover = true;
				} else if (Battle.didWin('X')) {
					StopperWin++;
					gameover = true;
				} else {
					// If the game is not over, let the computer player choose
					// This algorithm assumes the computer player always
					// goes after the human player and is represented by 'O',
					// not
					// 'X'
					Point play = Player.desiredMove(Battle);
					Battle.choose(play.x, play.y);
					if (Battle.didWin('O')) {
						RandomWin++;
						gameover = true;
					}
				}
			}// end while
			Battle.startNewGame();

		}// end 500 Games
		JOptionPane
				.showMessageDialog(null, "RandomAI wins: " + RandomWin
						+ "\n StopperAI wins: " + StopperWin
						+ "\n Tied Games: " + tied);
	}

	public void NewViewSelectionButtonViewGUI() {
		if (isGraphicView == true) {
			isGraphicView=false;
			isButtonView=true;
			this.remove(graphicalView);
			this.add(buttonView);
			buttonView.updateButtons();
			this.repaint();
			this.validate();
		}
	}

	public void NewViewSelectionGraphicViewGUI() {
		if (isButtonView) {
			isButtonView=false;
			isGraphicView=true;
			this.remove(buttonView);
			this.add(graphicalView);
			// update
			this.repaint();
			this.validate();
		}
	}

	private class MenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = ((JMenuItem) e.getSource()).getText();
			System.out.println(text);

			if (text.equals("New Game")) {
				NewGameSelectionGUI();
			} else if (text.equals("Random Strategy")) {
				NewStrategySelectionRandomGUI();
			} else if (text.equals("Stopper Strategy")) {
				NewStrategySelectionStopperGUI();
			} else if (text.equals("Stats")) {
				NewGameStatsSelectionGUI();
			} else if (text.equals("Battle")) {
				NewBattleSelectionGUI();
			} else if (text.equals("Button View")) {
				NewViewSelectionButtonViewGUI();
				// working on
			} else if (text.equals("Graphic View")) {
				NewViewSelectionGraphicViewGUI();
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------------------------

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		saveGameStats();

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		saveGameStats();// ////////////////////////////////

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}