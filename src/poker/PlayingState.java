package poker;

public class PlayingState implements PlayerState {

	@Override
	public void next(Player player) {
		player.setState(new PlayingState());
	}
	public void previous(Player player) {
		player.setState(new NotPlayingState());
	}
}
