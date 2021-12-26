package poker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import poker.Card.Rank;
import poker.Card.Suit; 
public class Player {
	private int playerNum; 
	private Card card1; // each player has two hole cards
	private Card card2; 
	private boolean isTurn = false; // is set to true when it's their turn (let's them do raise, call, check, or fold). if (turn) is done in the Runner
	private boolean isInHand = true; // is set to false when they fold
	private int playerPosition = 0; // represents position (starting from BB going to their right) so BB is 0, SB is 1, Dealer is 2, etc
	private double chipTotal = 0.0; // total # of chips that a player has (not counting anything in live pots)
	private int handType = 0; // 0-High, 1-pair, 2- 2 pair, 3- 3 kind, 4- straight, 5 - flush, 6 - full house, 7 - 4 kind (sets value after the river from lowest high card to straight flush)
	
    private ArrayList<Rank> pairs = new ArrayList<Rank>();  // stores the pairs
    private ArrayList<Rank> threes = new ArrayList<Rank>(); // stores 3 of a kinds
    private ArrayList<Rank> fours = new ArrayList<Rank>(); // stores four of a kinds
    
    private ArrayList<Card> finalCards = new ArrayList<Card>(); // Stores the final 7 cards for each player (2 pocket plus 5 CC's) 
    private ArrayList<Rank> finalHandRanks = new ArrayList<Rank>(); // stores the hand ranks so aces full of kings is AAAKK, pair of aces could be A A 10 9 2 (kickers in descending order)
    
    // CONSTRUCTOR
    
	public Player(double startingChips) { 
		this.chipTotal = startingChips; 
		this.isInHand = true; 
	}
	
	
	// GETTERS AND SETTERS
	
	
	// FOR CARD RELATED INFORMATION
    public Card getCard1() {
    	return this.card1; 
    }
    
    public void setCard1(Card card) {
    	this.card1 = card; 
    }
    
    public Card getCard2() {
    	return this.card2; 
    }
    
    public void setCard2(Card card) {
    	this.card2 = card; 
    }
    
    public ArrayList<Card> getFinalCards() {
    	return this.finalCards; 
    }
    public ArrayList<Rank> getFinalHandRanks() {
    	return this.finalHandRanks;
    }
    
    public int getHandType() {
    	return this.handType;
    }
    
    public void setHandType(int handType) {
    	this.handType = handType; 
    }
    
    
    // FOR TURN & GAMEPLAY RELATED INFORMATION
    
    public int getPlayerNum () {
    	return this.playerNum;
    }
    
    public void setPlayerNum (int num) {
    	this.playerNum = num; 
    }
    
    public boolean getIsTurn() {
    	return this.isTurn;
    }
    
    public void setIsTurn(boolean isTurn) {
    	this.isTurn = isTurn; 
    }
    
    public boolean getIsInHand() {
    	return this.isInHand;
    }
    
    public void setIsInHand(boolean isInHand) {
    	this.isInHand = isInHand; 
    }
    
    public int getPlayerPosition() {
    	return this.playerPosition;
    }
    
    public void setPlayerPosition(int position) {
    	this.playerPosition = position; 
    }
    
    // FOR CHIP INFORMATION
    
    public double getChipTotal() {
    	return this.chipTotal;
    }
    
    public void setChipTotal(double chipTotal) {
    	this.chipTotal = chipTotal; 
    }
    

    // TO STRING METHOD
    public String toString() {
    	return "Player " + playerNum + " "; 
    }
    
    // TO STRING METHOD (With their Cards)
    public String toStringInfo() {
    	return "Player " + playerNum + ": " + this.card1 + " " + this.card2 + " Stack: " + this.chipTotal; 
    }
		


