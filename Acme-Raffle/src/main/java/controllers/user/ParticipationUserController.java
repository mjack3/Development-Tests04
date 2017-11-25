
package controllers.user;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Code;
import domain.Participation;
import domain.Raffle;
import domain.User;
import security.LoginService;
import services.CodeService;
import services.ParticipationService;
import services.RaffleService;

@Controller
@RequestMapping("/participation/user")
public class ParticipationUserController {

	@Autowired
	private ParticipationService	participationService;

	@Autowired
	private CodeService				codeService;

	@Autowired
	private RaffleService			raffleService;

	@Autowired
	private LoginService			loginService;

	private int						participationId;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int q) {
		ModelAndView result;

		Raffle raffle = raffleService.findOne(q);
		User user = (User) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Participation participation = participationService.create();
		participation.setUser(user);
		participation.setRaffle(raffle);
		Date today = new Date();
		if (today.before(raffle.getDeadline()) && today.after(raffle.getPublicationTime())) {
			result = createNewModelAndView(participation, null);
			participationId = q;
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Participation participation, BindingResult binding) {
		ModelAndView result;
		Boolean codeRegister = false;
		Boolean codeParticipation = false;
		List<Code> codes = codeService.codeByRaffle(participationId);
		Boolean isWin = false;
		Integer codewin = 0;
		List<Participation> participations = codeService.codeByParticipation(participationId);
		User user = (User) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Boolean isUsed = false;
		Date today = new Date();
		if (!today.after(raffleService.findOne(participationId).getPublicationTime()) && !today.before(raffleService.findOne(participationId).getDeadline())) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		for (Code a : codes) {

			if (transform(a.getCode()).contentEquals(transform(participation.getUsedCode()))) {
				codeRegister = true;
				break;

			}
		}
		for (Participation a : participations) {
			if (transform(a.getUsedCode()).contentEquals(transform(participation.getUsedCode()))) {
				codeParticipation = true;
				break;

			}
		}

		for (Participation a : user.getParticipations()) {
			if (transform(a.getUsedCode()).contentEquals(transform(participation.getUsedCode()))) {
				isUsed = true;
				break;
			}
		}

		if (binding.hasErrors()) {

			result = createNewModelAndView(participation, null);
		} else {
			try {

				if (isUsed) {
					result = createNewModelAndView(participation, "code.used");

				} else if (!codeRegister) {
					result = createNewModelAndView(participation, "code.register");
				} else if (codeParticipation) {
					result = createNewModelAndView(participation, "code.participation");
				} else {
					participationService.save(participation);

					for (Code code : participation.getRaffle().getCodes()) {
						if (transform(participation.getUsedCode()).contentEquals(transform(code.getCode())) && code.getIsWinner() == true) {
							isWin = true;
							codewin = code.getPrize().getId();

							break;
						}

					}
					if (isWin) {
						result = new ModelAndView("redirect:/prize/win.do?q=" + codewin);
					} else {
						result = new ModelAndView("redirect:/prize/lose.do");
					}

				}
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(participation, "acme.error.message");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(Participation participation, String message) {
		ModelAndView result;
		result = new ModelAndView("participation/create");
		result.addObject("participation", participation);
		result.addObject("message", message);
		return result;
	}
	
	private String transform(String code) {
		String res = code;
		
		if(code.contains("/")) {
			String[] parts= code.split("/");
			res = parts[0] + parts[1];
		}else if(code.contains("-")) {
			String[] parts= code.split("-");
			res = parts[0] + parts[1];
		}else if(code.contains(" ")) {
			String[] parts= code.split(" ");
			res = parts[0] + parts[1];
		}
		
		return res;
	}

}
