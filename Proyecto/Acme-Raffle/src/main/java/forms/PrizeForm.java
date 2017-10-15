
package forms;

import domain.Prize;

public class PrizeForm extends Prize {
	
	private int winners;
	private int total;
	private int raffleId;
	
	
	public int getRaffleId() {
		return raffleId;
	}

	public void setRaffleId(int raffleId) {
		this.raffleId = raffleId;
	}

	public int getWinners() {
		return winners;
	}

	public void setWinners(int winners) {
		this.winners = winners;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	

}
