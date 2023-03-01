package dev.samplespace.hypixelutils.util.rotation;

public class BedwarsMap {
    private final String id;
    private final MapPool pool;
    private final String name;
    private final String festival;

    public BedwarsMap(String id, MapPool pool, String name, String festival) {
        this.id = id;
        this.pool = pool;
        this.name = name;
        this.festival = festival;
    }

    public String getId() {
        return this.id;
    }

    public MapPool getPool() {
        return this.pool;
    }

    public String getName() {
        return this.name;
    }

    public MapFestival getFestival() {
        return MapFestival.valueOf(this.festival);
    }
}