	GameRunner gameRunner = new GameRunner();
	public void setFinalCards() {
		  finalCards.add(card1); 
		  finalCards.add(card2); 
		  finalCards.addAll(gameRunner.getCommunityCards());
		  
		  for (Card card : finalCards) { // fills finalHandRanks
			  finalHandRanks.add(card.rank); 
		  }
		  Collections.sort(finalHandRanks, Collections.reverseOrder()); // sorts in descending order 

//		  finalHandRanks.remove(Collections.min(finalHandRanks)); // removes two lowest ranks
//		  finalHandRanks.remove(Collections.min(finalHandRanks)); // now we have the high cards ready if no hand is made
//		  Collections.sort(finalHandRanks, Collections.reverseOrder()); // sorts in descending order so ready for high cards
	}
	// finds frequency of each rank (i.e. finds the pairs, three of a kinds, four of a kinds)
	public void findFrequencies() {
		//  ArrayList<ArrayList<Rank>> hands = new ArrayList<ArrayList<Rank>>(); 
		  // Get frequency of each element, 
		  Map<Card.Rank, Integer> frequencies = new HashMap<Card.Rank, Integer>();
		  //adds the pairs, 3's, 4's to frequencies HashMap
		  for (Card card : finalCards) {
			  if (frequencies.containsKey(card.rank)) {
				  frequencies.put(card.rank, frequencies.get(card.rank) + 1); // adds one to frequency if already there
			  }
			  else {
				  frequencies.put(card.rank, 1); 
			  }
		  }
		  
		  for (Map.Entry<Card.Rank, Integer> entry : frequencies.entrySet()) {
			  if (entry.getValue() == 2) { // adds pairs
				  pairs.add(entry.getKey()); 
			  }
			  if (entry.getValue() == 3) { // adds 3 of a kinds (could be multiple)
				  threes.add(entry.getKey()); 
			  }
			  if (entry.getValue() == 4) { // adds 4 of a kind
				  fours.add(entry.getKey()); 
				  for (Card card : finalCards) { // adds the kicker
					  if (!fours.contains(card.rank)) {
						  fours.add(card.rank); 
					  }
					  fours.add(card.rank); 
					  if (fours.get(1).compareTo(card.rank)<0) { // makes sure the kicker is the largest 
						  fours.set(1, card.rank); 
					  }
				  }
				  finalHandRanks.set(4, fours.get(1)); // kicker is last card
				  for (int i=0; i<4; i++) {
					  finalHandRanks.set(i, fours.get(0)); // first four are the four of a kind so now four aces + 10 kicker would be A A A A 10
				  }
				  handType = 7;  // 4 of a kind is always highest hand if it happens so can be set immediately (unlike pair which can make 2 pair or a full house)
			  }
		  }
	}

