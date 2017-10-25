package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.AuditReport;
import domain.Auditor;
import domain.Raffle;
import repositories.AuditReportRepository;
import security.LoginService;

@Service
@Transactional
public class AuditReportService {

	@Autowired
	private AuditReportRepository	repository;
	
	@Autowired
	private AuditorService		auditorService;
	
	public AuditReportService() {
		super();
	}
	
	public AuditReport create() {
		AuditReport report= new AuditReport();
		
		report.setFinalMode(false);
		report.setMoment(new Date());
		report.setRaffle(new Raffle());
		report.setText("");
		
		return report;
	}

	public AuditReport save(AuditReport entity) {
		Assert.notNull(entity);
		return repository.save(entity);
	}
	
	public AuditReport update(AuditReport entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		if(entity.getFinalMode())
			return entity;
		return repository.save(entity);
	}

	public AuditReport findOne(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(repository.exists(id));
		return repository.findOne(id);
	}

	public void delete(AuditReport entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		if(!entity.getFinalMode()) {
			Auditor auditor = auditorService.findOneUserAccount(LoginService.getPrincipal().getId());
			
			List<AuditReport> reports = auditor.getReports();
			reports.remove(entity);
			auditor.setReports(reports);
			
			auditorService.update(auditor);
			
			repository.delete(entity);
		}
		
		
	}
	
	
	
	
}
