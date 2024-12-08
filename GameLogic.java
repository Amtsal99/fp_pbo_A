import java.util.Random;

public class GameLogic {
    private static final int MAX_DIGITS = 4;
    private static final int TIMER_SECONDS = 120;

    private int player1Score = 0;
    private int player2Score = 0;
    private String question;
    private String answer;

    private char[] digitToCharMap = new char[10];

    public GameLogic() {
        generateRandomMapping();
    }

    private long startTime;

    public void generateQuestion() {
        Random random = new Random();
        int num1 = random.nextInt((int) Math.pow(10, MAX_DIGITS));
        int num2 = random.nextInt((int) Math.pow(10, MAX_DIGITS));
        boolean isAddition = random.nextBoolean();
        
        int num3 = isAddition ? (num1 + num2) : (num1 - num2);
        String operator = isAddition ? " + " : " - ";
        this.answer = Integer.toString(num1) + operator + Integer.toString(num2) + " = " + Integer.toString(num3);

        String encryptedNum1 = encryptNumber(num1);
        String encryptedNum2 = encryptNumber(num2);
        String encryptedNum3 = encryptNumber(num3);
        this.question = encryptedNum1 + (isAddition ? " + " : " - ") + encryptedNum2 + " = " + encryptedNum3;
        System.out.println("Question: " + question);
        System.out.println("Actual numbers: " + num1 + (isAddition ? " + " : " - ") + num2 + " = " + num3);
    }

    private void generateRandomMapping() {
        Random random = new Random();
        char[] availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(availableChars.length - i) + i;

            char temp = availableChars[i];
            availableChars[i] = availableChars[randomIndex];
            availableChars[randomIndex] = temp;

            digitToCharMap[i] = availableChars[i];
        }
    }

    private String encryptNumber(int number) {
        StringBuilder encrypted = new StringBuilder();
        for (char digit : String.valueOf(number).toCharArray()) {
            encrypted.append(digitToCharMap[digit - '0']);
        }
        return encrypted.toString();
    }

    public void startTurn() {
        this.startTime = System.currentTimeMillis();
    }

    public String revealHint() {
        for (int i = 0; i < digitToCharMap.length; i++) {
            char encryptedChar = digitToCharMap[i];
            if (encryptedChar != '\0' && question.contains(String.valueOf(encryptedChar))) {
                question = question.replace(encryptedChar, (char) ('0' + i));
                digitToCharMap[i] = '\0';
                return question;
            }
        }
        return null;
    }
    
    public void deductPoints(boolean isPlayer1, int points) {
        if (isPlayer1) {
            player1Score -= points;
        } else {
            player2Score -= points;
        }
    }

    public int calculateScore(boolean isPlayer1, String playerAnswer) {
        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        int remainingTime = TIMER_SECONDS - (int) timeTaken;

        int scoreChange;
        System.out.println("Player answer: " + playerAnswer);
        System.out.println("Actual answer: " + answer);

        if (answer.equals(playerAnswer)) {
            scoreChange = Math.max(5, remainingTime / 10);
        } else if (playerAnswer.isEmpty()) {
            scoreChange = -4;
        } else {
            scoreChange = -Math.max(5, (int) timeTaken / 10);
        }

        if (isPlayer1) {
            player1Score += scoreChange;
        } else {
            player2Score += scoreChange;
        }

        return scoreChange;
    }

    public String getQuestion() {
        return question;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public boolean checkWin() {
        return player1Score >= 40 || player2Score >= 40;
    }
}
