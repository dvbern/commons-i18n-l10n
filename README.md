# commons-i18n-l10n
Helper methods to facilitate i18n and l10n concerns



## Usage

### assertions

```java
enum TestAppLanguages implements AppLanguage {
    DE(ULocale.forLanguageTag("de-CH")),
    FR(ULocale.forLanguageTag("fr-CH"));
	// code omitted for brevity
}

// business code
public I18nMessage sayHello(String toWho) {
    return I18nMessage.of("hello.world", "world", toWho);
}

// test code
@Test
public void has_all_translations_for_hello_world() {
    assertThatI18nMessage(sayHello("World"))
            .usingTLFactory(tlFactory)
            .translatesTo(TestAppLanguages.DE, "Hallo World")
            .translatesTo(TestAppLanguages.FR, "Bonjour World")
            .hasAllLanguagesTranslated(TestAppLanguages.values());
}
```