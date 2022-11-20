package me.lachy.hypixelutils.modules.teamupgradedisplay;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMessageData;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.IChatProcessor;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageEventData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.lachy.hypixelutils.util.VerbalExpression.regex;

public class TeamUpgradeChatProcessor implements IChatProcessor {

    private static final Pattern teamUpgrade = regex()
            .startOfLine()
            .capture("username").word().count(1, 16).endCapture()
            .then(" purchased ")
            .capture("upgrade").word().oneOrMore().endCapture()
            .buildPattern();

    @Override
    public void process(ProcessedChatMessageEventData.Builder builder, ChatMessageData chatMessageData) {
        String message = chatMessageData.getComponent().getPlainString();
        Matcher matcher = teamUpgrade.matcher(message);
        if (matcher.matches()) {
            TeamUpgrade upgrade = TeamUpgrade.fromString(matcher.group("upgrade"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(upgrade.name()));
            CecerMCLib.get(TeamUpgradeDisplayModule.class).addUpgrade(upgrade);
        }
    }

}
