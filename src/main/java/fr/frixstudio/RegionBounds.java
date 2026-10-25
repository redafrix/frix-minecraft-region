package fr.frixstudio;

public record RegionBounds(String world, int minX, int maxX, int minZ, int maxZ) {
    public RegionBounds {
        if (world == null || world.isBlank() || minX > maxX || minZ > maxZ)
            throw new IllegalArgumentException("Invalid region bounds");
    }
    public boolean contains(String dimension, double x, double z) {
        return world.equals(dimension) && Double.isFinite(x) && Double.isFinite(z)
                && x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }
}
