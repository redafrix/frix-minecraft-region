package fr.frixstudio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegionBoundsTest {
    @Test void boundariesAndDimensionAreScoped() {
        var region = new RegionBounds("world", -5, 5, -5, 5);
        assertTrue(region.contains("world", -5, 5));
        assertFalse(region.contains("world", 5.01, 0));
        assertFalse(region.contains("world_nether", 0, 0));
    }
    @Test void invalidBoundsFailClosed() {
        assertThrows(IllegalArgumentException.class, () -> new RegionBounds("world", 5, -5, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new RegionBounds("", 0, 1, 0, 1));
    }
    @Test void nonfiniteCoordinatesNeverMatch() {
        var region = new RegionBounds("world", -5, 5, -5, 5);
        assertFalse(region.contains("world", Double.NaN, 0));
        assertFalse(region.contains("world", 0, Double.POSITIVE_INFINITY));
    }
}
