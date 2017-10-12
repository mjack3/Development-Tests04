
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PrizeRepository;
import security.LoginService;
import domain.Prize;

@Service
@Transactional
public class PrizeService {

	@Autowired
	private PrizeRepository	prizeRepository;
	@Autowired
	private LoginService	loginService;


	public Prize save(final Prize prize) {
		Prize saved = null;
		Assert.notNull(prize);

		if (prize.getId() == 0) {	//alta nueva
			Assert.isTrue(LoginService.hasRole("MANAGER"));
			saved = this.prizeRepository.save(prize);
		}
		return saved;
	}

}
