package dev.samplespace.hypixelutils.util.rotation;

public enum MapPool {
    EIGHT_SLOW("BEDWARS_8TEAMS_SLOW", "8 Teams Long & Tactical"),
    EIGHT_FAST("BEDWARS_8TEAMS_FAST", "8 Teams Quick & Rushy"),
    FOUR_SLOW("BEDWARS_4TEAMS_SLOW", "4 Teams Long & Tactical"),
    FOUR_FAST("BEDWARS_4TEAMS_FAST", "4 Teams Quick & Rushy");

    private final String id;
    private final String niceName;
    public static final MapPool[] VALUES = values();

    MapPool(String id, String niceName) {
        this.id = id;
        this.niceName = niceName;
    }

    public static MapPool getById(String id) {
        for (MapPool pool : VALUES) {
            if (pool.id.equals(id)) {
                return pool;
            }
        }
        return null;
    }

    public String getId() {
        return this.id;
    }
    public String getNiceName() {
        return this.niceName;
    }
}