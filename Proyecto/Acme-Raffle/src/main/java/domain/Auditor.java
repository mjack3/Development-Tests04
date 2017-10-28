
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	public Auditor() {
		super();
	}


	private List<AuditReport>	reports;


	@NotNull
	@OneToMany(mappedBy = "auditor")
	public List<AuditReport> getReports() {
		return this.reports;
	}

	public void setReports(final List<AuditReport> reports) {
		this.reports = reports;
	}

}
