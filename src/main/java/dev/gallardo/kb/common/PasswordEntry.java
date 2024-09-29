package dev.gallardo.kb.common;

import javax.swing.*;
import java.util.StringJoiner;

/**
 * Class that represents a password entry.
 * Contains basic information about a password, such as the title, username, URL, and the password itself.
 * <p>
 * <strong>Attributes:</strong><br>
 * - {@code passwordId}: Unique identifier for the password entry.<br>
 * - {@code title}: Title of the password entry, such as the name of the service.<br>
 * - {@code userName}: Username associated with the password entry.<br>
 * - {@code url}: URL of the service or website related to the password entry.<br>
 * - {@code password}: The password itself.
 * <p>
 * <strong>Methods:</strong><br>
 * - {@code hashCode()}: Generates the hash code of the instance based on the unique identifier.<br>
 * - {@code equals()}: Compares two instances of {@code PasswordEntry}.<br>
 * - {@code toString()}: Returns a string representation of the instance.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unused")
public class PasswordEntry {
    private Integer passwordId;
    private String title;
    private String userName;
    private String url;
    private String password;
    private ImageIcon icon;

    public PasswordEntry(Integer passwordId, String title,
            String userName, String url, String password) {
        this.passwordId = passwordId;
        this.title = title;
        this.userName = userName;
        this.url = url;
        this.password = password;
        this.icon = null;
    }

    public PasswordEntry(PasswordEntry passwordEntry) {
        this.passwordId = passwordEntry.getPasswordId();
        this.title = passwordEntry.getTitle();
        this.userName = passwordEntry.getUserName();
        this.url = passwordEntry.getUrl();
        this.password = passwordEntry.getPassword();
        this.icon = passwordEntry.getIcon();
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

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordEntry that = (PasswordEntry) o;
        return passwordId.equals(that.passwordId) &&
                title.equals(that.title) &&
                userName.equals(that.userName) &&
                url.equals(that.url) &&
                password.equals(that.password);
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
                .add("icon='" + icon + "'")
                .toString();
    }
}
