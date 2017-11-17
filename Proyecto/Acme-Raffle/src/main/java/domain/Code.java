
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Code extends DomainEntity {

	private String	code;
	private Boolean	isWinner;


	public Code() {
		super();
	}

	@NotNull
	public Boolean getIsWinner() {
		return this.isWinner;
	}

	public void setIsWinner(final Boolean isWinner) {
		this.isWinner = isWinner;
	}

	@NotBlank
	@Pattern(regexp = "^[A-Z0-9]{4}[-| |\\/]{0,1}\\w{4}$")
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}


	//	Relationships	----------------------

	private Raffle	raffle;
	private Prize	prize;


	@ManyToOne(optional = false)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

	@ManyToOne(optional = false)
	public Prize getPrize() {
		return this.prize;
	}

	public void setPrize(final Prize prize) {
		this.prize = prize;
	}

}
