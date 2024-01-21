package ch.dvbern.oss.commons.i18nl10n;

import com.ibm.icu.util.ULocale;

public record AppLanguageDTO(ULocale locale) implements AppLanguage {
}
