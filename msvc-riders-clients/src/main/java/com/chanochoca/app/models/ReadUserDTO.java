package com.chanochoca.app.models;

import java.util.Set;
import java.util.UUID;

public record ReadUserDTO(UUID publicId,
                          String firstName,
                          String lastName,
                          String email,
                          String imageUrl,
                          Set<String> authorities) {
}
