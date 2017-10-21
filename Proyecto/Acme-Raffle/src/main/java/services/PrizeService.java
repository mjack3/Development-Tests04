
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PrizeRepository;
import security.LoginService;
import domain.Code;
import domain.Prize;
import forms.PrizeForm;

@Service
@Transactional
public class PrizeService {

	@Autowired
	private PrizeRepository	prizeRepository;

	@Autowired
	private ManagerService	managerService;
	@Autowired
	private RaffleService	raffleService;
	@Autowired
	private CodeService		codeService;


	public Prize save(final Prize prize) {
		Prize saved = null;
		Assert.notNull(prize);

		if (prize.getId() == 0) {	//alta nueva
			Assert.isTrue(LoginService.hasRole("MANAGER"));
			saved = this.prizeRepository.save(prize);
		} else
			saved = this.prizeRepository.save(prize);
		return saved;
	}

	public Prize create() {
		Assert.notNull(this.managerService.findPrincipal());
		final Prize prize = new Prize();

		return prize;
	}

	public PrizeForm createForm(final int raffleId) {
		Assert.isTrue(this.raffleService.exist(raffleId), "raffle.error.exist");
		Assert.isTrue(this.raffleService.isEditable(raffleId), "raffle.error.editable");
		final PrizeForm prizeForm = new PrizeForm();
		prizeForm.setRaffleId(raffleId);
		return prizeForm;

	}

	private Validator	validator;


	public Prize reconstruct(final PrizeForm prizeForm, final BindingResult binding) {
		Prize prize;
		if (prizeForm.getPrizeId() == 0) {
			prize = new Prize();
			prize.setRaffle(this.raffleService.findOne(prizeForm.getRaffleId()));
		} else
			prize = this.prizeRepository.findOne(prizeForm.getPrizeId());
		prize.setCodes(new ArrayList<Code>());
		prize.setDescription(prizeForm.getDescription());
		prize.setName(prizeForm.getName());

		this.validator.validate(prize, binding);
		return prize;

	}

	public Prize regCode(final PrizeForm prizeForm) {
		Prize prize;

		prize = this.prizeRepository.findOne(prizeForm.getPrizeId());
		for (final Code c : prize.getCodes())
			this.codeService.delete(c);
		this.codeService.getCodes(prizeForm.getTotal(), prizeForm.getWinners(), prize.getRaffle(), prize);
		return prize;

	}

	public List<Prize> findAllByRaffleId(final int raffleId) {
		Assert.isTrue(this.raffleService.exist(raffleId));
		return this.prizeRepository.findAllByRaffleId(raffleId);
	}

	public PrizeForm regCodeReconstruct(final int prizeId) {
		Assert.isTrue(this.exists(prizeId));
		final PrizeForm prizeForm = new PrizeForm();

		final Prize prize = this.prizeRepository.findOne(prizeId);
		prizeForm.setPrizeId(prizeId);
		final int total = prize.getCodes().size();
		final int winners = this.prizeRepository.countWinnersCode(prizeId);
		prizeForm.setTotal(total);
		prizeForm.setWinners(winners);
		return prizeForm;
	}

	public PrizeForm reconstruct(final int prizeId) {
		Assert.isTrue(this.exists(prizeId), "raffle.error.prize.exist");
		final PrizeForm prizeForm = new PrizeForm();

		final Prize prize = this.prizeRepository.findOne(prizeId);
		Assert.isTrue(this.raffleService.isEditable(prize.getRaffle().getId()), "raffle.error.editable");
		prizeForm.setPrizeId(prizeId);
		prizeForm.setDescription(prize.getDescription());
		prizeForm.setRaffleId(prize.getRaffle().getId());
		prizeForm.setName(prize.getName());
		prizeForm.setProperties(prize.getProperties());
		return prizeForm;
	}

	public boolean exists(final int prizeId) {

		return this.prizeRepository.exists(prizeId);
	}

	public void delete(final PrizeForm prize) {
		Assert.notNull(this.managerService.findPrincipal());
		final Prize pr = this.prizeRepository.findOne(prize.getPrizeId());
		for (final Code c : pr.getCodes())
			this.codeService.delete(c);
		this.prizeRepository.delete(pr.getId());

	}

	public void delete(final Prize prize) {
		Assert.notNull(this.managerService.findPrincipal());
		for (final Code c : prize.getCodes())
			this.codeService.delete(c);
		this.prizeRepository.delete(prize.getId());

	}

	public List<Prize> findAll() {
		return this.prizeRepository.findAll();
	}

	public Prize findOne(final Integer id) {
		Assert.notNull(id);
		return this.prizeRepository.findOne(id);
	}

	public void checkNumberCodes(final PrizeForm prizeForm, final BindingResult binding) {

		if (prizeForm.getTotal() < prizeForm.getWinners()) {
			binding.rejectValue("total", "prize.num.error", "error");
			throw new IllegalArgumentException();
		}
		if (prizeForm.getTotal() <= 0) {
			binding.rejectValue("total", "prize.num.error.0", "error");
			throw new IllegalArgumentException();
		}
		if (prizeForm.getWinners() <= 0) {
			binding.rejectValue("winners", "prize.winner.error.0", "error");
			throw new IllegalArgumentException();
		}

	}

}
