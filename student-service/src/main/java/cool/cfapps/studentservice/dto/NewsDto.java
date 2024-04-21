package cool.cfapps.studentservice.dto;

import jakarta.validation.constraints.NotBlank;

public record NewsDto(@NotBlank String title) {
}
