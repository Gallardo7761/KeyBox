package dev.gallardo.kb.validators;

import dev.gallardo.kb.common.PasswordEntry;
import java.util.ArrayList;
import java.util.List;

public class PasswordEntryValidator {
    private final PasswordEntry passwordEntry;
    private final List<String> errorMessages;

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final String URL_REGEX = "^(http|https)://.*$";

    public PasswordEntryValidator(PasswordEntry passwordEntry) {
        this.passwordEntry = passwordEntry;
        this.errorMessages = new ArrayList<>();
    }

    public boolean validate() {
        errorMessages.clear();  // Clear previous errors

        validateTitle();
        validateUserName();
        validateUrl();
        validatePassword();

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

    private void validatePassword() {
        if (passwordEntry.getPassword() == null || passwordEntry.getPassword().isEmpty()) {
            errorMessages.add("La contraseña no puede estar vacía.");
        } else if (!passwordEntry.getPassword().matches(PASSWORD_REGEX)) {
            errorMessages.add("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial.");
        }
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }
}
