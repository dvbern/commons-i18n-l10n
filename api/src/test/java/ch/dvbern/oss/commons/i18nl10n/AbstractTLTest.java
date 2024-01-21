package ch.dvbern.oss.commons.i18nl10n;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AbstractTLTest {

	private I18nMessage delegateParams = null;

	private final AbstractTL sut = new AbstractTL() {
		@Override
		public String translate(I18nMessage message) {
			delegateParams = message;
			return "from-delegate";
		}
	};

	@Test
	void translate_with_0_arguments_delegates_to_translate() {
		var actual = sut.translate("key");

		assertThat(actual)
				.isEqualTo("from-delegate");
		assertThat(delegateParams)
				.isEqualTo(I18nMessage.of("key"));
	}

	@Test
	void translate_with_1_argument_delegates_to_translate() {
		var actual = sut.translate("key", "a1", "v1");

		assertThat(actual)
				.isEqualTo("from-delegate");
		assertThat(delegateParams)
				.isEqualTo(I18nMessage.of("key", "a1", "v1"));
	}

	@Test
	void translate_with_2_arguments_delegates_to_translate() {
		var actual = sut.translate("key", "a1", "v1", "a2", "v2");

		assertThat(actual)
				.isEqualTo("from-delegate");
		assertThat(delegateParams)
				.isEqualTo(I18nMessage.of("key", "a1", "v1", "a2", "v2"));
	}

	@Test
	void translate_with_3_arguments_delegates_to_translate() {
		var actual = sut.translate("key", "a1", "v1", "a2", "v2", "a3", "v3");

		assertThat(actual)
				.isEqualTo("from-delegate");
		assertThat(delegateParams)
				.isEqualTo(I18nMessage.of("key", "a1", "v1", "a2", "v2", "a3", "v3"));
	}

	@Test
	void translate_with_4_arguments_delegates_to_translate() {
		var actual = sut.translate("key", "a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4");

		assertThat(actual)
				.isEqualTo("from-delegate");
		assertThat(delegateParams)
				.isEqualTo(I18nMessage.of("key", "a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4"));
	}

	@Test
	void translate_with_5_arguments_delegates_to_translate() {
		var actual = sut.translate("key", "a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4", "a5", "v5");

		assertThat(actual)
				.isEqualTo("from-delegate");
		assertThat(delegateParams)
				.isEqualTo(I18nMessage.of("key", "a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4", "a5", "v5"));
	}

}