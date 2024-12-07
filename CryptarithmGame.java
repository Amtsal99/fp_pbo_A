import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CryptarithmGame extends JFrame {
    private GameLogic gameLogic = new GameLogic();
    private boolean isPlayer1Turn = true;

    private JLabel questionLabel, timerLabel, player1ScoreLabel, player2ScoreLabel;
    private JTextField answerInput;
    private JButton submitButton;
    private Timer timer;
    private int timeLeft = 120;

    public CryptarithmGame() {
        setTitle("Cryptarithm Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel("Question: ", SwingConstants.CENTER);
        timerLabel = new JLabel("Time Left: 120s", SwingConstants.CENTER);
        player1ScoreLabel = new JLabel("Player 1 Score: 0", SwingConstants.CENTER);
        player2ScoreLabel = new JLabel("Player 2 Score: 0", SwingConstants.CENTER);
        answerInput = new JTextField();
        submitButton = new JButton("Submit Answer");

        add(questionLabel);
        add(timerLabel);
        add(answerInput);
        add(submitButton);
        add(player1ScoreLabel);
        add(player2ScoreLabel);

        startNewTurn();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
    }

    private void startNewTurn() {
        gameLogic.generateQuestion();
        questionLabel.setText("Question: " + gameLogic.getQuestion());
        answerInput.setText("");
        timeLeft = 120;

        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (timeLeft > 0) {
                        timerLabel.setText("Time Left: " + timeLeft + "s");
                        timeLeft--;
                    } else {
                        timer.cancel();
                        handleSubmit();
                    }
                });
            }
        }, 0, 1000);

        gameLogic.startTurn();
    }

    private void handleSubmit() {
        timer.cancel();

        String playerAnswer;
        try {
            playerAnswer = answerInput.getText().trim();
        } catch (NumberFormatException e) {
            playerAnswer = "";
        }

        gameLogic.calculateScore(isPlayer1Turn, playerAnswer);

        if (isPlayer1Turn) {
            player1ScoreLabel.setText("Player 1 Score: " + gameLogic.getPlayer1Score());
        } else {
            player2ScoreLabel.setText("Player 2 Score: " + gameLogic.getPlayer2Score());
        }

        if (gameLogic.checkWin()) {
            String winner = isPlayer1Turn ? "Player 1" : "Player 2";
            JOptionPane.showMessageDialog(this, winner + " wins!");
            System.exit(0);
        } else {
            isPlayer1Turn = !isPlayer1Turn;
            startNewTurn();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CryptarithmGame game = new CryptarithmGame();
            game.setVisible(true);
        });
    }
}
