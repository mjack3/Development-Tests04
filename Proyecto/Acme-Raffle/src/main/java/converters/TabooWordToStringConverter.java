package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TabooWord;

@Component
@Transactional
public class TabooWordToStringConverter implements Converter<TabooWord, String>{

	@Override
	public String convert(TabooWord word) {
		String res;

		if (word == null)
			res = null;
		else
			res = String.valueOf(word.getId());

		return res;
	}
}
