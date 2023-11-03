package com.example.challengedevonion.model.record;

import com.example.challengedevonion.model.enums.UserRole;

public record RegisterDTO(String usuario, String password, UserRole role) {
}
