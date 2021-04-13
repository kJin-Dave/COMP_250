package assignment2;

import java.util.List;
import java.util.Random;

public class Deck {
 public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
 public static Random gen = new Random();

 public int numOfCards; // contains the total number of cards in the deck
 public Card head; // contains a pointer to the card on the top of the deck

 /* 
  * TODO: Initializes a Deck object using the inputs provided
  */
 public Deck(int numOfCardsPerSuit, int numOfSuits) {

  //IllegalArguments
  if( numOfCardsPerSuit > 13 || numOfCardsPerSuit < 1 || numOfSuits > suitsInOrder.length || numOfSuits < 1){
   throw new IllegalArgumentException();
  }
  numOfCards = 0;

  Card newCard;

  for (int i = 0; i < numOfSuits; i++) { //For every suit, starting from clubs
   for (int j = 1; j < numOfCardsPerSuit+1; j++) { //Making every card, starting from 1 (Ace)
    newCard = new PlayingCard(suitsInOrder[i], j);
    addCard(newCard);
   }
  }
  //Adding both jokers;
  addCard(new Joker("red"));
  addCard(new Joker("black"));

 }

 /* 
  * TODO: Implements a copy constructor for Deck using Card.getCopy().
  * This method runs in O(n), where n is the number of cards in d.
  */
 public Deck(Deck d) {

  if (d.numOfCards == 0) {
   this.numOfCards = 0;
   return;
  }

  Card header = d.head.getCopy();
  addCard(header);

  Card curCard = d.head.next;


  for (int i = 0; i < d.numOfCards-1; i++) {
     addCard(curCard.getCopy());
     curCard = curCard.next;
  }

  this.numOfCards = d.numOfCards;
 }

 /*
  * For testing purposes we need a default constructor.
  */
 public Deck() {}

 /* 
  * TODO: Adds the specified card at the bottom of the deck. This 
  * method runs in $O(1)$. 
  */
 public void addCard(Card c) {

  if(head ==null) {
   head = c;
   head.next = c;
   head.prev = c;
   numOfCards++;
   return;
  }

  if (head.prev == null) {
   head.next = c;
   head.prev = c;
   c.next = head;
   c.prev = head;
   numOfCards++;
   return;
  }
  c.next = head;
  c.prev = head.prev;

  head.prev.next = c;
  head.prev = c;
  numOfCards++;
 }

 /*
  * TODO: Shuffles the deck using the algorithm described in the pdf. 
  * This method runs in O(n) and uses O(n) space, where n is the total 
  * number of cards in the deck.
  */
 public void shuffle() {

  if (this.numOfCards <2 ) return;


  Card[] array = new Card[numOfCards];
  Card card = this.head;
  array[0] = head;
  //Copy into an array;
  for (int i = 1; i < numOfCards; i++) {
   array[i] = array[i-1].next;
  }
  //Shuffle
  int j;
  Card temp;
  for (int i = numOfCards-1; i > 0; i--) {
   j = gen.nextInt(i+1);
   temp = array[i];
   array[i] = array[j];
   array[j] = temp;
  }
  //Rebuilding the array
  this.head = array[0];
  Card current = this.head;
  Card prev;
  for (int i = 1; i < numOfCards; i++) {

   current.next = array[i];
   prev = current;

   current = current.next;
   current.prev = prev;
  }
  current.next = head;
  head.prev = current;

 }


 /*
  * TODO: Returns a reference to the joker with the specified color in 
  * the deck. This method runs in O(n), where n is the total number of 
  * cards in the deck. 
  */
 public Joker locateJoker(String color) {

  if (numOfCards ==0) return null;

  Card current = head;
  for (int i = 0; i < numOfCards; i++) {
   if (current instanceof Joker) {
    Joker joker = (Joker) current;
    if (joker.redOrBlack.equals(color)) return joker;
   }
   current = current.next;
  }
  return null;
 }

 /*
  * TODO: Moved the specified Card, p positions down the deck. You can 
  * assume that the input Card does belong to the deck (hence the deck is
  * not empty). This method runs in O(p).
  */
 public void moveCard(Card c, int p) {

  if (numOfCards < 2) return;

  Card position = c;

  for (int i = 0; i < p; i++) {
   position = position.next;
  }

  c.prev.next = c.next;
  c.next.prev = c.prev;

  c.next = position.next;
  c.prev = position;

  c.next.prev = c;
  c.prev.next = c;

 }

 /*
  * TODO: Performs a triple cut on the deck using the two input cards. You 
  * can assume that the input cards belong to the deck and the first one is 
  * nearest to the top of the deck. This method runs in O(1)
  */
 public void tripleCut(Card firstCard, Card secondCard) {

  //Edge cases
  Card last = head.prev;
  if(firstCard == head || secondCard == last) {
   Card newHead = head;
   if (firstCard == newHead) {
    head = secondCard.next;
   }
   if (secondCard == newHead.prev) {
    head = firstCard;
    return;
   }
  }
  else {

   Card beforeFirst = firstCard.prev;
   Card afterSecond = secondCard.next;

   //First section;
   secondCard.next = head;
   head.prev = secondCard;

   //Connection head and tail
   beforeFirst.next = afterSecond;
   afterSecond.prev = beforeFirst;

   //Last section
   firstCard.prev = last;
   last.next = firstCard;

   //newHead

   head = afterSecond;
  }
 }

