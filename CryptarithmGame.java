import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CryptarithmGame extends JFrame {
    ImageIcon appIcon;
    private JPanel questionPanel, answerInputPanel1, answerInputPanel2, answerInputPanel3, 
        submitPanel, hintPanel, player1ScorePanel, player2ScorePanel, endGamePanel;
    private GameLogic gameLogic = new GameLogic();
    private Helper helperFunc = new Helper();
    private boolean isPlayer1Turn = true;

    private JLabel questionLabel, timerLabel, valueFor1, valueFor2, valueFor3, 
        player1ScoreLabel, player2ScoreLabel;
    private JTextField answerInput1, answerInput2, answerInput3;
    private JButton submitButton, hintButton, endGameButton;
    private Timer timer;
    private int timeLeft = 120;

    public CryptarithmGame() {
        appIcon = new ImageIcon("appIcon.png");
        this.setIconImage(appIcon.getImage());
        setTitle("Cryptarithm Game");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        questionLabel = new JLabel("Question: ");
        timerLabel = new JLabel("Time Left: 120s");
        valueFor1 = new JLabel("Value for X1: ");
        valueFor2 = new JLabel("Value for X2: ");
        valueFor3 = new JLabel("Value for X3: ");
        answerInput1 = new JTextField();
        answerInput2 = new JTextField();
        answerInput3 = new JTextField();
        player1ScoreLabel = new JLabel("Player 1 Score: 0");
        player2ScoreLabel = new JLabel("Player 2 Score: 0");
        submitButton = new JButton("Submit Answer");
        hintButton = new JButton("Get Hint");
        endGameButton = new JButton("End Game");

        // QUESTION PANEL
        questionPanel = new JPanel();
        questionPanel.setBackground(Color.GRAY);
        questionPanel.setBounds(0, 0, 640, 100);
        questionPanel.setLayout(new BorderLayout());
        
        questionLabel.setVerticalAlignment(JLabel.CENTER);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setFont(new Font("Inter", Font.PLAIN, 25));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        
        timerLabel.setVerticalAlignment(JLabel.TOP);
        timerLabel.setHorizontalAlignment(JLabel.LEFT);
        questionPanel.add(timerLabel, BorderLayout.NORTH);
        
        // VALUE PANEL x3
        answerInputPanel1 = new JPanel();
        answerInputPanel2 = new JPanel();
        answerInputPanel3 = new JPanel();
        answerInputPanel1.setBounds(0, 100, 640, 70);
        answerInputPanel2.setBounds(0, 170, 640, 70);
        answerInputPanel3.setBounds(0, 240, 640, 70);

        answerInputPanel1.setLayout(null);
        answerInputPanel2.setLayout(null);
        answerInputPanel3.setLayout(null);

        answerInputPanel1.setBackground(new Color(194, 218, 161));
        answerInputPanel2.setBackground(new Color(185, 202, 161));
        answerInputPanel3.setBackground(new Color(163, 184, 161));

        valueFor1.setFont(new Font("Inter", Font.PLAIN, 25));
        valueFor2.setFont(new Font("Inter", Font.PLAIN, 25));
        valueFor3.setFont(new Font("Inter", Font.PLAIN, 25));
        
        valueFor1.setBounds(30, 0, 300, 70);
        valueFor2.setBounds(30, 0, 300, 70);
        valueFor3.setBounds(30, 0, 300, 70);

        answerInput1.setHorizontalAlignment(JTextField.CENTER);
        answerInput2.setHorizontalAlignment(JTextField.CENTER);
        answerInput3.setHorizontalAlignment(JTextField.CENTER);
        answerInput1.setBounds(270, 12, 300, 42);
        answerInput2.setBounds(270, 12, 300, 42);
        answerInput3.setBounds(270, 12, 300, 42);

        addDecimalValidation(answerInput1);
        addDecimalValidation(answerInput2);
        addDecimalValidation(answerInput3);

        answerInputPanel1.add(valueFor1);
        answerInputPanel1.add(answerInput1);
        answerInputPanel2.add(valueFor2);
        answerInputPanel2.add(answerInput2);
        answerInputPanel3.add(valueFor3);
        answerInputPanel3.add(answerInput3);
        
        // SUBMIT PANEL
        submitPanel = new JPanel();
        submitPanel.setBounds(0, 310, 320, 50);
        submitPanel.setLayout(new BorderLayout());
        submitPanel.add(submitButton);

        // GET HINT PANEL
        hintPanel = new JPanel();
        hintPanel.setBounds(320, 310, 320, 50);
        hintPanel.setLayout(new BorderLayout());
        hintPanel.add(hintButton);

        // PLAYER SCORE
        player1ScorePanel = new JPanel();
        player1ScorePanel.setLayout(new BorderLayout());
        player1ScorePanel.setBounds(0, 360, 320, 50);
        player1ScorePanel.setBackground(new Color(209, 141, 141));
        player1ScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        player1ScoreLabel.setHorizontalAlignment(JLabel.CENTER);
        player1ScoreLabel.setVerticalAlignment(JLabel.CENTER);
        player1ScoreLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        player1ScorePanel.add(player1ScoreLabel);

        player2ScorePanel = new JPanel();
        player2ScorePanel.setLayout(new BorderLayout());
        player2ScorePanel.setBounds(320, 360, 320, 50);
        player2ScorePanel.setBackground(new Color(209, 141, 141));
        player2ScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        player2ScoreLabel.setHorizontalAlignment(JLabel.CENTER);
        player2ScoreLabel.setVerticalAlignment(JLabel.CENTER);
        player2ScoreLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        player2ScorePanel.add(player2ScoreLabel);

        // END GAME PANEL
        endGamePanel = new JPanel();
        endGamePanel.setBounds(0, 410, 640, 35);
        endGamePanel.setLayout(new BorderLayout());
        endGamePanel.add(endGameButton);        


        this.setLayout(null);
        this.add(questionPanel);
        this.add(answerInputPanel1);
        this.add(answerInputPanel2);
        this.add(answerInputPanel3);
        this.add(submitPanel);
        this.add(hintPanel);
        this.add(player1ScorePanel);
        this.add(player2ScorePanel);
        this.add(endGamePanel);


        // add(endGameButton);

        startNewTurn();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleHint();
            }
        });

        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEndGame();
            }
        });
    }

    private void addDecimalValidation(JTextField textField) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            private void validateInput() {
                String text = textField.getText();
                if (!Pattern.matches("\\d*\\.?\\d*", text)) {
                    SwingUtilities.invokeLater(() -> textField.setText(text.replaceAll("[^\\d.]", "")));
                }
            }
        });
    }

    private void startNewTurn() {
        gameLogic.generateQuestion();
        String questions = gameLogic.getQuestion();
        questionLabel.setText("Question: " + questions);
        answerInput1.setText("");
        answerInput2.setText("");
        answerInput3.setText("");

        String[] parts = questions.split(" ");
        valueFor1.setText("Value for " + parts[0] + ":");
        valueFor2.setText("Value for " + parts[2] + ":");
        valueFor3.setText("Value for " + parts[4] + ":");
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

        String playerAnswer1 = answerInput1.getText().trim();
        String playerAnswer2 = answerInput2.getText().trim();
        String playerAnswer3 = answerInput3.getText().trim();
        String playerAnswer = playerAnswer1 + gameLogic.getOperator()  + playerAnswer2 + " = " + playerAnswer3;

        if (playerAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an answer before submitting!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
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

    private void handleHint() {
        String revealedQuestion = helperFunc.revealHint(gameLogic.getDigitToCharMap(), gameLogic.getQuestion());
        if (revealedQuestion == null) {
            JOptionPane.showMessageDialog(this, "No more hints available!");
        } else {
            questionLabel.setText("Question: " + revealedQuestion);
            if (isPlayer1Turn) {
                gameLogic.deductPoints(true, 5);
                player1ScoreLabel.setText("Player 1 Score: " + gameLogic.getPlayer1Score());
            } else {
                gameLogic.deductPoints(false, 5);
                player2ScoreLabel.setText("Player 2 Score: " + gameLogic.getPlayer2Score());
            }
        }
    }

    private void handleEndGame() {
        timer.cancel();
        int player1Score = gameLogic.getPlayer1Score();
        int player2Score = gameLogic.getPlayer2Score();
    
        String winnerMessage;
        if (player1Score > player2Score) {
            winnerMessage = "Player 1 wins with " + player1Score + " points!";
        } else if (player2Score > player1Score) {
            winnerMessage = "Player 2 wins with " + player2Score + " points!";
        } else {
            winnerMessage = "It's a tie! Both players have " + player1Score + " points.";
        }
    
        JOptionPane.showMessageDialog(this, winnerMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CryptarithmGame game = new CryptarithmGame();
            game.setVisible(true);
        });
    }
}
