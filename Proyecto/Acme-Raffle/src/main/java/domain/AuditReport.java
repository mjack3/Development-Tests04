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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class AuditReport extends DomainEntity{

	
	public AuditReport() {
		super();
	}
	
	private String			text;
	private Date	 		moment;
	private Boolean 		finalMode;
	private Raffle 			raffle;
	
	@NotBlank
	public String getText() {
		return text;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getMoment() {
		return moment;
	}

	
	@NotNull
	public Boolean getFinalMode() {
		return finalMode;
	}
	
	@NotNull
	@ManyToOne(optional=false, targetEntity=Raffle.class)
	public Raffle getRaffle() {
		return raffle;
	}
	
	public void setRaffle(Raffle raffle) {
		this.raffle = raffle;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setFinalMode(Boolean finalMode) {
		this.finalMode = finalMode;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	
}
