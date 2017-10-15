
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	public User() {
		super();
	}
	
	//	Relationships	------------------


	//private Collection<Prize>			prizes;					//	premios ganados
	private Collection<Participation> participations;


	/*
	 * @OneToMany(mappedBy = "user")
	 * public Collection<Prize> getPrizes() {
	 * return this.prizes;
	 * }
	 * 
	 * public void setPrizes(final Collection<Prize> prizes) {
	 * this.prizes = prizes;
	 * }
	 */

	@OneToMany(mappedBy = "user")
	public Collection<Participation> getParticipations() {
		return this.participations;
	}

	public void setParticipations(final Collection<Participation> participations) {
		this.participations = participations;
	}

}
