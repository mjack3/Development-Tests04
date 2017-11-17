
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	private String	nick;
	private String	url;


	@NotBlank
	public String getNick() {
		return nick;
	}

	@NotBlank
	@URL
	public String getUrl() {
		return url;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
