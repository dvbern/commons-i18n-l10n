package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a key for a translation.
 * It is a simple wrapper around a String,
 * but it enforces a certain pattern that is compatible with all known translation tools.
 */
public record I18nKey(
		String value
) implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("java:S5998")
	private static final Pattern PATH_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]*(\\.[a-zA-Z0-9_-]+)*$");

	public I18nKey {
		value = validate(value);
	}

	public static I18nKey of(String path) {
		return new I18nKey(path);
	}

	private static String validate(String path) {
		boolean valid = isValid(path);

		if (!valid) {
			throw new IllegalArgumentException("I18nKey is not valid: >%s<".formatted(path));
		}

		return path;
	}

	static boolean isValid(String path) {
		Objects.requireNonNull(path, "path must not be null");

		var matches = PATH_PATTERN.matcher(path)
				.matches();

		return matches;
	}

	public I18nKey append(String suffix) {
		return I18nKey.of(value() + '.' + suffix);
	}

	@Override
	public String toString() {
		return value();
	}
}
