package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serializable;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Function;

import com.ibm.icu.text.MessageFormat;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultTranslatorStrategy implements Translator {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTranslatorStrategy.class);

	@FunctionalInterface
	public interface FormatterFactory {
		Formatter create(I18nMessage message, AppLanguage language, ResourceBundle bundle);
	}

	@FunctionalInterface
	public interface Formatter {
		String format(Map<String, Serializable> args);
	}

	private final ResourceBundle resourceBundle;

	@With
	private final AppLanguage appLanguage;

	@With
	private final FormatterFactory formatterFactory;

	public static FormatterFactory defaultMessageFormatFactory() {
		return (message, language, bundle) ->
				new MessageFormat(
						bundle.getString(message.key().toString()),
						language.locale()
				)::format;
	}

	public static DefaultTranslatorStrategy create(
			AppLanguage appLanguage,
			Function<AppLanguage, ResourceBundle> bundleLoader,
			FormatterFactory formatterFactory
	) {
		var bundle = bundleLoader.apply(appLanguage);
		return new DefaultTranslatorStrategy(bundle, appLanguage, formatterFactory);
	}

	public static DefaultTranslatorStrategy createDefault(
			AppLanguage appLanguage,
			Function<AppLanguage, ResourceBundle> bundleLoader
	) {
		return create(appLanguage, bundleLoader, defaultMessageFormatFactory());
	}

	public static DefaultTranslatorStrategy createDefault(
			AppLanguage appLanguage,
			String bundleBaseName
	) {
		Function<AppLanguage, ResourceBundle> bundleLoader =
				language -> ResourceBundle.getBundle(bundleBaseName, language.javaLocale());

		return createDefault(appLanguage, bundleLoader);
	}

	@Override
	public String translate(I18nMessage message) {
		try {
			var messageFormat = formatterFactory.create(message, appLanguage, resourceBundle);
			var result = messageFormat.format(message.args());

			return result;
		} catch (MissingResourceException ignored) {
			LOG.warn("Translation not found for key/locale: {}/{}", message.key(), appLanguage);
			var result = "!%s[%s]!".formatted(message.key().toString(), DebugHelpers.prettyPrintMap(message.args()));

			return result;
		}
	}
}
