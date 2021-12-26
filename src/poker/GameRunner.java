package poker;
import java.util.ArrayList;
import java.util.Scanner;
import poker.Card.Rank; 
import poker.Card.Suit; 
public class GameRunner {
	 private static Card CC1, CC2, CC3, CC4, CC5; // CC = community card (flop, turn, river), Make this array
	 private static int numPlayers = 2; // Number of players at start of hand. Default to 2 (up to 9)
	 private static int numPlayersInHand = 2; // # players in a certain hand
	 private static double chipsInPot = 0.0; // Amount of chips in the pot (is 0 in between hands)
	 private static double chipsInRound = 0.0; // Amount of chips raised in that betting round (reset to zero after each betting round in each hand)
	 private static Scanner scanner = new Scanner( System.in );
	 private static double startingChips; // Starting Chips
	 private static double bigBlind; // Big blind must be less than 1/10  the starting chips
	 private static Player[] players; 
	 
	 // GETTERS AND SETTERS
	 
	 // FOR COMMUNITY CARDS ("CC")
	 public Card getCC1() {
		 return GameRunner.CC1; 
	 }
	 public Card getCC2() {
		 return GameRunner.CC2; 
	 }
	 public Card getCC3() {
		 return GameRunner.CC3; 
	 }
	 public Card getCC4() {
		 return GameRunner.CC4; 
	 }
	 public Card getCC5() {
		 return GameRunner.CC5; 
	 }
	 public void setCC1(Card card) {
		 GameRunner.CC1 = card;
	 }
	 public void setCC2(Card card) {
		 GameRunner.CC2 = card;
	 }
	 public void setCC3(Card card) {
		 GameRunner.CC3 = card;
	 }
	 public void setCC4(Card card) {
		 GameRunner.CC4 = card;
	 }
	 public void setCC5(Card card) {
		 GameRunner.CC5 = card;
	 }
	 
	 // returns list of all community cards to rank player's hand strength
	 public ArrayList<Card> getCommunityCards() {
		 ArrayList<Card> cards = new ArrayList<Card>(); 
		 cards.add(CC1); 
		 cards.add(CC2); 
		 cards.add(CC3); 
		 cards.add(CC4); 
		 cards.add(CC5); 
		 return cards; 
	 }
	 public Player[] getPlayers() {
		 return players; 
	 }
	 
	 public static int getNumPlayers() {
		 return numPlayers;
	 }
	 
	 public static double getChipsInPot() {
		 return chipsInPot; 
	 }
	 public static void setChipsInPot(double chipsInPot) {
		GameRunner.chipsInPot = chipsInPot;
	 }
	  
