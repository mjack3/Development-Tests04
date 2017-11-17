
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Participation;

@Component
@Transactional
public class ParticipationToStringConverter implements Converter<Participation, String> {

	@Override
	public String convert(final Participation userAccount) {
		String res;

		if (userAccount == null)
			res = null;
		else
			res = String.valueOf(userAccount.getId());

		return res;
	}

}
