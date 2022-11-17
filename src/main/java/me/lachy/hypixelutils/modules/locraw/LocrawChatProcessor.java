package me.lachy.hypixelutils.modules.locraw;

import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMessageData;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatMetadata;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatProcessor;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageEventData;
import com.google.gson.annotations.SerializedName;
import me.lachy.hypixelutils.HypixelUtils;

public class LocrawChatProcessor implements IChatProcessor {

    @Override
    public void process(ProcessedChatMessageEventData.Builder builder, ChatMessageData chatMessageData) {
        LocrawMetadata metadata = LocrawMetadata.fromString(chatMessageData.getComponent().getPlainString());
        if (metadata == null) {
            return;
        }
        builder.addMetadata(metadata);
    }

    public static class LocrawMetadata implements IChatMetadata {
        @SerializedName("server") private String server;
        @SerializedName("gametype") private String gameType;
        @SerializedName("lobbyname") private String lobbyName;
        @SerializedName("mode") private String mode;
        @SerializedName("map") private String map;

        public static LocrawMetadata fromString(String jsonString) {
            if (!jsonString.startsWith("{")) {
                return null;
            }
            LocrawMetadata metadata = HypixelUtils.GSON.fromJson(jsonString, LocrawMetadata.class);
            if (metadata.getServer() == null) {
                return null;
            }
            return metadata;
        }

        public String getServer() {
            return this.server;
        }

        public String getGameType() {
            return this.gameType;
        }

        public String getLobbyName() {
            return this.lobbyName;
        }

        public String getMode() {
            return this.mode;
        }

        public String getMap() {
            return this.map;
        }
    }
}
