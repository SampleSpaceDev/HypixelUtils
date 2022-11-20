package me.lachy.hypixelutils.modules.mapdisplay;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMetadataModule;
import com.cecer1.projects.mc.cecermclib.forge.environment.ForgeClientEnvironment;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.RenderingModule;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.SafeHudRenderCallback;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.context.AbstractCanvas;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.context.TransformCanvas;
import com.google.common.collect.ImmutableSet;
import me.lachy.hypixelutils.modules.locraw.LocrawMetadata;
import me.lachy.hypixelutils.modules.locraw.LocrawModule;
import net.minecraft.client.gui.FontRenderer;

import java.util.Set;

public class MapDisplayModule implements IModule {

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
        SafeHudRenderCallback.EVENT.register(ctx -> {
            AbstractCanvas canvas = ctx.getCanvas();
            FontRenderer fontRenderer = ctx.getFontRenderer();

            LocrawMetadata location = CecerMCLib.get(LocrawModule.class).get();
            if (location == null || location.getMap() == null) {
                return;
            }

            int width = fontRenderer.getStringWidth(location.getMap());
            try (TransformCanvas ignored = canvas.transform()
                    .translate(100, 100)
//                    .translate(canvas.getWidth() - width, fontRenderer.FONT_HEIGHT)
                    .openTransformation()) {
                fontRenderer.drawStringWithShadow(location.getMap(), 0, 0, 0xFF741DDF);
            }
        });
    }
}
