package ch.dvbern.oss.commons.i18nl10n;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("HideUtilityClassConstructor")
class Helpers {
	static String prettyPrintMap(Map<String, Serializable> args) {
		return args.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> "%s=%s".formatted(e.getKey(), e.getValue()))
				.collect(Collectors.joining(", "));
	}
}
