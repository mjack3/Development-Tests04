
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TaxonomyRepository;
import domain.Taxonomy;

@Component
@Transactional
public class StringToTaxonomyConverter implements Converter<String, Taxonomy> {

	@Autowired
	TaxonomyRepository	userAccountRepository;


	@Override
	public Taxonomy convert(final String text) {
		Taxonomy res;
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
