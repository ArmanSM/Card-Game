package poker;

public class TurnState implements PlayerState {

	@Override
	public void next(Player player) {
		player.setState(new PlayingState());
	}
	@Override 
	public void previous(Player player) { // previous state here means folding
		player.setState(new FoldedState());
	}

}
