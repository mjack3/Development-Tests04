
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PrizeRepository;
import domain.Prize;

@Component
@Transactional
public class StringToPrizeConverter implements Converter<String, Prize> {

	@Autowired
	PrizeRepository	userAccountRepository;


	@Override
	public Prize convert(final String text) {
		Prize res;
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
