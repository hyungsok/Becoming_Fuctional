package algorithm;

/**
 * 개미수열 알고리즘 문제
 * <p>
 * 1
 * 11
 * 12
 * 1121
 * 122111
 * 112213
 */
public class AntSequenceJava {
    private static final String EMPTY = " ";

    public static void main(String args[]) {
        // 숫자가 100 넘어가면 StackOverFlow 에러가 남
        printAnt(6);
    }

    private static void printAnt(int line) {
        String numbers = "1";
        for (int i = 1; i <= line; i++) {
            System.out.println(numbers);
            numbers = getAnt(numbers);
        }
    }

    private static String getAnt(String numbers) {
        StringBuilder result = new StringBuilder();
        char head = numbers.charAt(0);
        char[] tail = (numbers.substring(1) + EMPTY).toCharArray();
        int count = 1;
        for (char number : tail) {
            if (number != head) {
                result.append(head).append(count);
                count = 1;
                head = number;
            } else {
                count++;
            }
        }
        return result.toString();
    }
}
