package ch.dvbern.oss.commons.i18nl10n;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("EmptyClass")
class I18NMessageTest {

	@Nested
	class ofI18nKey_withString {
		@Test
		void message_with_no_arguments_returns_an_instance_with_no_arguments() {
			var actual = I18nMessage.of("a.b.c");

			assertThat(actual)
					.isNotNull()
					.extracting(
							I18nMessage::key,
							I18nMessage::args
					)
					.containsExactly(
							I18nKey.of("a.b.c"),
							Map.of()
					);
		}

		@Test
		void message_with_key_and_arg_map_returns_an_instance_with_the_arguments() {
			var someDate = LocalDate.now();
			var actual = I18nMessage.of("a.b.c", Map.of("string", "a", "long", 123L, "object", someDate));

			assertThat(actual)
					.isNotNull()
					.extracting(I18nMessage::key, I18nMessage::args)
					.containsExactly(
							I18nKey.of("a.b.c"),
							Map.of("string", "a", "long", 123L, "object", someDate)

					);
		}

		@Test
		void message_with_string_key_and_args_returns_an_instance_with_the_arguments() {
			var someDate = LocalDate.now();
			var actual = I18nMessage.of("a.b.c", "string", "a", "long", 123L, "object", someDate);

			assertThat(actual)
					.isNotNull()
					.extracting(I18nMessage::key, I18nMessage::args)
					.containsExactly(
							I18nKey.of("a.b.c"),
							Map.of("string", "a", "long", 123L, "object", someDate)

					);
		}

		@Test
		void an_invalid_key_throws_and_has_useful_debug_info() {
			var ex = assertThrows(
					IllegalArgumentException.class,
					() -> I18nMessage.of("($)")
			);

			assertThat(ex)
					.hasMessageContaining("($)");
		}
	}

	@Nested
	class factories_with_args {
		@Test
		void message_with_1_argument_returns_an_instance_with_no_arguments() {
			var actual = I18nMessage.of("key", "a1", "v1");

			assertThat(actual)
					.extracting(
							I18nMessage::key,
							I18nMessage::args
					)
					.containsExactly(
							I18nKey.of("key"),
							Map.of("a1", "v1")
					);
		}

		@Test
		void message_with_2_arguments_returns_an_instance_with_no_arguments() {
			var actual = I18nMessage.of("key", "a1", "v1", "a2", "v2");

			assertThat(actual)
					.extracting(
							I18nMessage::key,
							I18nMessage::args
					)
					.containsExactly(
							I18nKey.of("key"),
							Map.of("a1", "v1", "a2", "v2")
					);
		}

		@Test
		void message_with_3_arguments_returns_an_instance_with_no_arguments() {
			var actual = I18nMessage.of("key", "a1", "v1", "a2", "v2", "a3", "v3");

			assertThat(actual)
					.extracting(
							I18nMessage::key,
							I18nMessage::args
					)
					.containsExactly(
							I18nKey.of("key"),
							Map.of("a1", "v1", "a2", "v2", "a3", "v3")
					);
		}

		@Test
		void message_with_4_arguments_returns_an_instance_with_no_arguments() {
			var actual = I18nMessage.of("key", "a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4");

			assertThat(actual)
					.extracting(
							I18nMessage::key,
							I18nMessage::args
					)
					.containsExactly(
							I18nKey.of("key"),
							Map.of("a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4")
					);
		}

		@Test
		void message_with_5_arguments_returns_an_instance_with_no_arguments() {
			var actual = I18nMessage.of("key", "a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4", "a5", "v5");

			assertThat(actual)
					.extracting(
							I18nMessage::key,
							I18nMessage::args
					)
					.containsExactly(
							I18nKey.of("key"),
							Map.of("a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4", "a5", "v5")
					);
		}

	}

}
