package jrl.qam2final.Helper;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Helper class for managing translations in the application using keys and language bundles. This will allow me to
 * easily translate all additional GUI elements like the Log-In Screen.
 *
 * @author Jeffrey Robert Lynch
 */
public class TranslationManagerHelper {

    private static final String BUNDLE_BASE_NAME = "bundle.language";
    private static ResourceBundle resourceBundle;

    static {
        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    /**
     * Retrieves the translated string for a key depending on language setting.
     *
     * @param key Key
     * @return Translated string based on key.
     */
    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Sets the application's language based on locale.
     *
     * @param locale The locale for the language setting.
     */
    public static void setLanguage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }
}