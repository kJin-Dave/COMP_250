public class charRightShift {

    public static char charRightShift(char input, int shift) {

        int newPos = (input - 'A' + shift)%26;
        return (char) ('A' + newPos);
    }

    public static char charLeftShift(char input, int shift) {
        int newPos = ('Z' - input + shift) %26;
        return (char) ('Z' - newPos);
    }

    public static void main(String[] args) {
        System.out.println(charLeftShift('W',4));
    }
}
