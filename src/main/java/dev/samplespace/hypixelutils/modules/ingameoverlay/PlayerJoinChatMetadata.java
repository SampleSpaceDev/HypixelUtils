package dev.samplespace.hypixelutils.modules.ingameoverlay;

import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatMetadata;

public class PlayerJoinChatMetadata implements IChatMetadata {
    private final String username;
    private final int count;
    private final int max;

    public PlayerJoinChatMetadata(String username, int count, int max) {
        this.username = username;
        this.count = count;
        this.max = max;
    }

    public String getUsername() {
        return this.username;
    }

    public int getCount() {
        return this.count;
    }

    public int getMax() {
        return this.max;
    }
}
