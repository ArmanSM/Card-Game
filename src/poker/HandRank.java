package poker;

import java.util.ArrayList;

public class HandRank {

	 public static void assignHand() {
		 GameRunner gameRunner = new GameRunner(); 
		 Player[] players = gameRunner.getPlayers();
		 for (Player player : players) {
			 	player.setFinalCards();
			
				player.findFlush(); 
				if (!(player.getHandType()>7)) {
					player.findFrequencies();
				}
				if (!(player.getHandType()>6)) {
					player.findFullHouse();
				}
				if (!(player.getHandType()>4)) {
					player.findStraight();
				}
				if (!(player.getHandType()>3)) {
					player.findThreeKind();
				}
				if (!(player.getHandType()>2)) {
					player.findTwoPair();
				}
				if (!(player.getHandType()>1)) {
					player.findPair();
				}
				if (!(player.getHandType()>0)) {
					player.findHighCard();
				}	
		 }
	 }
	 public static void whoWins() {
		GameRunner gameRunner = new GameRunner(); 
		Player[] players = gameRunner.getPlayers();
		Player winningPlayer = players[0]; // the "winning" player defaults to first one
//		int winningPlayerNum = 1; // player number of who wins so it can be printed out
		ArrayList<Player> tiedPlayers = new ArrayList<Player>(); // tied players in the event of a tie
		int tieCounter = 0; 
		for (int i=1; i<players.length; i++) {
			
	//		if (player == players[0]) { // No need to compare the first player to themselves
	//			continue; 
	//		}
			if (players[i].getHandType() > winningPlayer.getHandType()) {
				winningPlayer = players[i]; 
//				winningPlayerNum = i+1; 
			}
			else if (players[i].getHandType() == winningPlayer.getHandType() || (!(tiedPlayers.isEmpty()) && players[i].getHandType() == tiedPlayers.get(0).getHandType())) { // checks if hand type is equal to temp winning player(s)
				for (int n=0; n<5; n++) { // for each card in player (use n for the nth card in their hand. i signifies player number
					if (players[i].finalHandRanks.get(n).compareTo(winningPlayer.finalHandRanks.get(n))>0) {
						if (tiedPlayers.contains(winningPlayer)) {
							tiedPlayers.clear(); // new winner so all the "tied players" are losers
						}
						winningPlayer = players[i]; // sets new "winning" player if their hand is better
 //                       winningPlayerNum = i+1; // sets player who wins
						break; // stops checking once definitive winner is found
					}
					else if (players[i].finalHandRanks.get(n).compareTo(winningPlayer.finalHandRanks.get(n))<0) {
						break; // stops checking once definitive winner is found
					}
					else if (players[i].finalHandRanks.get(n).compareTo(winningPlayer.finalHandRanks.get(n))==0) {
						tieCounter++; 
					}
					if (tieCounter==5) { // if hand is equal add the two players to the "tied" list
						tieCounter = 0; // reset it
						tiedPlayers.add(players[i]); 
						if (!tiedPlayers.contains(winningPlayer)) {
    						tiedPlayers.add(winningPlayer);
						}
					}
				}
				tieCounter = 0; // resets it between players
			}
		}
		if (tiedPlayers.size()!=0) {
			System.out.println("These bitches win " + tiedPlayers); 
		}
		else {
		System.out.println("This Bitch wins " + winningPlayer.getPlayerNum()); 
		}
	 }
}
