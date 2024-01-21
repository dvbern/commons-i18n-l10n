package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serializable;

@SuppressWarnings({ "unused", "AbstractClassNeverImplemented" })
public abstract class AbstractTL implements TL {

	@Override
	public String translate(
			String key
	) {
		return translate(I18nMessage.of(key));
	}

	@Override
	public String translate(
			String key,
			String a1,
			Serializable v1
	) {
		return translate(I18nMessage.of(key, a1, v1));
	}

	@Override
	public String translate(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2
	) {
		return translate(I18nMessage.of(key, a1, v1, a2, v2));
	}

	@Override
	public String translate(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3
	) {
		return translate(I18nMessage.of(key, a1, v1, a2, v2, a3, v3));
	}

	@Override
	public String translate(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3,
			String a4,
			Serializable v4
	) {
		return translate(I18nMessage.of(key, a1, v1, a2, v2, a3, v3, a4, v4));
	}

	@Override
	public String translate(
			String key,
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
		return translate(I18nMessage.of(key, a1, v1, a2, v2, a3, v3, a4, v4, a5, v5));
	}
}
