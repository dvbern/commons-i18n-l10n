package ch.dvbern.oss.commons.i18nl10n;

/**
 * Basic workhorse for translation purposes. See {@link TL} for a more convenient interface.
 */
@SuppressWarnings("InterfaceNeverImplemented")
public interface Translator {
	String translate(I18nMessage message);
}
