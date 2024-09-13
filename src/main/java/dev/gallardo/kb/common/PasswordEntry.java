package dev.gallardo.kb.common;

public class PasswordEntry {
    private String service;
    private String username;
    private String password;
    private boolean passwordVisible;

    public PasswordEntry(String service, String username, String password) {
        this.service = service;
        this.username = username;
        this.password = password;
        this.passwordVisible = false;  // Por defecto, la contraseña está oculta
    }

    // Getters y setters
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordVisible() {
        return passwordVisible;
    }

    public void setPasswordVisible(boolean passwordVisible) {
        this.passwordVisible = passwordVisible;
    }
}
