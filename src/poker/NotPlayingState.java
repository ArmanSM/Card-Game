package poker;

public class NotPlayingState implements PlayerState {
	@Override
	public void next(Player player) {
		player.setState(new PlayingState());
	}
	@Override 
	public void previous(Player player) {
		System.out.println("Error. Already in root state"); 
	}
}
