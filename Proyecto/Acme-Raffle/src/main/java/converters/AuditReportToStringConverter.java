package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AuditReport;

@Component
@Transactional
public class AuditReportToStringConverter implements Converter<AuditReport, String>{

	@Override
	public String convert(final AuditReport auditor) {
		String res;

		if (auditor == null)
			res = null;
		else
			res = String.valueOf(auditor.getId());

		return res;
	}
}
