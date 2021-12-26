package poker;
import java.util.ArrayList; 
import java.util.Random;

import poker.Card.Rank;
import poker.Card.Suit;

public class Deck {
	
   public ArrayList<Card> deckOfCards = new ArrayList<Card>(); 
   private GameRunner gameRunner = new GameRunner(); 
   
 // CONSTRUCTOR
	public Deck() {
		for (Suit suit: Suit.values()) {
			for (Rank rank : Rank.values()) {
				if (rank.ordinal()!=0 && rank.ordinal()!=14) { // ignore placeholder values
					deckOfCards.add(new Card(suit, rank)); 
				}
			}
		}
	}
	// shuffles using Fisher-Yates algo in O(n) and is quite obvious
	public void shuffle () { 
		Random rand = new Random(); 

		// make random Integer (object that changes every time)
		// int randomNum; 
		 for (int i=51; i>0; i--) {
			int randomNum = rand.nextInt(i+1); // j <-- random integer such that 0 <= j <= i
	         Card randomCard = deckOfCards.get(randomNum);  // randomCard is intermediate var
	         deckOfCards.set(randomNum, deckOfCards.get(i));
	         deckOfCards.set(i, randomCard );    // swap a[j] and a[i]  using intermediate var
		 }
	}
	// deals each player 2 cards (aka "hole" cards)
	public void dealHoleCards(Player[] players) {
		for (int i=0; i<players.length; i++) {
			players[i].setCard1(deckOfCards.get(deckOfCards.size()-1));
			deckOfCards.remove(deckOfCards.size()-1);
			players[i].setCard2(deckOfCards.get(deckOfCards.size()-1));
			deckOfCards.remove(deckOfCards.size()-1); 
			}
	}
	// deals the flop (the first 3 community cards, done all at once)
	public void dealFlop() {
		gameRunner.setCC1(deckOfCards.get(deckOfCards.size()-1)); // Sets the community cards, and removes them from deck
		deckOfCards.remove(deckOfCards.size()-1); 
		gameRunner.setCC2(deckOfCards.get(deckOfCards.size()-1)); 
		deckOfCards.remove(deckOfCards.size()-1); 
		gameRunner.setCC3(deckOfCards.get(deckOfCards.size()-1)); 
		deckOfCards.remove(deckOfCards.size()-1); 
	}
	// same as flop but just one card
	public void dealTurnOrRiver(Card cc) {
		if (cc ==gameRunner.getCC4()) {
			gameRunner.setCC4(deckOfCards.get(deckOfCards.size()-1)); 
		}
		else {
			gameRunner.setCC5(deckOfCards.get(deckOfCards.size()-1)); 
		}
		deckOfCards.remove(deckOfCards.size()-1); 
	}
	
/*	public String toString() {
		for (Card aCard : deckOfCards) {
			aCard.toString(); 
		 }
		return null;
	} */
	
	 
}
