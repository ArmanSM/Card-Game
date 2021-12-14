package randomShit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class randomEnumTesting {
	enum Rank {
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
		ACE(14);
		private int rankValue;
		
		private Rank (int rankValue) {
			this.rankValue = rankValue;
		}
		public int getRank() {
			return this.rankValue; 
		}
	}
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
	public static void main (String[] args) {
		/*
		Rank Rank1 = Rank.TWO;
		Rank Rank2 = Rank.JACK; 
		Rank Rank3 = Rank.ACE;

		ArrayList<Rank> someRanks = new ArrayList<Rank>(); 
		someRanks.add(Rank1); 
		someRanks.add(Rank2); 
		someRanks.add(Rank3);
		someRanks.add(1, Rank.NINE); 
		
		System.out.println("******"); 
		*/
		int hoursLate = 5; 
		int weight = 1187; 
		double grade = (double) weight/hoursLate; 
		System.out.println(grade); 

		
	/*	if (someRanks.get(2).compareTo(someRanks.get(4))>0 && Rank3.compareTo(Rank2)>0) {
			System.out.println("fuck yes"); 
		} */
	}
	
}

