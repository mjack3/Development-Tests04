package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TabooWord;
import repositories.TabooWordRepository;

@Component
@Transactional
public class StringToTabooWordConverter implements Converter<String, TabooWord>{

	@Autowired
	TabooWordRepository	repository;


	@Override
	public TabooWord convert(final String text) {
		TabooWord res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.repository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
