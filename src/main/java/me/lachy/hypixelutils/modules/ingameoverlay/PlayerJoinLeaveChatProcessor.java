package me.lachy.hypixelutils.modules.ingameoverlay;

import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMessageData;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatProcessor;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageEventData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.lachy.hypixelutils.util.VerbalExpression.regex;

public class PlayerJoinLeaveChatProcessor implements IChatProcessor {

    // ^(?<username>[0-9A-Za-z_]{1,16}) has joined \((?<count>[0-9]+)/(?<max>[0-9]+)\)!$
    private static final Pattern playerJoin = regex()
            .startOfLine()
            .capture("username").word().count(1, 16).endCapture()
            .then(" has joined (")
            .capture("count").digit().oneOrMore().endCapture()
            .then("/")
            .capture("max").digit().oneOrMore().endCapture()
            .then(")!")
            .endOfLine()
            .buildPattern();

    private static final Pattern playerLeave = regex()
            .startOfLine()
            .capture("username").word().count(1, 16).endCapture()
            .then(" has quit!")
            .buildPattern();

    @Override
    public void process(ProcessedChatMessageEventData.Builder builder, ChatMessageData chatMessageData) {
        String message = chatMessageData.getComponent().getPlainString();
        Matcher matcher = playerJoin.matcher(message);
        if (matcher.matches()) {
            String username = matcher.group("username");
            int count = Integer.parseInt(matcher.group("count"));
            int max = Integer.parseInt(matcher.group("max"));

            builder.addMetadata(new PlayerJoinChatMetadata(username, count, max));
            return;
        }

        matcher = playerLeave.matcher(message);
        if (matcher.matches()) {
            String username = matcher.group("username");
            builder.addMetadata(new PlayerLeaveChatMetadata(username));
        }
    }
}
