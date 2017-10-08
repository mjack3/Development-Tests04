
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	public User() {
		super();
	}


	//	Relationships	------------------

	private Collection<Code>	codes;	//	códigos usados
	private Collection<Prize>	prizes;		//	premios ganados


	@ManyToMany
	public Collection<Code> getCodes() {
		return this.codes;
	}

	public void setCodes(final Collection<Code> codes) {
		this.codes = codes;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Prize> getPrizes() {
		return this.prizes;
	}

	public void setPrizes(final Collection<Prize> prizes) {
		this.prizes = prizes;
	}

}
