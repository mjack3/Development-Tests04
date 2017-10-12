
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Code;
import domain.Participation;
import domain.Prize;
import domain.Raffle;
import repositories.CodeRepository;
import security.LoginService;

@Transactional
@Service

public class CodeService {

	@Autowired
	CodeRepository	codeRepository;
	@Autowired
	LoginService	loginService;


	public CodeService() {
		super();
	}
	/**
	 * Devuelve una lista de códigos
	 * 
	 * @param num
	 *            total de codigos
	 * @param numWinner
	 *            codigos premiados
	 * @param raffleSaved
	 *            rifa a asociar
	 * @param prizeSaved
	 *            premio a asociar
	 * @return Collection<Code>
	 */
	public Collection<Code> getCodes(final Integer num, final Integer numWinner, final Raffle raffleSaved, final Prize prizeSaved) {
		// TODO Auto-generated method stub
		final Collection<Code> saveds = new ArrayList<Code>();

		for (int i = 0; i < num - numWinner; i++) {
			final Code code = this.getCode(prizeSaved, raffleSaved);
			saveds.add(this.save(code));
		}

		for (int i = 0; i < numWinner; i++) {
			final Code code = this.getCode(prizeSaved, raffleSaved);
			code.setIsWinner(true);
			saveds.add(this.save(code));
		}
		return saveds;
	}
	private Code save(final Code code) {
		// TODO Auto-generated method stub
		Assert.notNull(code);
		Code saved = null;
		if (code.getId() == 0) {	//Alta nueva
			Assert.isTrue(LoginService.hasRole("MANAGER"));
			saved = this.codeRepository.save(code);
		}
		return saved;
	}
	/**
	 * Devuelve un código NO PREMIADO válido para una rifa y un premio
	 * 
	 * @param prizeSaved
	 * @param raffleSaved
	 * @return
	 */
	private Code getCode(final Prize prizeSaved, final Raffle raffleSaved) {
		// TODO Auto-generated method stub
		final Code resul = new Code();
		resul.setPrize(prizeSaved);
		resul.setRaffle(raffleSaved);
		resul.setIsWinner(false);

		//generamos cadena

		String string = RandomStringUtils.randomAlphabetic(4).toUpperCase() + RandomStringUtils.random(1, new char[] {
			'/', '-', ' '
		}) + RandomStringUtils.randomAlphanumeric(4).toUpperCase();

		final Random r = new Random();
		if (r.nextInt(2) == 1)
			string = string.replace(" ", "");

		resul.setCode(string);

		return resul;
	}
	public List<Code> codeByRaffle(int id) {
		Assert.notNull(id);
		return codeRepository.codeByRaffle(id);
	}
	public List<Participation> codeByParticipation(int id) {
		Assert.notNull(id);
		return codeRepository.codeByParticipation(id);
	}

}
