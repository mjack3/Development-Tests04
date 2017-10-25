package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.AuditReport;
import domain.Auditor;
import domain.Raffle;
import security.LoginService;
import services.AuditReportService;
import services.AuditorService;

@Controller
@RequestMapping("/auditReport/auditor")
public class AuditReportAuditorController {
	
	@Autowired
	private AuditorService 		auditorService;
	
	@Autowired
	private AuditReportService auditReportService;
	
	private Raffle toSave = null;

	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("auditReport/list");
		
		Auditor auditor = auditorService.findOneUserAccount(LoginService.getPrincipal().getId());
		
		res.addObject("auditReports", auditor.getReports());

		return res;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam Raffle q) {
		ModelAndView res;

		res = new ModelAndView("auditReport/create");
		
		res.addObject("auditreport", auditReportService.create());
		
		toSave=q;

		return res;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Integer q) {
		ModelAndView res;

		res = new ModelAndView("auditReport/edit");
		
		res.addObject("auditreport", auditReportService.findOne(q));

		return res;
	}
	
	@RequestMapping(value ="/save",method = RequestMethod.POST)
	public ModelAndView save(@Valid AuditReport auditreport, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("auditReport/create");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("auditReport/create");
			res.addObject("auditReport", auditreport);
			res.addObject("message", "commit.error");
		}else {
			try {
				auditreport.setRaffle(toSave);
				auditReportService.save(auditreport);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("auditReport/create");
				res.addObject("auditReport", auditreport);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping(value ="/saveEdit",method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid AuditReport auditreport, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("auditReport/edit");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("auditReport/edit");
			res.addObject("auditReport", auditreport);
			res.addObject("message", "commit.error");
		}else {
			try {
				auditReportService.update(auditreport);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("auditReport/edit");
				res.addObject("auditReport", auditreport);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer q) {
		try {
			AuditReport prop = auditReportService.findOne(q);
			auditReportService.delete(prop);
			return list();
		}catch (Exception e) {
			e.printStackTrace();
			return list();
		}
	}
}
