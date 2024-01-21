package ch.dvbern.oss.commons.i18nl10n;

import java.util.Comparator;
import java.util.Locale;

import com.ibm.icu.util.ULocale;

@FunctionalInterface
public interface AppLanguage {
	ULocale locale();

	default Locale javaLocale() {
		return locale().toLocale();
	}

	static Comparator<AppLanguage> comparator() {
		return Comparator.comparing(AppLanguage::locale);
	}

}
