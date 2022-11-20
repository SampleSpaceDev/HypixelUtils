package me.lachy.hypixelutils.modules.teamupgradedisplay;

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
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamUpgradeDisplayModule implements IModule {

    private final List<TeamUpgrade> upgrades = new ArrayList<>();


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

        SafeHudRenderCallback.EVENT.register(ctx -> {
            FontRenderer fontRenderer = ctx.getFontRenderer();

            AbstractCanvas canvas = ctx.getCanvas();
            try (TransformCanvas canvas1 = canvas.transform()
                    .translate(0, 0)
                    .openTransformation()) {
                for (int i = 0; i < this.upgrades.size(); i++) {
                    TeamUpgrade upgrade = this.upgrades.get(i);
                    fontRenderer.drawStringWithShadow(upgrade.name(), 0, fontRenderer.FONT_HEIGHT * i, 0xFFCFEFFF);
                }
            }
        });
    }

    public void addUpgrade(TeamUpgrade upgrade) {
        this.upgrades.add(upgrade);
    }
}
