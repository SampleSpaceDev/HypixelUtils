package dev.samplespace.hypixelutils.util.rotation;

import dev.samplespace.hypixelutils.util.MinecraftColour;

public enum MapFestival {
    NONE(MinecraftColour.GRAY),
    NEW_YEAR(MinecraftColour.BLUE),
    LUNAR_NEW_YEAR(MinecraftColour.DARK_AQUA),
    EASTER(MinecraftColour.DARK_GREEN),
    SUMMER(MinecraftColour.YELLOW),
    HALLOWEEN(MinecraftColour.GOLD),
    CHRISTMAS(MinecraftColour.RED);

    private final MinecraftColour colour;

    MapFestival(MinecraftColour colour) {
        this.colour = colour;
    }

    public MinecraftColour getColour() {
        return this.colour;
    }
}
