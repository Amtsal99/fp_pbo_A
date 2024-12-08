import java.util.Random;

public class GameLogic {

    private Helper helperFunc = new Helper();

    private static final int MAX_DIGITS = 4;
    private static final int TIMER_SECONDS = 120;

    private int player1Score = 0;
    private int player2Score = 0;
    private String question;
    private String answer;
    private boolean operator;

    private char[] digitToCharMap = new char[10];

    public GameLogic() {
        this.digitToCharMap = helperFunc.generateRandomMapping();
    }

    public char[] getDigitToCharMap() {
        return this.digitToCharMap;
    }

    private long startTime;

    public void generateQuestion() {
        Random random = new Random();
        int num1 = random.nextInt((int) Math.pow(10, MAX_DIGITS));
        int num2 = random.nextInt((int) Math.pow(10, MAX_DIGITS));
        boolean isAddition = random.nextBoolean();
        
        int num3 = isAddition ? (num1 + num2) : (num1 - num2);
        String oper = isAddition ? " + " : " - ";
        this.answer = Integer.toString(num1) + operator + Integer.toString(num2) + " = " + Integer.toString(num3);
        this.operator = isAddition;

        String encryptedNum1 = helperFunc.encryptNumber(num1, this.digitToCharMap);
        String encryptedNum2 = helperFunc.encryptNumber(num2, this.digitToCharMap);
        String encryptedNum3 = helperFunc.encryptNumber(num3, this.digitToCharMap);

        this.question = encryptedNum1 + oper + encryptedNum2 + " = " + encryptedNum3;
        System.out.println("Question: " + question);
        System.out.println("Actual numbers: " + num1 + oper + num2 + " = " + num3);
    }

    public void startTurn() {
        this.startTime = System.currentTimeMillis();
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

        scoreChange = validateAnswer(isPlayer1, playerAnswer, timeTaken, remainingTime);

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

    //fungsi buat validasi jawaban player
    private int validateAnswer(boolean isPlayer1, String playerAnswer, long timeTaken, int remainingTime) {
        String[] playerAnswerParsed = new String[3]; 
        String[] questionParsed = new String[3];

        playerAnswerParsed = helperFunc.parseNumbersToDigits(playerAnswer);
        questionParsed = helperFunc.parseNumbersToDigits(this.question);

        //kalo jawaban player sesuai length string question
        if (!helperFunc.sameLength(questionParsed, playerAnswerParsed)) {
            System.out.println("Invalid answer length");
            return -Math.max(5,remainingTime / 10);
        }

        //kalo jawaban player konsisten di setiap digit
        else if (!helperFunc.isConsistent(questionParsed, playerAnswerParsed)){
            System.out.println("Inconsistent answer");
            return -Math.max(5,remainingTime / 10);
        }

        //kalo jawaban player ada assign digit leading zero
        else if (helperFunc.isZeroLeading(playerAnswerParsed)){
            System.out.println("Zero leading");
            return -Math.max(5,remainingTime / 10);
        }

        else if(!helperFunc.isCorrect(playerAnswerParsed, this.operator)){
            System.out.println("Incorrect answer");
            return -Math.max(5,remainingTime / 10);
        }
        
        //valid dan benar
        return Math.max(5, remainingTime / 10);
    }
}
