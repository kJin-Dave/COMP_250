package assignment2;

public class test {
    public static char charRightShift(char input, int n){
        //Courtesy of week2 extra exercises
        //char cast
        char output = (char) (input+n);

        //if spills over
        if(input + n > 'Z') {
            output = charRightShift('A',input-'A'+n-26);
        }
        //if goes back (less than a)
        if (input +n < 'A') {
            output = charRightShift('Z', -(input-'A'-n-1));
        }
        return output;
    }

    public static char charLeftShift(char input, int n) {
        char output = (char) (input-n);
        //if spills over
        if(input - n > 'Z') {
            output = charLeftShift('A',n-('Z'-input-1));
        }
        //if goes back (less than a)
        if (input - n < 'A') {
            output = charLeftShift('Z', n-(input-'A'+1));
        }
        return output;
    }
    public static void main(String[] args) {
/*
        Deck deck = new Deck(2,1);
        deck.shuffle();
        System.out.println(deck.generateNextKeystreamValue());


 */

        char character = 'A';


        System.out.println(charLeftShift('Z',-1));
    }
}
