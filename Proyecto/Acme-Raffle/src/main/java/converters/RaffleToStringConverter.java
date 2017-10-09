
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Raffle;

@Component
@Transactional
public class RaffleToStringConverter implements Converter<Raffle, String> {

	@Override
	public String convert(final Raffle userAccount) {
		String res;

		if (userAccount == null)
			res = null;
		else
			res = String.valueOf(userAccount.getId());

		return res;
	}

}
