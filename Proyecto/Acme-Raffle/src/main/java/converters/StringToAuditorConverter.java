package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Auditor;
import repositories.AuditorRepository;

@Component
@Transactional
public class StringToAuditorConverter implements Converter<String, Auditor>{

	@Autowired
	AuditorRepository	auditorRepository;


	@Override
	public Auditor convert(final String text) {
		Auditor res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.auditorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
