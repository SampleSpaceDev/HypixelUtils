package dev.samplespace.hypixelutils.modules.teamupgradedisplay;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;
import com.cecer1.projects.mc.cecermclib.common.modules.AllModulesRegisteredCallback;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMetadataModule;
import com.cecer1.projects.mc.cecermclib.forge.environment.ForgeClientEnvironment;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.RenderingModule;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.SafeHudRenderCallback;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.context.AbstractCanvas;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.context.TransformCanvas;
import com.google.common.collect.ImmutableSet;
import dev.samplespace.hypixelutils.util.MinecraftColour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.Collectors;

public class TeamUpgradeDisplayModule implements IModule {

    private final Map<TeamUpgrade, String> upgrades = new HashMap<>();


    @Override
    public boolean isEnvironmentSupported(AbstractEnvironment environment) {
        return environment instanceof ForgeClientEnvironment;
    }

    @Override
    public Set<Class<? extends IModule>> getDependencies() {
        return ImmutableSet.of(RenderingModule.class, ChatMetadataModule.class);
    }

    @Override
    public void onModuleRegister() {
        AllModulesRegisteredCallback.EVENT.register(() -> CecerMCLib.get(ChatMetadataModule.class).registerProcessor(new TeamUpgradeChatProcessor()));

        Minecraft minecraft = Minecraft.getMinecraft();
        SafeHudRenderCallback.EVENT.register(ctx -> {
            FontRenderer fontRenderer = ctx.getFontRenderer();

            EntityPlayerSP player = minecraft.thePlayer;
            ScoreObjective objective = null;
            if (player != null) {
                Scoreboard scoreboard = player.getWorldScoreboard();
                if (scoreboard != null) {
                    objective = scoreboard.getObjectiveInDisplaySlot(1);
                    if (objective == null) {
                        return;
                    }
                    if (objective.getName().equals("PreScoreboard")) {
                        this.clearUpgrades();
                        return;
                    }
                }
            }
//
//            if (this.getCurrentTitle().equals("VICTORY!")) {
//                this.clearUpgrades();
//                return;
//            }

            AbstractCanvas canvas = ctx.getCanvas();
            try (TransformCanvas canvas1 = canvas.transform()
                    .openTransformation()) {

                if (this.upgrades.isEmpty() || objective == null || !objective.getName().equals("BForeboard")) {
                    return;
                }

                fontRenderer.drawStringWithShadow("\u00a7nPurchased Upgrades:", 5, 5, MinecraftColour.GREEN.getARGB());

                int y = fontRenderer.FONT_HEIGHT + 8;
                List<Map.Entry<TeamUpgrade, String>> entries = this.upgrades.entrySet().stream()
                        .sorted(Comparator.comparing(entry -> entry.getKey().ordinal()))
                        .collect(Collectors.toList());

                for (Map.Entry<TeamUpgrade, String> entry : entries) {
                    String upgrade = entry.getKey().getNiceName();
                    String purchaser = entry.getValue();

                    fontRenderer.drawStringWithShadow(upgrade, 5, y, MinecraftColour.GOLD.getARGB());

                    fontRenderer.drawStringWithShadow(String.format("(%s)", purchaser),
                            5 + fontRenderer.getStringWidth(upgrade + " "), y,
                            MinecraftColour.GRAY.getARGB());
                    y += fontRenderer.FONT_HEIGHT + 2;
                }
            }
        });
    }

//    public String getCurrentTitle() {
//        return this.title;
//    }
//
//    public void setCurrentTitle(String title) {
//        this.title = title;
//    }

    public void addUpgrade(TeamUpgrade upgrade, String purchaser) {
        this.upgrades.put(upgrade, purchaser);
    }

    public void clearUpgrades() {
        this.upgrades.clear();
    }
}
