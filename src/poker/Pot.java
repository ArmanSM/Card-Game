package poker;

public class Pot {
	private double[] playerTotals; // amount put in by certain player
	private double grandTotal; 
	
	public Pot(int numPlayers) {
		this.grandTotal = 0; 
		for (int i=0; i<numPlayers; i++) {
			this.playerTotals[i] =0; 
		}
	}
	public double[] getPlayerTotal() {
		return this.playerTotals; 
	}
	public void setPlayerTotal(double[] playerTotal) {
		this.playerTotals = playerTotal; 
	}
	public double getGrandTotal() {
		return this.grandTotal; 
	}
	public void setGrandTotal(double total) {
		this.grandTotal = total; 
	}
	
	
}
