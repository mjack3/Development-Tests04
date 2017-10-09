
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Taxonomy;

@Component
@Transactional
public class TaxonomyToStringConverter implements Converter<Taxonomy, String> {

	@Override
	public String convert(final Taxonomy userAccount) {
		String res;

		if (userAccount == null)
			res = null;
		else
			res = String.valueOf(userAccount.getId());

		return res;
	}

}
