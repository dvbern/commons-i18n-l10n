package ch.dvbern.oss.commons.i18nl10n;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibm.icu.util.ULocale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;

import static java.util.function.Function.identity;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpAcceptLanguageHeaderParser {
	private static final ULocale[] EMPTY = new ULocale[0];
	private final AppLanguage fallback;
	private final Map<ULocale, AppLanguage> byLocale;
	private final ULocale[] locales;

	public static HttpAcceptLanguageHeaderParser forAppLanguages(
			Collection<AppLanguage> available,
			AppLanguage fallback
	) {
		// convert available to a map keyed by ULocale
		var byLocale = available.stream()
				.collect(Collectors.toMap(
						AppLanguage::locale,
						identity()
				));

		if (!Objects.equals(byLocale.get(fallback.locale()), fallback)) {
			throw new IllegalArgumentException("Fallback %s is not contained in available locales: %s".formatted(
					fallback,
					available));
		}

		return new HttpAcceptLanguageHeaderParser(
				fallback,
				byLocale,
				byLocale.keySet().toArray(EMPTY)
		);
	}

	public AppLanguage parse(@Nullable String acceptLanguageHeader) {
		var header = trimToEmpty(acceptLanguageHeader);
		if (header.isEmpty()) {
			return fallback;
		}

		var locale = ULocale.acceptLanguage(header, locales, null);
		if (locale == null) {
			return fallback;
		}

		var language = Objects.requireNonNull(byLocale.get(locale), () -> "Locale %s not found???".formatted(locale));

		return language;
	}

	private String trimToEmpty(@Nullable String str) {
		return str == null
				? ""
				: str.trim();
	}
}
