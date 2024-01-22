package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serializable;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ibm.icu.text.MessageFormat;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultTLStrategy extends AbstractTL {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTLStrategy.class);

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

	public static DefaultTLStrategy create(
			ResourceBundle bundle,
			AppLanguage appLanguage,
			FormatterFactory formatterFactory
	) {
		return new DefaultTLStrategy(bundle, appLanguage, formatterFactory);
	}

	public static DefaultTLStrategy createDefault(
			ResourceBundle bundle,
			AppLanguage appLanguage
	) {
		return create(bundle, appLanguage, defaultMessageFormatFactory());
	}

	@Override
	public String translate(I18nMessage message) {
		try {
			var messageFormat = formatterFactory.create(message, appLanguage, resourceBundle);
			var result = messageFormat.format(message.args());

			return result;
		} catch (MissingResourceException ignored) {
			LOG.warn("Translation not found for key/locale: {}/{}", message.key(), appLanguage);
			var result = "!%s[%s]!".formatted(message.key().toString(), Helpers.prettyPrintMap(message.args()));

			return result;
		}
	}

}
