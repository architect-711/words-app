package edu.architect_711.words.entities;

import edu.architect_711.words.entities.db.Role;

public interface Account {
    String getUsername();
    String getPassword();
    Role getRole();
}