	 public static void bettingRound() {
     	 boolean flag = true; 
     	 while (flag) {
			double bet = 0;
			double pastRaise = 0; 
			int noRaiseCounter = 0; // once everyone has either called or checked, this will equal numPlayersInHand and we change rounds
			for (int i=0; i<players.length; i++) {
				
				if (players[i].getIsInHand()) {
					players[i].setIsTurn(true); 
					if (numPlayersInHand==1) {
						System.out.println(players[i].toString() + "WINS"); 
						break;
					}
					int input = 0;
					do {
						System.out.println(players[i].toString() + "'s Turn: Enter -1 to fold and 0 to check"); 
						input = scanner.nextInt();
					} while (!(input==-1 || input ==0)); 
	
					if (input ==-1) { // fold
						players[i].setIsInHand(false); 
						numPlayersInHand--; 
						noRaiseCounter++;
					}
					else if (input ==0) noRaiseCounter++; 
				}
			}
			if (numPlayersInHand==1) break; 
//			do {
//				 System.out.println("Enter amount to raise: "); 
//				  raise = scanner.nextDouble(); 
//				  if (bet==pastRaise) { // player "calls" the bet
//					  noRaiseCounter++;
//				  }
//				  else if (bet==-1) { // player folds
//					  noRaiseCounter++; 
//					  break; 
//				  }
//				  else if (bet>=2*pastRaise && bet>=bigBlind) { // raise must be equal or greater previous raise
//					  noRaiseCounter = 0; // reset to zero when someone raises
//				  }
//			 } while (!(bet>=2*pastRaise && bet>=bigBlind) || bet!=-1);
//			pastRaise = bet;
//			bet = 0; 
			if (noRaiseCounter>=numPlayersInHand ) {
				System.out.println("On a bike: "+ noRaiseCounter);
				flag = false;
				break;
			}
     	 }
	 }
	 public static void main(String args[]) {
		Deck aDeck = new Deck();  
		HandRank handRank = new HandRank(); 
		// Sets # of players
		do {
			System.out.println("Enter how many players you want (2 to 9):"); 
			numPlayers = scanner.nextInt();
			numPlayersInHand = numPlayers; 
		} while (numPlayers<2 || numPlayers>9); 
		
		// Sets starting chip amount per player
		do {
			System.out.println("Enter starting chip amount for each player: ");
			startingChips = scanner.nextDouble();
		} while (startingChips<=20.0); 
		do {
			System.out.println("Enter the Big Blind amount (Cannot be greater than 1/10 starting chips): ");
			bigBlind = scanner.nextDouble();
		} while (bigBlind>startingChips/10); 
		
		
		// Makes array of players, players[0] akin to "player1"
		players = new Player[numPlayers]; 
		for (int i =0; i<players.length; i++) {
			players[i] = new Player(startingChips); // gives each player the inputted # of starting chips
			players[i].setPlayerNum(i+1);
		} 
		
		// Shuffles deck
		 aDeck.shuffle(); 
		 
		 // deals hole cards
     	 aDeck.dealHoleCards(players); 
		 for (int i =0; i<players.length; i++) {
			System.out.println(players[i].toStringInfo());  // (i+1) makes players[0] come out as player1
		 }  
     	 bettingRound(); 
     	 
//		 System.out.println("dealt \n"); 
//		 players[0].setCard1(new Card(Suit.HEARTS, Rank.ACE));
//		 players[0].setCard2(new Card(Suit.CLUBS, Rank.ACE));
//		 players[1].setCard1(new Card(Suit.CLUBS, Rank.SIX)); 
//		 players[1].setCard2(new Card(Suit.DIAMONDS, Rank.TWO)); 
//		 players[2].setCard1(new Card(Suit.DIAMONDS, Rank.TEN));
//		 players[2].setCard2(new Card(Suit.DIAMONDS, Rank.SEVEN));
//
//
//
//		 for (int i =0; i<players.length; i++) {
//			System.out.println("Player" + (i+1) + ": " + players[i].getCard1() + " " + players[i].getCard2() + " Stack: " + players[i].getChipTotal());  // (i+1) makes players[0] come out as player1
//		 }  
		 
		 // deals flop
		 aDeck.dealFlop(); 
		 System.out.println("The Flop: " + CC1 + " " + CC2 + " "+ CC3); 
		 bettingRound();
//		 CC1 = new Card(Suit.CLUBS, Rank.FIVE)); 
//		 CC2 = new Card(Suit.DIAMONDS, Rank.NINE); 
//		 CC3 = new Card(Suit.CLUBS, Rank.THREE); 
//		 CC4 = new Card(Suit.CLUBS, Rank.FOUR); 
//		 CC5 = new Card(Suit.CLUBS, Rank.TWO); 
	 
     	 // deals turn
		 aDeck.dealTurnOrRiver(CC4); 
		 System.out.println("The Turn: " + CC4); 
		 bettingRound();
		 // deals river
		 aDeck.dealTurnOrRiver(CC5); 
		 System.out.println("The River: " + CC5); 
	     bettingRound();
		 
		 // assigns final hand
		 handRank.assignHand(); 
		 // prints each hand type
		 for (int i =0; i<players.length; i++) {
			System.out.println("Player" + (i+1) + ": " + "Hand Type: " + players[i].getHandType());
			System.out.println("Player" + (i+1) + ": " + "7 cards: " + players[i].getFinalCards());
			System.out.println("Player" + (i+1) + ": " + "Final hand ranks: " + players[i].getFinalHandRanks());

		 }
		 handRank.whoWins(); 
		 
	 }

}
