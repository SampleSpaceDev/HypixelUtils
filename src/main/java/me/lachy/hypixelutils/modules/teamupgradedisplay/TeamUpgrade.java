package me.lachy.hypixelutils.modules.teamupgradedisplay;

public enum TeamUpgrade {

    SHARPENED_SWORDS("Sharpened Swords"),
    REINFORCED_ARMOR_I("Reinforced Armor I"),
    REINFORCED_ARMOR_II("Reinforced Armor II"),
    REINFORCED_ARMOR_III("Reinforced Armor III"),
    REINFORCED_ARMOR_IV("Reinforced Armor IV"),
    MANIAC_MINER_I("Maniac Miner I"),
    MANIAC_MINER_II("Maniac Miner II"),
    IRON_FORGE("Iron Forge"),
    GOLDEN_FORGE("Golden Forge"),
    EMERALD_FORGE("Emerald Forge"),
    MOLTEN_FORGE("Molten Forge"),
    HEAL_POOL("Heal Pool"),
    DRAGON_BUFF("Dragon Buff");

    private final String niceName;

    TeamUpgrade(String niceName) {
        this.niceName = niceName;
    }

    public static TeamUpgrade fromString(String parsed) {
        return valueOf(parsed.replace(" ", "_").toUpperCase());
    }

    public String getNiceName() {
        return this.niceName;
    }
}