 /*
  * TODO: Performs a count cut on the deck. Note that if the value of the 
  * bottom card is equal to a multiple of the number of cards in the deck, 
  * then the method should not do anything. This method runs in O(n).
  */
 public void countCut() {
  //Value of bottom % numOfCards
  Card last = head.prev;
  int count = last.getValue()%numOfCards;

  if(count >= numOfCards-1) return;

  if (count == 0) return;

  Card bot = head; //the card after counting down from head
  for (int i = 1; i < count; i++) {
   bot = bot.next;
  }

  Card newHead = bot.next;
  Card top = last.prev;

  bot.next = last;
  last.prev = bot;

  top.next = head;
  head.prev = top;

  newHead.prev = last;
  last.next = newHead;
  head = newHead;



/*
  Card top = last.prev;

  //Connecting
   //Top <-> head

  head.prev = top;
  top.next = head;

  //Bot <-> last
  bot.prev = last;
  last.next = bot;

  //New head;
  head = bot;
*/
 }

 /*
  * TODO: Returns the card that can be found by looking at the value of the
  * card on the top of the deck, and counting down that many cards. If the
  * card found is a Joker, then the method returns null, otherwise it returns
  * the Card found. This method runs in O(n).
  */
 public Card lookUpCard() {
  if(numOfCards!= 0) {
   int count = head.getValue();
   Card lookUp = head;
   for (int i = 0; i < count; i++) {
    lookUp = lookUp.next;
   }
   if (lookUp instanceof PlayingCard) {
    return lookUp;
   }
  }
  return null;
 }

 /*
  * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
  * using this deck. This method runs in O(n).
  */
 public int generateNextKeystreamValue() {


  Joker red = this.locateJoker("red");
  Joker black = this.locateJoker("black");

  //1. Locate red, and move it one card down
  moveCard(red,1);

  //2. Locate black and move it two down
  moveCard(black,2);

  //3. Triple cut
    //Find top joker
    Card top = this.head;
    while(!(top instanceof Joker)) top = top.next;

    //Triple cut
    if(top == red) {
     this.tripleCut(red,black);
    }
    else tripleCut(black,red);


  //4. Countcut
  countCut();

  //5. Look up Card

  Card keystreamCard = lookUpCard();

  if (keystreamCard == null) {
   return generateNextKeystreamValue();
  }

  return keystreamCard.getValue();
 }
/*
 private Joker findTopJoker(Joker red, Joker black) {
  Card redTemp = red;
  Card blackTemp = black;
  while(redTemp.getValue() != head.getValue() && blackTemp.getValue() != head.getValue()) {
   redTemp = redTemp.next;
   blackTemp = blackTemp.next;
  }
  if (redTemp.getValue() == head.getValue()) return black;
  else if (blackTemp.getValue() == head.getValue()) return red;
  else return null;
 }

 */

 public abstract class Card { 
  public Card next;
  public Card prev;

  public abstract Card getCopy();
  public abstract int getValue();

 }

 public class PlayingCard extends Card {
  public String suit;
  public int rank;

  public PlayingCard(String s, int r) {
   this.suit = s.toLowerCase();
   this.rank = r;
  }

  public String toString() {
   String info = "";
   if (this.rank == 1) {
    //info += "Ace";
    info += "A";
   } else if (this.rank > 10) {
    String[] cards = {"Jack", "Queen", "King"};
    //info += cards[this.rank - 11];
    info += cards[this.rank - 11].charAt(0);
   } else {
    info += this.rank;
   }
   //info += " of " + this.suit;
   info = (info + this.suit.charAt(0)).toUpperCase();
   return info;
  }

  public PlayingCard getCopy() {
   return new PlayingCard(this.suit, this.rank);   
  }

  public int getValue() {
   int i;
   for (i = 0; i < suitsInOrder.length; i++) {
    if (this.suit.equals(suitsInOrder[i]))
     break;
   }

   return this.rank + 13*i;
  }

 }

 public class Joker extends Card{
  public String redOrBlack;

  public Joker(String c) {
   if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
    throw new IllegalArgumentException("Jokers can only be red or black"); 

   this.redOrBlack = c.toLowerCase();
  }

  public String toString() {
   //return this.redOrBlack + " Joker";
   return (this.redOrBlack.charAt(0) + "J").toUpperCase();
  }

  public Joker getCopy() {
   return new Joker(this.redOrBlack);
  }

  public int getValue() {
   return numOfCards - 1;
  }

  public String getColor() {
   return this.redOrBlack;
  }
 }

}
