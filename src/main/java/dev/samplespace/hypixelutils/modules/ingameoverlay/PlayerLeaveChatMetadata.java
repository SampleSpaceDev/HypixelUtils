package dev.samplespace.hypixelutils.modules.ingameoverlay;

import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatMetadata;

public class PlayerLeaveChatMetadata implements IChatMetadata {

    private final String username;

    public PlayerLeaveChatMetadata(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
