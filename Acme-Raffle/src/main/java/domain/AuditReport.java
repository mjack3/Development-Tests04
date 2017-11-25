
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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class AuditReport extends DomainEntity {

	public AuditReport() {
		super();
	}


	private String	text;
	private Date	moment;
	private Boolean	finalMode;
	private Raffle	raffle;
	private Auditor	auditor;


	@NotBlank
	public String getText() {
		return this.text;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ManyToOne(optional = false)
	@NotNull
	public Auditor getAuditor() {
		return this.auditor;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

}
