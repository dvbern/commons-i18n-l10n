package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.assertj.core.api.AbstractAssert;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({ "NonBooleanMethodNameMayNotStartWithQuestion", "unused" })
public class I18nMessageAssertions extends AbstractAssert<I18nMessageAssertions, I18nMessage> {
	private @Nullable Function<AppLanguage, Translator> translatorFactory;
	private final Set<AppLanguage> translatedLanguages = new HashSet<>();

	protected I18nMessageAssertions(I18nMessage message) {
		super(message, I18nMessageAssertions.class);
	}

	public static I18nMessageAssertions assertThatI18nMessage(I18nMessage message) {
		return new I18nMessageAssertions(message);
	}

	/**
	 * assert that it has the expected key
	 */
	public I18nMessageAssertions hasKey(I18nKey expectedKey) {
		isNotNull();

		if (!Objects.equals(actual.key(), expectedKey)) {
			// fail with actual and expected values
			throw failureWithActualExpected(
					actual.key(),
					expectedKey,
					"Expected I18nKey to have key %s but was %s", expectedKey, actual.key()
			);
		}

		return this;
	}

	/**
	 * assert that it has the expected key
	 */
	public I18nMessageAssertions hasKey(String expectedKey) {
		return hasKey(I18nKey.of(expectedKey));
	}

	/**
	 * assert that it has no args
	 */
	public I18nMessageAssertions hasNoArgs() {
		isNotNull();

		if (!actual.args().isEmpty()) {
			// fail with actual and expected values
			failWithMessage(
					"Expected I18nKey to have no args but has %s",
					prettyPrintMap(actual.args())
			);
		}

		return this;
	}

	/**
	 * assert that it has the expected args
	 */
	public I18nMessageAssertions hasArgs(
			Map<String, Serializable> expectedArgs
	) {
		isNotNull();

		if (!Objects.equals(actual.args(), expectedArgs)) {
			// fail with actual and expected values
			failWithActualExpectedAndMessage(
					actual.args(),
					expectedArgs,
					"Expected I18nKey to have args %s but was %s",
					prettyPrintMap(expectedArgs),
					prettyPrintMap(actual.args())
			);
		}

		return this;
	}

	/**
	 * assert that it has the expected args
	 */
	public I18nMessageAssertions hasArgs(
			String a1,
			Serializable v1
	) {
		return hasArgs(Map.of(a1, v1));
	}

	/**
	 * assert that it has the expected args
	 */
	public I18nMessageAssertions hasArgs(
			String a1,
			Serializable v1,
			String a2,
			Serializable v2
	) {
		return hasArgs(Map.of(a1, v1, a2, v2));
	}

	/**
	 * assert that it has the expected args
	 */
	public I18nMessageAssertions hasArgs(
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3
	) {
		return hasArgs(Map.of(a1, v1, a2, v2, a3, v3));
	}

	/**
	 * assert that it has the expected args
	 */
	public I18nMessageAssertions hasArgs(
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3,
			String a4,
			Serializable v4
	) {
		return hasArgs(Map.of(a1, v1, a2, v2, a3, v3, a4, v4));
	}

	/**
	 * assert that it has the expected args
	 */
	public I18nMessageAssertions hasArgs(
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3,
			String a4,
			Serializable v4,
			String a5,
			Serializable v5
	) {
		return hasArgs(Map.of(a1, v1, a2, v2, a3, v3, a4, v4, a5, v5));
	}

	public I18nMessageAssertions usingTranslatorFactory(
			Function<AppLanguage, Translator> translatorFactory
	) {
		this.translatorFactory = translatorFactory;

		return this;
	}

	public I18nMessageAssertions translatesTo(
			AppLanguage language,
			String expected
	) {
		isNotNull();
		var tl = fromFactory(language);

		var translation = tl.translate(this.actual);

		if (!Objects.equals(translation, expected)) {
			failWithActualExpectedAndMessage(
					translation,
					expected,
					"The translation differs from the expected text for language %s/%s",
					language,
					language.locale()
			);
		}

		this.translatedLanguages.add(language);

		return this;
	}

	public I18nMessageAssertions hasAllLanguagesTranslated(Collection<AppLanguage> requiredLanguages) {
		isNotNull();

		var missingLanguages = requiredLanguages.stream()
				.filter(l -> !translatedLanguages.contains(l))
				.collect(Collectors.toSet());

		if (!missingLanguages.isEmpty()) {
			throw failure(
					"Expected all languages to be translated, but the following are missing: %s",
					missingLanguages
			);
		}

		return this;
	}

	public I18nMessageAssertions hasAllLanguagesTranslated(AppLanguage... requiredLanguages) {
		return hasAllLanguagesTranslated(Set.of(requiredLanguages));
	}

	private Translator fromFactory(AppLanguage language) {
		isNotNull();
		if (translatorFactory == null) {
			throw failure("tlFactory has not been wet");
		}
		return translatorFactory.apply(language);
	}

	private static String prettyPrintMap(Map<String, Serializable> args) {
		return args.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> "%s=%s".formatted(e.getKey(), e.getValue()))
				.collect(Collectors.joining(", "));
	}
}
