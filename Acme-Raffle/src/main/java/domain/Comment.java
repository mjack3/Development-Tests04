
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private String	text;
	private Date	moment;
	private Integer	rating;
	private Actor	actor;
	private Raffle	raffle;
	private Prize	prize;


	public Comment() {
		super();
	}

	@NotBlank
	@NotNull
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	@Range(min = 0, max = 3)
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(final Integer rating) {
		this.rating = rating;
	}

	@ManyToOne(optional = true)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@ManyToOne(optional = true)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

	@ManyToOne(optional = true)
	public Prize getPrize() {
		return this.prize;
	}

	public void setPrize(final Prize prize) {
		this.prize = prize;
	}

}
