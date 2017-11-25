
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ParticipationRepository;
import domain.Participation;

@Component
@Transactional
public class StringToParticipationConverter implements Converter<String, Participation> {

	@Autowired
	ParticipationRepository	userAccountRepository;


	@Override
	public Participation convert(final String text) {
		Participation res;
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
