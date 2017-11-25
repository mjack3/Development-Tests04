
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Auditor;
import forms.AuditorForm;
import services.AdministratorService;
import services.AuditorService;
import utilities.Validator;

@Controller
@RequestMapping("/auditor/administrator")
public class AuditorAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private AuditorService			auditorService;


	public AuditorAdministratorController() {
		super();
	}

	//	ALTA AUDITOR	================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView resul;

		final AuditorForm auditorForm = new AuditorForm();
		resul = this.createEditModelAndView(auditorForm, null);

		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AuditorForm auditorForm, final BindingResult bindingResult) {
		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createEditModelAndView(auditorForm, null);
		else
			try {
				if (Validator.isCorrectPhone(auditorForm.getPhone())) {
					bindingResult.rejectValue("phone", "error.phone", "error");
					throw new IllegalArgumentException();
				}

				if (!auditorForm.getPassword().equals(auditorForm.getRepeatPassword())) {
					bindingResult.rejectValue("repeatPassword", "repeatPassword.error", "error");
					throw new IllegalArgumentException();
				}

				final Auditor auditor = this.auditorService.reconstruct(auditorForm);
				this.auditorService.save(auditor);

				resul = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				resul = this.createEditModelAndView(auditorForm, "auditor.commit.error");
			}

		return resul;
	}

	private ModelAndView createEditModelAndView(final AuditorForm auditorForm, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("auditor/edit");
		resul.addObject("message", message);
		resul.addObject("auditorForm", auditorForm);
		resul.addObject("actionParam", "auditor/administrator/edit.do");
		return resul;
	}
}
