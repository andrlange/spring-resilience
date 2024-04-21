package cool.cfapps.studentservice.entity;

import java.time.Instant;

public record NewsRecord(String title, Instant createdOn) {
}
