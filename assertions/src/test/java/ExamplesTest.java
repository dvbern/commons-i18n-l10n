import java.util.function.Function;

import ch.dvbern.oss.commons.i18nl10n.AppLanguage;
import ch.dvbern.oss.commons.i18nl10n.DefaultTranslatorStrategy;
import ch.dvbern.oss.commons.i18nl10n.I18nMessage;
import ch.dvbern.oss.commons.i18nl10n.Translator;
import com.ibm.icu.util.ULocale;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static ch.dvbern.oss.commons.i18nl10n.I18nMessageAssertions.assertThatI18nMessage;
import static org.junit.jupiter.api.Assertions.fail;

public class ExamplesTest {
	private static final String RESOURCE_BUNDLE_NAME = ExamplesTest.class.getSimpleName();

	@RequiredArgsConstructor
	enum TestAppLanguages implements AppLanguage {

		DE(ULocale.forLanguageTag("de-CH")),
		FR(ULocale.forLanguageTag("fr-CH"));

		private final ULocale locale;

		@Override
		public ULocale locale() {
			return locale;
		}
	}

	private final Function<AppLanguage, Translator> tlFactory = appLanguage ->
			message -> DefaultTranslatorStrategy.createDefault(appLanguage, RESOURCE_BUNDLE_NAME)
					.translate(message);

	// business code
	public I18nMessage sayHello(String toWho) {
		return I18nMessage.of("hello.world", "world", toWho);
	}

	// test code
	@Test
	public void has_all_translations_for_hello_world() {
		assertThatI18nMessage(sayHello("World"))
				.usingTranslatorFactory(tlFactory)
				.translatesTo(TestAppLanguages.DE, "Hallo World")
				.translatesTo(TestAppLanguages.FR, "Bonjour World")
				.hasAllLanguagesTranslated(TestAppLanguages.values());
	}

	@Test
	public void throws_because_FR_is_missing() {
		try {
			assertThatI18nMessage(I18nMessage.of("nur.in.deutsch"))
					.usingTranslatorFactory(tlFactory)
					.translatesTo(TestAppLanguages.DE, "Wolpertinger")
					.translatesTo(TestAppLanguages.FR, "Dahu")
					.hasAllLanguagesTranslated(TestAppLanguages.values());

			fail();
		} catch (AssertionError ingored) {
			// expected
		}
	}
}
