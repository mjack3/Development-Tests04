
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
	private ManagerService managerService;
	@Autowired
	private RaffleService raffleService;
	@Autowired 
	private CodeService codeService;

	public Prize save(final Prize prize) {
		Prize saved = null;
		Assert.notNull(prize);

		if (prize.getId() == 0) {	//alta nueva
			Assert.isTrue(LoginService.hasRole("MANAGER"));
			saved = this.prizeRepository.save(prize);
		}else{
			saved= this.prizeRepository.save(prize);
		}
		return saved;
	}
	
	public Prize create(){
		Assert.notNull(managerService.findPrincipal());
		Prize prize = new Prize();
		
		return prize;
	}
	
	public PrizeForm createForm(int raffleId){
		Assert.isTrue(raffleService.exist(raffleId),"raffle.error.exist");
		Assert.isTrue(this.raffleService.isEditable(raffleId),"raffle.error.editable");
		PrizeForm prizeForm= new PrizeForm();
		prizeForm.setRaffleId(raffleId);
		return prizeForm;
		
	}
	@Autowired
	private Validator validator;
	public Prize reconstruct(PrizeForm prizeForm, BindingResult binding){
		Prize prize;
		if(prizeForm.getId()==0){
			prize = new Prize();
			prize.setRaffle(raffleService.findOne(prizeForm.getRaffleId()));
		}else{
			prize = prizeRepository.findOne(prizeForm.getId());
		}
		prize.setCodes(new ArrayList<Code>());
		prize.setDescription(prizeForm.getDescription());
		prize.setName(prizeForm.getName());
		
		
		validator.validate(prize, binding);
		return prize;
		
	}
	
	public Prize regCode(PrizeForm prizeForm){
		Prize prize;
		
		prize = prizeRepository.findOne(prizeForm.getId());
		for(Code c: prize.getCodes()){
			codeService.delete(c);
		}
		codeService.getCodes(prizeForm.getTotal(), prizeForm.getWinners(), prize.getRaffle(), prize);
		return prize;
		
	}

	public List<Prize> findAllByRaffleId(int raffleId) {
		Assert.isTrue(raffleService.exist(raffleId));
		return this.prizeRepository.findAllByRaffleId(raffleId);
	}
	
	public PrizeForm regCodeReconstruct(int prizeId) {
		Assert.isTrue(this.exists(prizeId));
		PrizeForm prizeForm = new PrizeForm();
		
		Prize prize = this.prizeRepository.findOne(prizeId);
		prizeForm.setId(prizeId);
		int total = prize.getCodes().size();
		int winners = this.prizeRepository.countWinnersCode(prizeId);
		prizeForm.setTotal(total);
		prizeForm.setWinners(winners);
		return prizeForm;
	}

	public PrizeForm reconstruct(int prizeId) {
		Assert.isTrue(this.exists(prizeId),"raffle.error.prize.exist");
		PrizeForm prizeForm = new PrizeForm();
		
		Prize prize = this.prizeRepository.findOne(prizeId);
		Assert.isTrue(raffleService.isEditable(prize.getRaffle().getId()),"raffle.error.editable");
		prizeForm.setId(prizeId);
		prizeForm.setDescription(prize.getDescription());
		prizeForm.setRaffleId(prize.getRaffle().getId());
		prizeForm.setName(prize.getName());
		prizeForm.setProperties(prize.getProperties());
		return prizeForm;
	}

	public boolean exists(int prizeId) {
		
		return this.prizeRepository.exists(prizeId);
	}

	public void delete(PrizeForm prize) {
		Assert.notNull(managerService.findPrincipal());
		Prize pr = this.prizeRepository.findOne(prize.getId());
		for(Code c : pr.getCodes()){
			codeService.delete(c);
		}
		this.prizeRepository.delete(pr.getId());
		
	}

	public List<Prize> findAll() {
		return prizeRepository.findAll();
	}

	public Prize findOne(Integer id) {
		Assert.notNull(id);
		return prizeRepository.findOne(id);
	}

	public void checkNumberCodes(PrizeForm prizeForm, BindingResult binding) {

		if (prizeForm.getTotal() < prizeForm.getWinners()) {
			binding.rejectValue("total", "prize.num.error", "error");
			throw new IllegalArgumentException();
		}
		if(prizeForm.getTotal() <= 0 ){
			binding.rejectValue("total", "prize.num.error.0", "error");
			throw new IllegalArgumentException();
		}
		if(prizeForm.getWinners() <= 0 ){
			binding.rejectValue("winners", "prize.winner.error.0", "error");
			throw new IllegalArgumentException();
		}
		
		
		
		
	}

}
