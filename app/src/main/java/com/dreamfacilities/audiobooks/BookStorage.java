package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 19/02/17.
 */

public interface BookStorage {
    boolean hasLastBook();
    String getLastBook();
    void saveLastBook(String key);
}
