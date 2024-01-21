package ch.dvbern.oss.commons.i18nl10n;

import java.util.Set;

import com.ibm.icu.util.ULocale;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("FieldNamingConvention")
class HttpAcceptLanguageHeaderParserTest {

	private record LangDTO(ULocale locale) implements AppLanguage {
	}

	private static final AppLanguage DE = new LangDTO(ULocale.forLanguageTag("de-CH"));
	private static final AppLanguage IT = new LangDTO(ULocale.forLanguageTag("it-CH"));

	@Nested
	class with_configured_Parser {
		private final HttpAcceptLanguageHeaderParser sut = HttpAcceptLanguageHeaderParser.forAppLanguages(
				Set.of(DE, IT),
				DE
		);

		@Test
		void finds_best_matching_entry_in_header() {
			var actual = sut.parse("de-CH,de;q=0.9,en-US;q=0.8,en;q=0.7,fr;q=0.6");

			assertThat(actual)
					.isEqualTo(DE);
		}

		@Test
		void finds_matching_entry_further_down_in_header() {
			var actual = sut.parse("en-US;q=0.9,en-US;q=0.8,en;q=0.7,it;q=0.6");

			assertThat(actual)
					.isEqualTo(IT);
		}

		@Test
		void returns_fallback_if_no_entry_matches_in_header() {
			var actual = sut.parse("en-US,en;q=0.9");

			assertThat(actual)
					.isEqualTo(DE);
		}

		@Test
		void returns_fallback_on_null_header() {
			var actual = sut.parse(null);

			assertThat(actual)
					.isEqualTo(DE);
		}

		@Test
		void returns_fallback_on_empty_header() {
			var actual = sut.parse("");

			assertThat(actual)
					.isEqualTo(DE);
		}
	}

	@Nested
	class factory_input_validation {
		@Test
		void throws_with_debug_info_if_fallback_is_not_contained_in_available_langs() {
			var ex = assertThrows(
					IllegalArgumentException.class,
					() -> HttpAcceptLanguageHeaderParser.forAppLanguages(Set.of(IT), DE)
			);

			assertThat(ex)
					.hasMessageContaining("it_CH")
					.hasMessageContaining("de_CH");
		}
	}
}