
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Prize;

@Component
@Transactional
public class PrizeToStringConverter implements Converter<Prize, String> {

	@Override
	public String convert(final Prize userAccount) {
		String res;

		if (userAccount == null)
			res = null;
		else
			res = String.valueOf(userAccount.getId());

		return res;
	}

}
