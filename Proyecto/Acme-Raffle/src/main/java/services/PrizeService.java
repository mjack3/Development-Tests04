
package services;


import java.util.ArrayList;
import java.util.Collection;
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
	private LoginService	loginService;
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private RaffleService raffleService;

	public Prize save(final Prize prize) {
		Prize saved = null;
		Assert.notNull(prize);

		if (prize.getId() == 0) {	//alta nueva
			Assert.isTrue(LoginService.hasRole("MANAGER"));
			saved = this.prizeRepository.save(prize);
		}
		return saved;
	}
	
	public Prize create(){
		Assert.notNull(managerService.findPrincipal());
		Prize prize = new Prize();
		
		return prize;
	}
	
	public PrizeForm createForm(int raffleId){
		Assert.isTrue(raffleService.exist(raffleId));
		PrizeForm prizeForm= new PrizeForm();
		prizeForm.setRaffleId(raffleId);
		return prizeForm;
		
	}
	@Autowired
	private Validator validator;
	public Prize reconstruct(PrizeForm prizeForm, BindingResult binding){
		Prize prize = new Prize();
		prize.setCodes(new ArrayList<Code>());
		prize.setDescription(prizeForm.getDescription());
		prize.setName(prizeForm.getName());
		prize.setRaffle(raffleService.findOne(prizeForm.getRaffleId()));
		
		validator.validate(prize, binding);
		return prize;
		
	}

	public Collection<Prize> findAllByRaffleId(int raffleId) {
		Assert.isTrue(raffleService.exist(raffleId));
		return this.prizeRepository.findAllByRaffleId(raffleId);
	}
	

	public PrizeForm reconstruct(int prizeId) {
		Assert.isTrue(this.exists(prizeId));
		PrizeForm prizeForm = new PrizeForm();
		Prize prize = this.prizeRepository.findOne(prizeId);
		prizeForm.setDescription(prize.getDescription());
		prizeForm.setRaffleId(prize.getRaffle().getId());
		prizeForm.setName(prize.getName());
		return prizeForm;
	}

	public boolean exists(int prizeId) {
		
		return this.prizeRepository.exists(prizeId);
	}

	public void delete(Prize prize) {
		Assert.notNull(managerService.findPrincipal());
		this.prizeRepository.delete(prize.getId());
		
	}

	public List<Prize> findAll() {
		return prizeRepository.findAll();
	}

	public Prize findOne(Integer id) {
		Assert.notNull(id);
		return prizeRepository.findOne(id);
	}

}
