package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
// mimic record behavior
@Accessors(fluent = true)
public class I18nKey implements Serializable {
	@Serial
	private static final long serialVersionUID = 956682065123085284L;

	// This is a no-brainer pattern, but it should be sufficient for an MVP.
	// Most importantly: Components separated by dots, no whitespace and no special characters
	// that might break external translation tools like Weblate.
	@SuppressWarnings("java:S5998")
	private static final Pattern PATH_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]*(\\.[a-zA-Z0-9_-]+)*$");

	private final String value;

	public static I18nKey of(String path) {
		validate(path);

		return new I18nKey(path);
	}

	private static void validate(String path) {
		boolean valid = isValid(path);

		if (!valid) {
			throw new IllegalArgumentException("I18nKey is not valid: >%s<".formatted(path));
		}
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
