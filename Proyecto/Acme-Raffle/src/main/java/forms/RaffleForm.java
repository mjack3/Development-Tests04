
package forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import domain.Comment;
import domain.Raffle;

public class RaffleForm extends Raffle {

	private Integer	num;
	private Integer	numWinner;
	private String	namePrize;
	private String	descriptionPrize;
	private List<Comment> comments;
	
	
	@NotNull
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

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
	@NotBlank
	public String getNamePrize() {
		return this.namePrize;
	}
	public void setNamePrize(final String namePrize) {
		this.namePrize = namePrize;
	}
	@NotBlank
	public String getDescriptionPrize() {
		return this.descriptionPrize;
	}
	public void setDescriptionPrize(final String descriptionPrize) {
		this.descriptionPrize = descriptionPrize;
	}

}
