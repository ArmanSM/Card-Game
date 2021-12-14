package poker;
// example: Ace of clubs would have Suit clubs and Rank ACE
public class Card {
	
	 enum Suit {
		CLUBS(1), // values are to check for flushes
		DIAMONDS(2),
		HEARTS(3),
		SPADES(4);
		private int suitValue; 
		private Suit suit; 
		Suit(int suitValue) {
			this.suitValue = suitValue;
		}
		public Suit getSuit() {
			return this.suit;
		}
	
		public void setSuit(Suit s) {
			this.suit = s; 
		}
	}
	
	 enum Rank {
		ONE(1), // One is for the Ace in the "A-2-3-4-5" straight 
		TWO(2), 
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT (8),
		NINE(9),
		TEN(10),
		JACK(11),
		QUEEN(12),
		KING(13),
		ACE(14),
		KILL(15); 
		private int rankValue; // used to compare strength of pairs, straights, etc
		private Rank rank; 
		private Rank (int rankValue) {
			this.rankValue = rankValue;
		}
		public int getRankInt () {
			return rankValue;
		}
		public Rank getRank() {
			return this.rank; 
		}
		public void setRank(Rank r) {
			this.rank = r; 
		}
	}
	
    Suit suit;
    Rank rank; // 2-14 with ace being 14

	public Card(Suit s, Rank r) {
		this.suit = s;
		this.rank = r; 
	}
	
	 public String toString() {
	//	 String theCard = this.rank + " of " + this.suit; 
	//	 return theCard; 
		 
		   if (this.rank.compareTo(Rank.JACK)<0) {
		  
			 int rankNum = this.rank.ordinal()+1; 
			 String theCard = rankNum + "" + this.suit.name().charAt(0); 
			 return theCard; 
		 }
		 else {
			 String theCard =this.rank.name().charAt(0) + "" + this.suit.name().charAt(0); 
			 return theCard; 
		 }
		 
	}
}
