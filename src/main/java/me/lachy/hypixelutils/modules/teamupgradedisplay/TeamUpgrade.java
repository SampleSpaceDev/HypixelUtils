package me.lachy.hypixelutils.modules.teamupgradedisplay;

import org.apache.commons.lang3.text.WordUtils;

public enum TeamUpgrade {

    SHARPENED_SWORDS,
    REINFORCED_ARMOR_I,
    REINFORCED_ARMOR_II,
    REINFORCED_ARMOR_III,
    REINFORCED_ARMOR_IV,
    MANIAC_MINER_I,
    MANIAC_MINER_II,
    IRON_FORGE,
    GOLDEN_FORGE,
    EMERALD_FORGE,
    MOLTEN_FORGE,
    HEAL_POOL,
    DRAGON_BUFF;

    TeamUpgrade() {
    }

    public static TeamUpgrade fromString(String parsed) {
        return valueOf(parsed.replace(" ", "_").toUpperCase());
    }

    public String getNiceName() {
        return WordUtils.capitalizeFully(this.name().toLowerCase().replace("_", ""));
    }
}
