
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import domain.Raffle;

public class RaffleForm extends Raffle {

	private Integer	num;
	private Integer	numWinner;


	@NotNull
	@Range(min = 1, max = 50)
	public Integer getNum() {
		return this.num;
	}
	public void setNum(final Integer num) {
		this.num = num;
	}
	@NotNull
	@Range(min = 1, max = 50)
	public Integer getNumWinner() {
		return this.numWinner;
	}
	public void setNumWinner(final Integer numWinner) {
		this.numWinner = numWinner;
	}

}
