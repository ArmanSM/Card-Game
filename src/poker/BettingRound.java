package poker;

public class BettingRound {
	public void betting() {
		 GameRunner gameRunner = new GameRunner(); 
		 Player[] players = gameRunner.getPlayers();
	}
	// adds chips raised to the pot
	public void raise(Player player, double amount) {
		// throw errors if breaking a raising rule
		player.setChipTotal(player.getChipTotal()-amount);  
		GameRunner.setChipsInPot(GameRunner.getChipsInPot() + amount); 
		
	}
	// Don't think I need check(), maybe reRaise() TO REVISIT
	
	// adds the called chips to the pot
	public void call (Player player, double amount) {
		player.setChipTotal(player.getChipTotal()-amount);
		GameRunner.setChipsInPot(GameRunner.getChipsInPot() + amount); 
	}
	
	public void fold(Player player) {
	//	player.setIsPlaying(false); 
	}
}
