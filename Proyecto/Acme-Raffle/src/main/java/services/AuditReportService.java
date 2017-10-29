
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditReportRepository;
import security.LoginService;
import domain.AuditReport;
import domain.Auditor;
import domain.Raffle;

@Service
@Transactional
public class AuditReportService {

	@Autowired
	private LoginService			loginService;
	@Autowired
	private AuditReportRepository	repository;

	@Autowired
	private AuditorService			auditorService;


	public AuditReportService() {
		super();
	}

	public AuditReport create() {
		final AuditReport report = new AuditReport();

		report.setFinalMode(false);
		report.setMoment(new Date());
		report.setRaffle(new Raffle());
		report.setAuditor(this.auditorService.findOneUserAccount(LoginService.getPrincipal().getId()));

		return report;
	}

	public AuditReport save(final AuditReport entity) {
		Assert.notNull(entity);
		entity.setMoment(new Date(System.currentTimeMillis() - 1));
		return this.repository.saveAndFlush(entity);
	}

	public AuditReport update(final AuditReport entity) {
		Assert.notNull(entity);
		Assert.isTrue(this.repository.exists(entity.getId()));
		if (entity.getFinalMode())
			return entity;
		return this.repository.saveAndFlush(entity);
	}
	public AuditReport findOne(final Integer id) {
		Assert.notNull(id);
		Assert.isTrue(this.repository.exists(id));
		return this.repository.findOne(id);
	}

	public void delete(final AuditReport entity) {
		Assert.notNull(entity);
		Assert.isTrue(this.repository.exists(entity.getId()));
		if (!entity.getFinalMode()) {
			final Auditor auditor = this.auditorService.findOneUserAccount(LoginService.getPrincipal().getId());

			final List<AuditReport> reports = (List<AuditReport>) auditor.getReports();
			reports.remove(entity);
			auditor.setReports(reports);

		}

	}
	/**
	 * Encuentra
	 * 
	 * @param id
	 * @return
	 */

	public Collection<AuditReport> findAllByRaffle(final int id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		final Collection<AuditReport> auditReports = this.repository.findAllByRaffle(id);
		Assert.notNull(auditReports);
		return auditReports;
	}

	public void delete2(final AuditReport auditReport) {
		// TODO Auto-generated method stub
		Assert.notNull(auditReport);
		Assert.isTrue(LoginService.hasRole("MANAGER"));
		this.repository.delete(auditReport);
	}


	@Autowired
	private Validator	validator;


	public AuditReport reconstruct(AuditReport auditreport, final BindingResult binding) {
		// TODO Auto-generated method stub
		if (auditreport.getId() == 0)
			auditreport = this.create();
		else
			auditreport = this.findOne(auditreport.getId());

		return auditreport;
	}
}
