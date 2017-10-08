
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Raffle extends DomainEntity {

	private String	logo;
	private String	title;
	private String	description;
	private Date	publicationTime;
	private Date	deadline;


	public Raffle() {
		super();
	}

	@NotBlank
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(final String logo) {
		this.logo = logo;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Past
	@Temporal(TemporalType.DATE)
	public Date getPublicationTime() {
		return this.publicationTime;
	}

	public void setPublicationTime(final Date publicationTime) {
		this.publicationTime = publicationTime;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.DATE)
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

}
