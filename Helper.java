import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {

    public static String[] parseNumbersToDigits(String answer) {
        String[] partisi = answer.split(" ");
        List<String> hasil =  new ArrayList<>();

        for (String part : partisi) {
            if (part.matches("\\d+") || part.matches("[a-zA-Z]+")) {
                hasil.add(part);
            }
        }

        return hasil.toArray(new String[0]);
    }

    public static boolean isConsistent(String[] question, String[] ans) {
        String concatAns = String.join("", ans);
        String concatQuestion = String.join("", question);

        int[] val = new int[26];
        for (int i = 0; i < val.length; i++) {
            val[i] = -1;
        }

        for (int i = 0; i < concatAns.length(); i++) {
            char c = concatAns.charAt(i);
            char d = concatQuestion.charAt(i);
            
            if (val[d - 'A'] != -1 && val[d - 'A'] != (c-'0')) {
                return false;
            } else if (val[d - 'A'] == -1) {
                val[d - 'A'] = c - '0';
            }
        }
        return true;

    }

    public static boolean isZeroLeading(String[] ans) {
        for (String s : ans) {
            if (s.length() > 1 && s.charAt(0) == '0') {
                return true;
            }
        }
        return false;
    }

    public static boolean sameLength(String[] question, String[] ans) {
        for (int i = 0 ; i < 3 ; i++){
            if (question[i].length() != ans[i].length()) {
                System.out.println(ans[i].length());
                System.out.println(question[i].length());
                return false;
            }
        }
        return true;
    }

    public static boolean isCorrect(String[]ans, boolean isAddition){
        int bil1 = Integer.parseInt(ans[0]);
        int bil2 = Integer.parseInt(ans[1]);
        int bil3 = Integer.parseInt(ans[2]);
        if (isAddition) {
            return bil1 + bil2 == bil3;
        } else {
            return bil1 - bil2 == bil3;
        }
    }
    public char[] generateRandomMapping() {
        Random random = new Random();
        char[] availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(availableChars.length - i) + i;

            char temp = availableChars[i];
            availableChars[i] = availableChars[randomIndex];
            availableChars[randomIndex] = temp;

            // digitToCharMap[i] = availableChars[i];
        }
        return availableChars;
    }

    public String encryptNumber(int number, char[] digitToCharMap) {
        StringBuilder encrypted = new StringBuilder();
        for (char digit : String.valueOf(number).toCharArray()) {
            encrypted.append(digitToCharMap[digit - '0']);
        }
        return encrypted.toString();
    }

    public String revealHint(char[] digitToCharMap, String question) {
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

}
