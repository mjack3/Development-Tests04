
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RaffleRepository;
import domain.Raffle;

@Component
@Transactional
public class StringToRaffleConverter implements Converter<String, Raffle> {

	@Autowired
	RaffleRepository	userAccountRepository;


	@Override
	public Raffle convert(final String text) {
		Raffle res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.userAccountRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
