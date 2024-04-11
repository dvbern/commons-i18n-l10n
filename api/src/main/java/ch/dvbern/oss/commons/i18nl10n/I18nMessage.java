package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public record I18nMessage(
		I18nKey key,
		Map<String, Serializable> args
) implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	public I18nMessage {
		Objects.requireNonNull(key);
		args = Map.copyOf(Objects.requireNonNull(args));
	}

	public static I18nMessage of(
			I18nKey key,
			Map<String, Serializable> args
	) {
		return new I18nMessage(key, args);
	}

	public static I18nMessage of(
			String key,
			Map<String, Serializable> args
	) {
		return new I18nMessage(I18nKey.of(key), args);
	}

	public static I18nMessage of(
			String key
	) {
		return of(I18nKey.of(key), Map.of());
	}

	public static I18nMessage of(
			String key,
			String a1,
			Serializable v1
	) {
		return of(I18nKey.of(key), Map.of(a1, v1));
	}

	public static I18nMessage of(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2
	) {
		return of(I18nKey.of(key), Map.of(a1, v1, a2, v2));
	}

	public static I18nMessage of(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3) {
		return of(I18nKey.of(key), Map.of(a1, v1, a2, v2, a3, v3));
	}

	public static I18nMessage of(
			String key,
			String a1,
			Serializable v1,
			String a2,
			Serializable v2,
			String a3,
			Serializable v3,
			String a4,
			Serializable v4) {
		return of(I18nKey.of(key), Map.of(a1, v1, a2, v2, a3, v3, a4, v4));
	}

	public static I18nMessage of(
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
			Serializable v5) {
		return of(I18nKey.of(key), Map.of(a1, v1, a2, v2, a3, v3, a4, v4, a5, v5));
	}
}
