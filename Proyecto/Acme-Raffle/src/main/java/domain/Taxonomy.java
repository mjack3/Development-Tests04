
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Taxonomy extends DomainEntity {

	private String	property;


	public Taxonomy() {
		super();
	}

	@NotBlank
	public String getProperty() {
		return this.property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}
}
