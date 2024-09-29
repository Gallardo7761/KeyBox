package dev.gallardo.kb.common;

/**
* This is an interface for classes that want to be notified of changes in the database.
* @author Gallardo7761
* @version 1.0
* @since 1.0
*/
public interface DBChangeListener {
    void onDBChanged();
}
