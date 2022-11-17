package me.lachy.hypixelutils.modules.locraw;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;
import com.cecer1.projects.mc.cecermclib.common.environment.IClientEnvironment;
import com.cecer1.projects.mc.cecermclib.common.modules.AllModulesRegisteredCallback;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMetadataModule;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.Collections;
import java.util.Set;

public class LocrawModule implements IModule {

    private LocrawChatProcessor.LocrawMetadata lastState;

    @Override
    public boolean isEnvironmentSupported(AbstractEnvironment environment) {
        return environment instanceof IClientEnvironment;
    }

    @Override
    public Set<Class<? extends IModule>> getDependencies() {
        return Collections.singleton(ChatMetadataModule.class);
    }

    @Override
    public void onModuleRegister() {
        IModule.super.onModuleRegister();

        AllModulesRegisteredCallback.EVENT.register(() -> CecerMCLib.get(ChatMetadataModule.class).registerProcessor(new LocrawChatProcessor()));

        ProcessedChatMessageCallback.EVENT.register(data -> {
            LocrawChatProcessor.LocrawMetadata metadata = data.getMetadata(LocrawChatProcessor.LocrawMetadata.class);
            if (metadata != null) {
                this.lastState = metadata;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(this.lastState.getServer()));
            }
        });
    }

    public LocrawChatProcessor.LocrawMetadata get() {
        return this.lastState;
    }
}
