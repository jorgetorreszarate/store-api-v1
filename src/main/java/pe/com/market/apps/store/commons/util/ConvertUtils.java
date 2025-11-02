package pe.com.market.apps.store.commons.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertUtils {

	public static LocalDate toDate(String str) {
		if (!StringUtils.hasText(str)) {
			return null;
		}

		var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(str, formatter);
	}
}
