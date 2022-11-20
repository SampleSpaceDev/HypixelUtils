package me.lachy.hypixelutils.modules.locraw;

import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMessageData;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatProcessor;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageEventData;

public class LocrawChatProcessor implements IChatProcessor {

    @Override
    public void process(ProcessedChatMessageEventData.Builder builder, ChatMessageData chatMessageData) {
        LocrawMetadata metadata = LocrawMetadata.fromString(chatMessageData.getComponent().getPlainString());
        if (metadata == null) {
            return;
        }
        builder.addMetadata(metadata);
    }
}
