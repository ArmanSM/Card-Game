package poker;

public class FoldedState implements PlayerState{

	@Override
	public void next(Player player) {
		player.setState(new PlayingState());
	}
	@Override 
	public void previous(Player player) {
		System.out.println("ERROR, nothing to go back to"); 
	}

}
