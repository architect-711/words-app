package edu.architect_711.words.entities;

import java.time.LocalDateTime;

public interface Word {
    Long getId();
    Long getUserId();
    String getTitle();
    String getTranslation();
    String getDescription();
    String getLanguage();
    LocalDateTime getLocalDateTime();
}
