package edu.architect_711.words.entities;

import java.time.LocalDateTime;
import java.util.Set;

public interface Group {
    Long getId();
    String getTitle();
    String getDescription();
    Set<Long> getWordsIds();
    LocalDateTime getCreated();
}
