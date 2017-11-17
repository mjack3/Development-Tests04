
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	public Manager() {
		super();
	}


	//	Relationships ---------------------------

	private Collection<Raffle>	raffles;


	@OneToMany(mappedBy = "manager")
	public Collection<Raffle> getRaffles() {
		return this.raffles;
	}

	public void setRaffles(final Collection<Raffle> raffles) {
		this.raffles = raffles;
	}

}
