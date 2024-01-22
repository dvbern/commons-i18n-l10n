package ch.dvbern.oss.commons.i18nl10n;

import java.util.ResourceBundle;

import com.ibm.icu.util.ULocale;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTLStrategyTest {

	private static final String TESTING_BUNDLE_NAME = DefaultTLStrategyTest.class.getName();

	private final AppLanguage de = new AppLanguageDTO(ULocale.GERMANY);
	private final AppLanguage fr = new AppLanguageDTO(ULocale.FRANCE);
	private final ResourceBundle bundle = ResourceBundle.getBundle(TESTING_BUNDLE_NAME, de.javaLocale());
	private final DefaultTLStrategy tl = DefaultTLStrategy.createDefault(bundle, de);

	@Test
	void sanity_check_bundle() {
		assertThat(bundle.getString("hello.world"))
				.isEqualTo("Hallo Welt");
	}

	@Test
	void sanity_check_bundle_loading_works_for_differing_AppLanguage() {
		var bundleFR = ResourceBundle.getBundle(TESTING_BUNDLE_NAME, fr.javaLocale());

		assertThat(bundleFR.getString("hello.world"))
				.isEqualTo("Bonjour le monde");
	}

	@Nested
	class translate_with_I18nMessage {

		@Test
		void translates_without_args() {
			var actual = tl.translate(I18nMessage.of("hello.world"));

			assertThat(actual)
					.isEqualTo("Hallo Welt");
		}

		@Test
		void translates_with_one_arg() {
			var actual = tl.translate(I18nMessage.of("hello.stranger", "stranger", "Fremder"));

			assertThat(actual)
					.isEqualTo("Hallo Fremder!");
		}

		@Test
		void translates_named_args_replacing_placeholders() {
			var actual = tl.translate(
					I18nMessage.of(
							"hello.my.friends",
							"Ich",
							"Henk",
							"Kumpel",
							"Joe",
							"Freundin",
							"Fränzi"
					));

			assertThat(actual)
					.isEqualTo("Hallo Fränzi, Joe und Henk!");
		}

		@Test
		void returns_the_message_key_and_args_if_the_key_is_not_found() {
			assertThat(tl.translate(I18nMessage.of("not.found", "arg1", "foo", "arg2", "bar")))
					.isEqualTo("!not.found[arg1=foo, arg2=bar]!");
		}
	}

	@Nested
	class custom_formatter_factory {
		private final DefaultTLStrategy customFactory = DefaultTLStrategy.create(
				bundle,
				de,
				(message, language, bundle) ->
						args -> "%s, {%s}".formatted(message.key().toString(), Helpers.prettyPrintMap(args))
		);

		@Test
		void calls_the_custom_formatter() {
			var actual = customFactory.translate(I18nMessage.of("hello.world", "foo", "bar"));

			assertThat(actual)
					.isEqualTo("hello.world, {foo=bar}");
		}

	}
}
