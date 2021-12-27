package poker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	 public static void preFlopBetting() {
     	 boolean flag = true; 
     	 while (flag) {
			double bet = 0; // amount player is putting in pot
			double pastRaise = 0; 
			int noRaiseCounter = 0; // once everyone has either called or checked, this will equal numPlayersInHand and we change rounds
			// Remove BB and SB chips, adds to chipsInPot
			players[1].setChipTotal(players[0].getChipTotal()-bigBlind);
			players[0].setChipTotal(players[1].getChipTotal()-bigBlind/2);
			chipsInPot += 1.5*bigBlind; 
			
			for (int i=0; i<players.length; i++) { // start after BB (position=2), end at BB (position=1) 
				int playerIndex = (i+2) % players.length; 
				double minBet = pastRaise==0 ? 2*bigBlind : 2*pastRaise; // min bet is either BB or 2*past bet (1 to call, 1 to raise)
				if (players[playerIndex].getIsInHand()) {
					players[playerIndex].setIsTurn(true); 
					if (numPlayersInHand==1) {
						System.out.println(players[playerIndex].toString() + "WINS"); 
						break;
					}
					do {
						 System.out.println(players[playerIndex].toString() + "'s Turn. Enter amount to bet, 0 to call/check or -1 to fold"); 
						 bet = scanner.nextDouble(); 

					 } while (!(bet>=minBet || bet==-1 || bet==0)); 
					
					  if (bet==0) { // player "calls" the bet
						  chipsInPot += pastRaise;
						  players[playerIndex].setChipTotal(players[playerIndex].getChipTotal()-pastRaise);
						  noRaiseCounter++;
					  }
					  else if (bet==-1) { // player folds
						  noRaiseCounter++; 
						  break; 
					  }
					  else if (bet>=minBet) { // raise must be equal or greater previous bet. "bet" is total amount placed in pot 
						  chipsInPot += bet; 
						  players[playerIndex].setChipTotal(players[playerIndex].getChipTotal()-bet);
						  noRaiseCounter = 1; // reset to 1 when someone bets as that person cannot go again unless a raise is made
					  }
					pastRaise = bet;
					bet = 0; 
				}
			}
			if (numPlayersInHand==1) break; 

			if (noRaiseCounter>=numPlayersInHand ) {
				flag = false;
				break;
			}
     	 }
	 }
	 //find next index. Go from 0 to max position
	 public static int findNextIndex(int index, int size) {
		 return index==0 ? (size-1) : (index-1);
	 }
	 // for flop and onwards. pre-flop betting will start  
	 public static void bettingRound() {
     	 boolean flag = true; 
     	 while (flag) {
			double bet = 0; // amount player is putting in pot
			double pastRaise = 0; 
			int noRaiseCounter = 0; // once everyone has either called or checked, this will equal numPlayersInHand and we change rounds
			// start at Small Blind, end at Dealer (who has greatest position) 
			for (int i=0; i<players.length; i++) {
				double minBet = pastRaise==0 ? bigBlind : 2*pastRaise; // min bet is either BB or 2*past bet (1 to call, 1 to raise)
				
				if (players[i].getIsInHand()) {
					players[i].setIsTurn(true); 
					if (numPlayersInHand==1) {
						System.out.println(players[i].toString() + "WINS"); 
						break;
					}
					do {
						 System.out.println(players[i].toString() + "'s Turn. Enter amount to bet, 0 to call/check or -1 to fold"); 
						 bet = scanner.nextDouble(); 

					 } while (!(bet>=minBet || bet==-1 || bet==0)); 
					
					  if (bet==0) { // player "calls" the bet
						  chipsInPot += pastRaise;
						  players[i].setChipTotal(players[i].getChipTotal()-pastRaise);
						  noRaiseCounter++;
					  }
					  else if (bet==-1) { // player folds
						  noRaiseCounter++; 
						  break; 
					  }
					  else if (bet>=minBet) { // raise must be equal or greater previous bet. "bet" is total amount placed in pot 
						  chipsInPot += bet; 
						  players[i].setChipTotal(players[i].getChipTotal()-bet);
						  noRaiseCounter = 1; // reset to 1 when someone bets as that person cannot go again unless a raise is made
					  }
					pastRaise = bet;
					bet = 0; 
				}
			}
			if (numPlayersInHand==1) break; 

			if (noRaiseCounter>=numPlayersInHand ) {
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
		// START OF NEW HAND
		// Shuffles deck and sort players (by position with BB as 0) so they are in correct order for raising
		 aDeck.shuffle(); 
		 Arrays.sort(players, new PlayerComparator());
		 // deals hole cards and prints them out
     	 aDeck.dealHoleCards(players); 
		 for (int i =0; i<players.length; i++) {
			System.out.println(players[i].toStringInfo());  // (i+1) makes players[0] come out as player1
		 }  
     	 bettingRound(); 

		 // deals flop
		 aDeck.dealFlop(); 
		 System.out.println("The Flop: " + CC1 + " " + CC2 + " "+ CC3); 
		 for (int i =0; i<players.length; i++) {
			System.out.println(players[i].toStringInfo());  // (i+1) makes players[0] come out as player1
		 }
		 bettingRound();
	 
     	 // deals turn
		 aDeck.dealTurnOrRiver(CC4); 
		 System.out.println("The Turn: " + CC4); 
		 for (int i =0; i<players.length; i++) {
			System.out.println(players[i].toStringInfo());  // (i+1) makes players[0] come out as player1
		 }
		 bettingRound();
		 
		 // deals river
		 aDeck.dealTurnOrRiver(CC5); 
		 System.out.println("The River: " + CC5); 
		 for (int i =0; i<players.length; i++) {
			System.out.println(players[i].toStringInfo());  // (i+1) makes players[0] come out as player1
		 }
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
