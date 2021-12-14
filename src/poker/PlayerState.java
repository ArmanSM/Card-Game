package poker;

public interface PlayerState {
	void next(Player player); 
	void previous(Player player); 
}
