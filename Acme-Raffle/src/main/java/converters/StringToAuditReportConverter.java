package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AuditReport;
import repositories.AuditReportRepository;

@Component
@Transactional
public class StringToAuditReportConverter implements Converter<String, AuditReport>{

	@Autowired
	AuditReportRepository	auditReportRepository;


	@Override
	public AuditReport convert(final String text) {
		AuditReport res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.auditReportRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