	// finds full houses
	public void findFullHouse() {
		  // Full house if a pair (or two) and 3 of a kind
		  if (pairs.size()>0 && threes.size()>0) {
		      // AAA KK would be [A, K] in the ArrayList, the bank of 3 comes first, then the pair
			  // if 2 pairs can only be one 3 of a kind
			  if (pairs.size()>1) {
				  if (pairs.get(0).compareTo(pairs.get(1))>0) {
//					  fullHouse.add(threes.get(0));
//					  fullHouse.add(pairs.get(0)); 
					  for (int i=0; i<3; i++) { // fills finalHandRanks w/ the full house (e.g. AAAKK)
						  finalHandRanks.set(i, threes.get(0)); 
					  }
					  for (int i=3; i<5; i++) {
						  finalHandRanks.set(i, pairs.get(0)); 
					  }
				  }
				  else {
//					  fullHouse.add(threes.get(0));
//					  fullHouse.add(pairs.get(1)); 
					  for (int i=0; i<3; i++) { // fills finalHandRanks w/ the full house (e.g. AAAKK)
						  finalHandRanks.set(i, threes.get(0)); 
					  }
					  for (int i=3; i<5; i++) {
						  finalHandRanks.set(i, pairs.get(1)); 
					  }
				  }
			  }
			  // if 2 three of a kind can only be one pair
			  else if (threes.size()>1) {
				  if (threes.get(0).compareTo(threes.get(1))>0) {
//					  fullHouse.add(threes.get(0)); 
//					  fullHouse.add(pairs.get(0)); 
					  for (int i=0; i<3; i++) { // fills finalHandRanks w/ the full house (e.g. AAAKK)
						  finalHandRanks.set(i, threes.get(0)); 
					  }
					  for (int i=3; i<5; i++) {
						  finalHandRanks.set(i, pairs.get(0)); 
					  }
				  }
				  else {
//					  fullHouse.add(threes.get(1)); 
//					  fullHouse.add(pairs.get(0)); 
					  for (int i=0; i<3; i++) { // fills finalHandRanks w/ the full house (e.g. AAAKK)
						  finalHandRanks.set(i, threes.get(1)); 
					  }
					  for (int i=3; i<5; i++) {
						  finalHandRanks.set(i, pairs.get(0)); 
					  }
				  }
			  }
			  else {
//				  fullHouse.add(threes.get(0)); 
//				  fullHouse.add(pairs.get(0)); 
				  
				  for (int i=0; i<3; i++) { // fills finalHandRanks w/ the full house (e.g. AAAKK)
					  finalHandRanks.set(i, threes.get(0)); 
				  }
				  for (int i=3; i<5; i++) {
					  finalHandRanks.set(i, pairs.get(0)); 
				  }
			  }
			  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
			  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
			  handType = 6; // sets this to a full house
		  }

		  // For three of a kind (if two of them then we have a full house)
		  else if (threes.size()==2) {
			   // if 2 three of a kind, lower one becomes pair for a full house
//				  pairs.add(Collections.min(threes)); 
				  for (int i=0; i<3; i++) { 
					  finalHandRanks.set(i, Collections.max(threes)); 
				  }
				  for (int i=3; i<5; i++) {
					  finalHandRanks.set(i, Collections.min(threes)); 
				  }
//				  fullHouse.add(Collections.max(threes)); 
//				  fullHouse.add(pairs.get(0)); 
				  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
				  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
				  handType = 6;   
		  }
	}
	// finds three of a kind's 
	public void findThreeKind() {
			  if (threes.size()==1) {
				  // adds kickers to 4th and 5th slots in list so that Aces w/ KQ kicker will be A, A, A, K, Q
				  for (Card card : finalCards) { 
					  if (!threes.contains(card.rank)) {
						  finalHandRanks.add(card.rank); // 2 extra kickers
					  }
				  }
				  Collections.sort(finalHandRanks); // so now it's 9, 10, Q, K
				  finalHandRanks.set(4, finalHandRanks.get(2)); // now it's 9, 10, Q, K, Q
				  for (int i=0; i<3; i++) { // sets 3 aces to A, A, A, K, Q. Done!
					  finalHandRanks.set(i, threes.get(0)); 
				  }
				  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
				  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
				  handType = 3; // if only one 3 of a kind
			  }
	  }
	// Finds two pair(s) (there might possibly a third to be removed)
	 public void findTwoPair() {
		   if (pairs.size()> 1) {
			  if (pairs.size() == 3) { // remove smallest of the three pairs
				  pairs.remove(Collections.min(pairs)); 
			  }
			  finalHandRanks.set(0, Collections.max(pairs)); // first pair is greatest
			  finalHandRanks.set(1, Collections.max(pairs)); 
			  finalHandRanks.set(2, Collections.min(pairs)); // second pair is smallest
			  finalHandRanks.set(3, Collections.min(pairs)); 
			  
			  for (Card card : finalCards) { // sets the kicker
				  if (!pairs.contains(card.rank)) { 
					 if (card.rank.compareTo(finalHandRanks.get(4))>0) {
						 finalHandRanks.set(4, card.rank);
					 }
				  }
			  }
			  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
			  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
			  handType = 2; // sets handType to "2 pair" 
		  }
	  }
	// For a single pair
	public void findPair() {
		  if (pairs.size() == 1) { 
			  for (Card card : finalCards) {
				  if (!pairs.contains(card.rank)) { // adds all 5 kickers (2 extra)
					  finalHandRanks.add(card.rank); 
				  }
			  }
			  Collections.sort(finalHandRanks); // sorted so that 2 smallest are in first two spots
			  finalHandRanks.set(0, pairs.get(0)); // sets the pair
			  finalHandRanks.set(1, pairs.get(0)); // now finalHandRanks has the pair followed by 3 kickers (A, A, J, Q, K)
			  Rank intermediate = finalHandRanks.get(2); 
			  finalHandRanks.set(2, finalHandRanks.get(4)); 
			  finalHandRanks.set(4, intermediate); // now order is corrected so that we have (A, A, K, Q, J)
			  
			  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
			  if (finalHandRanks.size()>5) finalHandRanks.remove(5); 
			  handType = 1; 
		  }
		  
	//	  return handType;
	/*	  hands.add(pairs);
		  hands.add(1, threes); 
		  hands.add(2, fours); 
		  return hands; */
	}
	// For High Card 
	
