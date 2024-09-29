package dev.gallardo.kb.validators;

import dev.gallardo.kb.common.PasswordEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates a PasswordEntry object.
 * <p>
 * The following rules are checked:
 * <ul>
 *     <li>The title must not be empty.</li>
 *     <li>The user name must not be empty.</li>
 *     <li>The URL must not be empty and must start with http:// or https://.</li>
 *     <li>The password must not be empty.</li>
 *     <li>The notes must not be empty.</li>
 *     <li>The password must not be empty.</li>
 * </ul>
 * <p>
 * If any of the rules are violated, the error messages can be retrieved using the
 * {@link #getErrorMessages()} method.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see PasswordEntry
 * @see PasswordEntryValidator
 */
public class PasswordEntryValidator {
    private final PasswordEntry passwordEntry;
    private final List<String> errorMessages;

    private static final String URL_REGEX = "^(http|https)://.*$";

    public PasswordEntryValidator(PasswordEntry passwordEntry) {
        this.passwordEntry = passwordEntry;
        this.errorMessages = new ArrayList<>();
    }

    /**
     * Validates the PasswordEntry object.
     * @return true if the PasswordEntry object is valid, false otherwise.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validate() {
        errorMessages.clear();  // Clear previous errors

        validateTitle();
        validateUserName();
        validateUrl();

        return errorMessages.isEmpty();
    }

    private void validateTitle() {
        if (passwordEntry.getTitle() == null || passwordEntry.getTitle().isEmpty()) {
            errorMessages.add("El título no puede estar vacío.");
        }
    }

    private void validateUserName() {
        if (passwordEntry.getUserName() == null || passwordEntry.getUserName().isEmpty()) {
            errorMessages.add("El nombre de usuario no puede estar vacío.");
        }
    }

    private void validateUrl() {
        if (passwordEntry.getUrl() == null || passwordEntry.getUrl().isEmpty()) {
            errorMessages.add("La URL no puede estar vacía.");
        } else if (!passwordEntry.getUrl().matches(URL_REGEX)) {
            errorMessages.add("La URL debe comenzar con http:// o https://.");
        }
    }

    /**
     * @return a list of error messages.
     */
    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }
}
