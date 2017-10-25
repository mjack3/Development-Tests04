package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import domain.AuditReport;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor{

	public Auditor() {
		super();
	}
	
	private List<AuditReport> reports;

	
	@NotNull
	@OneToMany
	public List<AuditReport> getReports() {
		return reports;
	}

	public void setReports(List<AuditReport> reports) {
		this.reports = reports;
	}
	
	
}
