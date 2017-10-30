
package controllers.auditor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditReportService;
import services.AuditorService;
import domain.AuditReport;
import domain.Auditor;
import domain.Raffle;

@Controller
@RequestMapping("/auditReport/auditor")
public class AuditReportAuditorController {

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private AuditReportService	auditReportService;

	private Raffle				toSave	= null;


	@RequestMapping("/list2")
	public ModelAndView list2(@RequestParam final int raffleId) {
		ModelAndView res;

		res = new ModelAndView("auditReport/all/list");

		final Collection<AuditReport> auditReports = this.auditReportService.findAllByRaffleFinal(raffleId);

		res.addObject("auditReports", auditReports);

		return res;
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("auditReport/list");

		final Auditor auditor = this.auditorService.findOneUserAccount(LoginService.getPrincipal().getId());

		res.addObject("auditReports", auditor.getReports());

		return res;
	}

	@RequestMapping("/create")
	public ModelAndView create(@RequestParam final Raffle q) {
		ModelAndView res;

		res = new ModelAndView("auditReport/create");
		final AuditReport auditReport = this.auditReportService.create();
		auditReport.setMoment(new Date(System.currentTimeMillis() - 1));
		auditReport.setRaffle(q);

		res.addObject("auditreport", auditReport);
		this.toSave = q;

		return res;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final Integer q) {
		ModelAndView res;

		res = new ModelAndView("auditReport/edit");
		final AuditReport auditReport = this.auditReportService.findOne(q);

		if (auditReport.getFinalMode() == true) {
			res = this.list();
			res.addObject("message", "error.edit.report");

		}
		res.addObject("auditreport", auditReport);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final AuditReport auditreport, final BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("auditReport/create");

		if (binding.hasErrors()) {
			res = new ModelAndView("auditReport/create");
			res.addObject("auditReport", auditreport);
			res.addObject("message", "commit.error");
		} else
			try {

				//auditreport = this.auditReportService.reconstruct(auditreport, binding);

				//auditreport.setRaffle(this.toSave);
				this.auditReportService.save(auditreport);
				return this.list();
			} catch (final Exception e) {
				res = new ModelAndView("auditReport/create");
				res.addObject("auditReport", auditreport);
				res.addObject("message", "commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(final @Valid AuditReport auditreport, final BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("auditReport/edit");
		if (auditreport.getFinalMode() == null)
			auditreport.setFinalMode(false);

		if (binding.hasErrors()) {
			res = new ModelAndView("auditReport/edit");
			res.addObject("auditReport", auditreport);
			res.addObject("message", "commit.error");
		} else
			try {
				this.auditReportService.update(auditreport);
				return this.list();
			} catch (final Exception e) {
				res = new ModelAndView("auditReport/edit");
				res.addObject("auditReport", auditreport);
				res.addObject("message", "commit.error");
			}

		return res;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam final Integer q) {

		try {
			final AuditReport prop = this.auditReportService.findOne(q);
			if (prop.getFinalMode() == true) {
				final ModelAndView resul = this.list();
				resul.addObject("message", "error.edit.report");
				return resul;
			}
			this.auditReportService.delete(prop);
			return this.list();
		} catch (final Exception e) {
			e.printStackTrace();
			return this.list();
		}
	}
}
