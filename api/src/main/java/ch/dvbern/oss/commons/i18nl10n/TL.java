package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serializable;

/**
 * Convenience wrapper for {@link Translator} to allow for easier usage in day-to-day usage.
 */
@SuppressWarnings("unused")
public interface TL extends Translator {

	String translate(
			String key
	);

	String translate(
			String key,
			String a1,
			Serializable v1
	);

	String translate(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2
	);

	String translate(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3
	);

	String translate(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3,
			String a4,
			Serializable v4
	);

	String translate(
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
	);

}
