
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Prize extends DomainEntity {

	private String	name;
	private String	description;


	public Prize() {
		super();
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	//	Relationships ------------------------

	private Raffle				raffle;
	private Collection<Property>			properties;
	private User				user;
	private Collection<Code>	codes;


	@ManyToOne(optional = false)
	public Raffle getRaffle() {
		return this.raffle;
	}

	public void setRaffle(final Raffle raffle) {
		this.raffle = raffle;
	}

	@ManyToMany
	public Collection<Property> getProperties() {
		return this.properties;
	}

	public void setProperties(final Collection<Property> properties) {
		this.properties = properties;
	}

	@ManyToOne
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "prize")
	public Collection<Code> getCodes() {
		return this.codes;
	}

	public void setCodes(final Collection<Code> codes) {
		this.codes = codes;
	}

}
