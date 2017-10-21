
package forms;

import java.util.Collection;

import domain.Prize;
import domain.Property;

public class PrizeForm extends Prize {
	
	private int winners;
	private int total;
	private int raffleId;
	private int prizeId;
	private Collection<Property> allProperties;
	
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

	public int getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	public Collection<Property> getAllProperties() {
		return allProperties;
	}

	public void setAllProperties(Collection<Property> allProperties) {
		this.allProperties = allProperties;
	}

	

}
