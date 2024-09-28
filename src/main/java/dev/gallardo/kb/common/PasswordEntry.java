package dev.gallardo.kb.common;

import java.util.StringJoiner;

@SuppressWarnings("unused")
public class PasswordEntry {
    private Integer passwordId;
    private String title;
    private String userName;
    private String url;
    private String password;
    private boolean passwordVisible;

    public PasswordEntry(Integer passwordId, String title, String userName, String url, String password) {
        this.passwordId = passwordId;
        this.title = title;
        this.userName = userName;
        this.url = url;
        this.password = password;
        this.passwordVisible = false;  // Por defecto, la contraseña está oculta
    }

    public PasswordEntry(PasswordEntry passwordEntry) {
        this.passwordId = passwordEntry.getPasswordId();
        this.title = passwordEntry.getTitle();
        this.userName = passwordEntry.getUserName();
        this.url = passwordEntry.getUrl();
        this.password = passwordEntry.getPassword();
        this.passwordVisible = passwordEntry.isPasswordVisible();
    }

    public Integer getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Integer passwordId) {
        this.passwordId = passwordId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordEntry that = (PasswordEntry) o;
        return passwordId.equals(that.passwordId) && title.equals(that.title) && userName.equals(that.userName) && url.equals(that.url) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = passwordId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PasswordEntry.class.getSimpleName() + "[", "]")
                .add("passwordId=" + passwordId)
                .add("title='" + title + "'")
                .add("userName='" + userName + "'")
                .add("website='" + url + "'")
                .add("password='" + password + "'")
                .add("passwordVisible=" + passwordVisible)
                .toString();
    }
}
