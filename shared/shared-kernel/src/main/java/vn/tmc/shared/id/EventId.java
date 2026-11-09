package vn.tmc.shared.id;

import java.util.Objects;
import java.util.UUID;

/**
 * Kiosk-generated UUID identifying one scan (E1 idempotent event identity).
 * <p>The same value is used online, in the kiosk outbox and in the turnstile request (§6.1.4).
 */
public record EventId(UUID value) {

    public EventId {
        Objects.requireNonNull(value, "value");
    }

    public static EventId of(String text) {
        return new EventId(UUID.fromString(text));
    }

    public static EventId random() {
        return new EventId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
