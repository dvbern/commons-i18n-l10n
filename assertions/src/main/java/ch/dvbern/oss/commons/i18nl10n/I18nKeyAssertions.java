package ch.dvbern.oss.commons.i18nl10n;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({ "NonBooleanMethodNameMayNotStartWithQuestion", "unused" })
public class I18nKeyAssertions extends AbstractAssert<I18nKeyAssertions, I18nKey> {
	protected I18nKeyAssertions(I18nKey i18nKey) {
		super(i18nKey, I18nKeyAssertions.class);
	}

	public static I18nKeyAssertions assertThatI18nKey(I18nKey i18nKey) {
		return new I18nKeyAssertions(i18nKey);
	}

	public I18nKeyAssertions hasKey(String expectedKey) {
		return hasKey(I18nKey.of(expectedKey));
	}

	public I18nKeyAssertions hasKey(I18nKey expectedKey) {
		isNotNull();

		if (!Objects.equals(actual, expectedKey)) {
			failWithActualExpectedAndMessage(
					actual.value(),
					expectedKey,
					"Expected I18nKey to have key %s but was %s",
					expectedKey,
					actual.value()
			);
		}

		return this;
	}

}
