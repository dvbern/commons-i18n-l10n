package ch.dvbern.oss.commons.i18nl10n;

import java.util.Comparator;
import java.util.Locale;

import com.ibm.icu.util.ULocale;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("EmptyClass")
class AppLanguageTest {

	@Nested
	class javaLocale {

		@ParameterizedTest
		@CsvSource({
				"de-CH, de-CH",
				"de-CH, de-ch",
				"de,    de",
				"de-IT, de-IT",
				"de-IT, de-IT-CH",
				"de-IT, de-IT-IT",
				// root locale
				"'',''"
		})
		void javaLocale_returns_a_matching_Locale(String expectedText, String inputText) {
			AppLanguage impl = new AppLanguageDTO(ULocale.forLanguageTag(inputText));

			var actual = impl.javaLocale();

			var expected = Locale.forLanguageTag(expectedText);
			assertThat(actual)
					.isEqualTo(expected);
		}

	}

	@Nested
	class comparator {
		private final AppLanguage de = new AppLanguageDTO(ULocale.GERMANY);
		private final AppLanguage fr = new AppLanguageDTO(ULocale.FRANCE);

		private final Comparator<AppLanguage> cmp = AppLanguage.comparator();

		@Test
		void same_values_are_equal() {
			//noinspection EqualsWithItself
			assertThat(cmp.compare(de, de))
					.isEqualTo(0);
		}

		@Test
		void equal_values_are_equal() {
			assertThat(cmp.compare(de, new AppLanguageDTO(ULocale.GERMANY)))
					.isEqualTo(0);
		}

		@Test
		void de_is_smaller_than_fr() {
			assertThat(cmp.compare(de, fr))
					.isLessThan(0);
			assertThat(cmp.compare(fr, de))
					.isGreaterThan(0);
		}

	}

}