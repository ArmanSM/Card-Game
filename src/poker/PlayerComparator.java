package poker;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player>{
    //Comparator so that players can be sorted in position (BB first, UTG last) 
    @Override
    public int compare(Player p1, Player p2) {
    	if (p1.getPlayerPosition() > p2.getPlayerPosition()) {
    		return 1;
    	}
    	else if (p1.getPlayerPosition() < p2.getPlayerPosition()) {
    		return -1;
    	}
    	return 0; 
    }
}
