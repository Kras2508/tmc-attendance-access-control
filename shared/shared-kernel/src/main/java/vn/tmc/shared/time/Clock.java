package vn.tmc.shared.time;

import java.time.Instant;

/**
 * Abstraction of the current time (§8.1.1). Lets tests control time (DIP, Table 26).
 */
public interface Clock {
    Instant now();
}
