package vn.tmc.app.config;

import org.springframework.stereotype.Component;
import vn.tmc.shared.time.Clock;

import java.time.Instant;

/**
 * Production Clock backed by the system time (NTP-synchronised).
 */
@Component
public class SystemClock implements Clock {
    
    @Override
    public Instant now() {
        return Instant.now();
    }
}
