
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Participation extends DomainEntity {

	public Participation() {
		super();
	}


	private Date	moment;
	private String	usedCode;


	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	public String getUsedCode() {
		return this.usedCode;
	}

	public void setUsedCode(final String usedCode) {
		this.usedCode = usedCode;
	}


	//	Relationships	---------------

	private User	user;
	private Raffle	raffle;


	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@ManyToOne(optional = false)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

}
