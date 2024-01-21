package ch.dvbern.oss.commons.i18nl10n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class I18nKeyTest {

	@ParameterizedTest
	@CsvSource({
			"a",
			"a.b",
			"a.b.c",
			"a.b.c.d",
			"a.b.c.d.e",
			"aa",
			"aa.bb",
			"aa.bb.cc",
			"aa.bb.cc.dd",
			"aa.bb.cc.dd.ee",
			"this.contains.cer_tain.allowed.special-characters",
			"array.0.notation",
	})
	void parsing_a_valid_key_with_various_lengths(String key) {
		var i18nKey = I18nKey.of(key);

		assertThat(i18nKey.value())
				.isEqualTo(key);
	}

	@ParameterizedTest
	@CsvSource({
			"''",
			".b",
			"a.",
			"a..d",
			"a.b.",
			"a.b.",
			"_must-start-with-letter",
			"-must-start-with-letter",
			"0must-start-with-letter",
	})
	void parsing_an_invalid_key_throws(String key) {
		var ex = assertThrows(
				IllegalArgumentException.class,
				() -> I18nKey.of(key)
		);

		assertThat(ex)
				.hasMessage("I18nKey is not valid: >" + key + '<');
	}

	@Test
	void parsing_a_null_key_throws() {
		@SuppressWarnings("DataFlowIssue")
		var ex = assertThrows(
				NullPointerException.class,
				() -> I18nKey.of(null)
		);

		assertThat(ex)
				.hasMessage("path must not be null");
	}

	@ParameterizedTest
	@CsvSource({
			"a.b, c,   a.b.c",
			"a.b, c.d, a.b.c.d",
	})
	void appending_generates_a_valid_key(
			String start,
			String append,
			String expected
	) {
		var actual = I18nKey.of(start)
				.append(append);

		assertThat(actual)
				.hasToString(expected);
	}

}
