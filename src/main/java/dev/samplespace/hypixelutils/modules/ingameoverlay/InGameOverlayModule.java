package dev.samplespace.hypixelutils.modules.ingameoverlay;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;
import com.cecer1.projects.mc.cecermclib.common.modules.AllModulesRegisteredCallback;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMetadataModule;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ProcessedChatMessageCallback;
import com.cecer1.projects.mc.cecermclib.forge.environment.ForgeClientEnvironment;
import com.cecer1.projects.mc.cecermclib.forge.modules.rendering.RenderingModule;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class InGameOverlayModule implements IModule {

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
        AllModulesRegisteredCallback.EVENT.register(() -> CecerMCLib.get(ChatMetadataModule.class)
                .registerProcessor(new PlayerJoinLeaveChatProcessor()));

        ProcessedChatMessageCallback.EVENT.register(data -> {
            PlayerJoinChatMetadata joinMetadata = data.getMetadata(PlayerJoinChatMetadata.class);
            if (joinMetadata != null) {
                this.handleJoin(joinMetadata);
                return;
            }

            PlayerLeaveChatMetadata leaveMetadata = data.getMetadata(PlayerLeaveChatMetadata.class);
            if (leaveMetadata != null) {
                this.handleLeave(leaveMetadata);
            }
        });
    }

    public void handleJoin(PlayerJoinChatMetadata metadata) {
        System.out.printf("username: %s, count: %s, max: %s", metadata.getUsername(), metadata.getCount(), metadata.getMax());
    }

    public void handleLeave(PlayerLeaveChatMetadata metadata) {
        System.out.printf("username: %s", metadata.getUsername());
    }
}