	public void findHighCard() {
		 finalHandRanks.remove(Collections.min(finalHandRanks)); // removes two lowest ranks
		 finalHandRanks.remove(Collections.min(finalHandRanks)); // now we have the high cards ready if no hand is made
		 Collections.sort(finalHandRanks, Collections.reverseOrder()); // sorts in descending order so ready for high cards
		 
		 handType = 0; 
	}
	
	// uses the finalCards ArrayList to find 5 of same suit, records highest for the flush
	 // flushRanks has the ranks of the flush (to be used to compare strength)
	public void findFlush() {
		 int clubs = 0; 
		 int diamonds = 0;  
		 int hearts = 0;
		 int spades = 0;
		 ArrayList<Rank> clubRanks = new ArrayList<Rank>(); 
		 ArrayList<Rank> diamondRanks = new ArrayList<Rank>(); 
		 ArrayList<Rank> heartRanks = new ArrayList<Rank>(); 
		 ArrayList<Rank> spadesRanks = new ArrayList<Rank>(); 

		 for (Card card : finalCards) {
			 if (card.suit.equals(Suit.CLUBS)) { // checks each suit
				 clubs++;   
				 clubRanks.add(card.rank);
			 }
			 else if (card.suit.equals(Suit.DIAMONDS)) {
				 diamonds++;
				 diamondRanks.add(card.rank);
			 }
			 else if (card.suit.equals(Suit.HEARTS)) {
				 hearts++;
				 heartRanks.add(card.rank);
			 }
			 else if (card.suit.equals(Suit.SPADES)) {
				 spades++;
				 spadesRanks.add(card.rank);
			 }
		 }
		 if (clubs>=5) { // if flush set handType to flush
			 Collections.sort(clubRanks);
			 if (findStraight(clubRanks)) { // check for straight for strflush possibility
				 handType = 8; 
				 return; 
			 }
			 if (clubs>5) clubRanks.remove(0); // if more than 5, remove the lowest one(s)
			 if (clubs>5) clubRanks.remove(0); 
			 finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
			 finalHandRanks.remove(0); 
			 Collections.reverse(clubRanks); // now it's in descending order
			 Collections.copy(finalHandRanks, clubRanks); 
			 handType = 5; 
		 }
		 else if (diamonds>=5) {
			 Collections.sort(diamondRanks);
			 if (findStraight(diamondRanks)) {
				 handType = 8; 
				 return; 
			 }
			 if (diamonds>5) diamondRanks.remove(0);
			 if (diamonds>5) diamondRanks.remove(0); 
			 finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
			 finalHandRanks.remove(0); 
			 Collections.reverse(diamondRanks); 
			 Collections.copy(finalHandRanks, diamondRanks); 
			 handType = 5; 
		 }
		 else if (hearts>=5) {
			 Collections.sort(heartRanks);
			 if (findStraight(heartRanks)) {
				 handType = 8; 
				 return; 
			 }
			 if (hearts>5) heartRanks.remove(0); 
			 if (hearts>5) heartRanks.remove(0); 
			 finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
			 finalHandRanks.remove(0); 
			 Collections.reverse(heartRanks); 
			 Collections.copy(finalHandRanks, heartRanks); 
			 handType = 5; 
		 }
		 else if (spades>=5) {
			 Collections.sort(spadesRanks);
			 if (findStraight(spadesRanks)) {
				 handType = 8; 
				 return; 
			 }
			 if (spades>5) spadesRanks.remove(0); 
			 if (spades>5) spadesRanks.remove(0); 
			 finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
			 finalHandRanks.remove(0); 
			 Collections.reverse(spadesRanks); 
			 Collections.copy(finalHandRanks, spadesRanks); 
			 handType = 5; 
		 }
	}
	// sort them, check if five are in a row then set handType to 4. Now figure out how to compare hands on the same level
	// cards all already added to the ArrayList "finalCards"  
	// ranksInt list is left with only the ranks that form the straight
	public void findStraight() {
		ArrayList<Rank> straightRanks = new ArrayList<Rank>(); 
	//	ArrayList<Rank> toRemove = new ArrayList<Rank>(); 
		int numSequentialCards = 1; // since we only need 5 of the 7 cards to be sequential we can have up to 2 cards that don't work 
		int lowStraightCounter = 1; 
		
		for (Card card : finalCards) {
			if (!straightRanks.contains(card.rank)) {
				straightRanks.add(card.rank); // fills straightRanks with all the ranks (unique)
			}
		}
		Collections.sort(straightRanks); // ranks are sorted in ascending order
		int testRank = straightRanks.get(0).ordinal()+2; // next card after first one (e.g. if first is "two", testRank is "three")

		int length = straightRanks.size(); 
		for (int i = 1; i < length; i++) { // check each card with the next one so cycle n-1 times. n is size of straightRanks
			if (straightRanks.get(i).ordinal()+1 != testRank) {
				if (numSequentialCards>4) {
					if (straightRanks.size()>5) straightRanks.remove(Rank.KILL); 
		     		if (straightRanks.size()>5) straightRanks.remove(Rank.KILL); 
					if (straightRanks.size()>5) straightRanks.remove(5); // removes the two greater cards if they don't fit in the straight
					if (straightRanks.size()>5) straightRanks.remove(5); 
					break; // break so that the var isn't reset as we already have a straight (no chance of improvement in this case)
				}
				numSequentialCards = 1; 
				if (i<length-5) { // once there's less than 5 new cards to check, no chance of a straight
					testRank = straightRanks.get(i+1).ordinal()+1; // shifts testRank to the next card in the series
					straightRanks.set(i-1, Rank.KILL); 
					if (i>1) straightRanks.set(i-2, Rank.KILL); 
				}
				else {
					break; // break if less than 5 cards left to check
				}
			}
			else {
				testRank++;
				numSequentialCards++; 
			}
		}
		if (numSequentialCards>4) { // remove two lowest cards if needed
			if (straightRanks.size()>5) straightRanks.remove(0);
			if (straightRanks.size()>5) straightRanks.remove(0); 
			Collections.reverse(straightRanks); // reverse order so it's in descending order (i.e. 8, 7, 6, 5, 4)
			finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
			finalHandRanks.remove(0); 
			Collections.copy(finalHandRanks, straightRanks); // copy to finalHandRanks 
			handType = 4; // marks hand as straight
			return; 
		}		
		
		if (straightRanks.get(straightRanks.size()-1)==Rank.ACE) { // if Ace, check for Ace to 5 straight

			for (Rank rank : straightRanks) {
				if (rank.ordinal() == lowStraightCounter) { 
					lowStraightCounter++; 
				}
				else {
					break; // breaks out of loop so we don't waste time searching when it can't be an Ace to 5 straight
				}
			}
			if (lowStraightCounter==5) { // if Ace to 5, can't have 10 to Ace so remove the ace, making this the weakest straight
				handType = 4;
				straightRanks.remove(straightRanks.get(straightRanks.size()-1)); // Removes Ace
				straightRanks.add(0, Rank.ONE); // Adds "low" ace to show that this is the low straight
				if (straightRanks.size()>5) straightRanks.remove(5); // removes two greatest if necessary
				if (straightRanks.size()>5) straightRanks.remove(5);
				Collections.reverse(straightRanks); 
				finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
				finalHandRanks.remove(0); 
				Collections.copy(finalHandRanks, straightRanks); 
			}
		}
	}
	// to be used to see if flush is actually a straightflush
	public boolean findStraight(ArrayList<Rank> flushRanks) {
	// Assumptions: flushRanks is already in ascending order (5-7 cards) 
		ArrayList<Rank> straightFlushRanks = new ArrayList<Rank>(); // stores the sequential flush ranks 
		straightFlushRanks.add(flushRanks.get(0)); 
		int numSequentialCards = 1; // since we only need 5 of the 7 cards to be sequential we can have up to 2 cards that don't work 
		int lowStraightCounter = 1; 

		int testRank = flushRanks.get(0).ordinal()+2; // next card after first one (e.g. if first is "two", testRank is "three")

		int length = flushRanks.size(); 
		for (int i = 1; i < length; i++) { // check each card with the next one so cycle n-1 times. n is size of straightRanks
			if (flushRanks.get(i).ordinal()+1 != testRank) {
				if (numSequentialCards>4) {
					if (flushRanks.size()>5) flushRanks.remove(Rank.KILL); 
		     		if (flushRanks.size()>5) flushRanks.remove(Rank.KILL); 
					if (flushRanks.size()>5) flushRanks.remove(5); // removes the two greater cards if they don't fit in the straight
					if (flushRanks.size()>5) flushRanks.remove(5); 
					break; // break so that the var isn't reset as we already have a straight (no chance of improvement in this case)
				}
				numSequentialCards = 1; 
				if (i<length-5) { // once there's less than 5 new cards to check, no chance of a straight
					testRank = flushRanks.get(i+1).ordinal()+1; // shifts testRank to the next card in the series
					flushRanks.set(i-1, Rank.KILL); 
					if (i>1) flushRanks.set(i-2, Rank.KILL); 
				}
				else {
					break; // break if less than 5 cards left to check
				}
			}
			else {
				testRank++;
				numSequentialCards++; 
			}
		}
		if (numSequentialCards>4) { // remove two lowest cards if needed
			if (flushRanks.size()>5) flushRanks.remove(0);
			if (flushRanks.size()>5) flushRanks.remove(0); 
			finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
			finalHandRanks.remove(0); 
			Collections.reverse(flushRanks); // reverse order so it's in descending order (i.e. 8, 7, 6, 5, 4)
			Collections.copy(finalHandRanks, flushRanks); // copy to finalHandRanks 
			handType = 8; // marks hand as straight
			return true; 
		}		
		
		if (flushRanks.get(flushRanks.size()-1)==Rank.ACE) { // if Ace, check for Ace to 5 straight

			for (Rank rank : flushRanks) {
				if (rank.ordinal() == lowStraightCounter) { 
					lowStraightCounter++; 
				}
				else {
					break; // breaks out of loop so we don't waste time searching when it can't be an Ace to 5 straight
				}
			}
			if (lowStraightCounter==5) { // if Ace to 5, can't have 10 to Ace so remove the ace, making this the weakest straight
				flushRanks.remove(flushRanks.get(flushRanks.size()-1)); // Removes Ace
				flushRanks.add(0, Rank.ONE); // Adds "low" ace to show that this is the low straight
				if (flushRanks.size()>5) flushRanks.remove(5); // removes two greatest if necessary
				if (flushRanks.size()>5) flushRanks.remove(5);
				Collections.reverse(flushRanks); 
				finalHandRanks.remove(0); // remove 2 from finalHandRanks so that there's no extra (5 cards only)
				finalHandRanks.remove(0); 
				Collections.copy(finalHandRanks, flushRanks); 
				handType = 8;
				return true; 
			}
		}
		return false;
	}
}
