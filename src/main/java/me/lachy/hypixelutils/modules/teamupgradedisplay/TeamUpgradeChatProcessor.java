package me.lachy.hypixelutils.modules.teamupgradedisplay;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMessageData;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatProcessor;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageEventData;
import me.lachy.hypixelutils.util.VerbalExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamUpgradeChatProcessor implements IChatProcessor {

    private static final Pattern teamUpgrade = VerbalExpression.regex()
            .startOfLine()
            .capture("username").word().count(1, 16).endCapture()
            .then(" purchased ")
            .capture("upgrade").anything().endCapture()
            .buildPattern();

    @Override
    public void process(ProcessedChatMessageEventData.Builder builder, ChatMessageData chatMessageData) {
        String message = chatMessageData.getComponent().getPlainString();
        Matcher matcher = teamUpgrade.matcher(message);
        if (matcher.matches()) {
            TeamUpgrade upgrade = TeamUpgrade.fromString(matcher.group("upgrade"));
            if (upgrade == null) {
                return;
            }
            String username = matcher.group("username");
            CecerMCLib.get(TeamUpgradeDisplayModule.class).addUpgrade(upgrade, username);
        }
    }

}
