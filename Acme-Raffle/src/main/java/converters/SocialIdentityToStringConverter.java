
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialIdentity;

@Component
@Transactional
public class SocialIdentityToStringConverter implements Converter<SocialIdentity, String> {

	@Override
	public String convert(final SocialIdentity socialIdentity) {
		String res;

		if (socialIdentity == null)
			res = null;
		else
			res = String.valueOf(socialIdentity.getId());

		return res;
	}

}
