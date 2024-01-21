package ch.dvbern.oss.commons.i18nl10n;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.ibm.icu.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTLImpl extends AbstractTL {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTLImpl.class);
    private final AppLanguage appLanguage;
    private final ResourceBundle bundle;

    public DefaultTLImpl(AppLanguage appLanguage, ResourceBundle bundle) {
        this.appLanguage = Objects.requireNonNull(appLanguage);
        this.bundle = Objects.requireNonNull(bundle);
    }

    @Override
    public String translate(I18nMessage message) {
        try {
            var pattern = bundle.getString(message.key().value());

            var messageFormat = new MessageFormat(pattern, appLanguage.locale());

            var result = messageFormat.format(message.args());

            return result;
        } catch (MissingResourceException ignored) {
            LOG.warn("Translation not found for key/locale: {}/{}", message.key(), appLanguage);
            var result = "!%s[%s]!".formatted(message.key().toString(), Helpers.prettyPrintMap(message.args()));

            return result;
        }
    }

}
