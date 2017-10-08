
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Code extends DomainEntity {

	private String	code;


	public Code() {
		super();
	}

	@NotBlank
	@Pattern(regexp = "^[A-Z]{4}[|\\/|-| ]{0,1}\\w {4}$")
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

}
