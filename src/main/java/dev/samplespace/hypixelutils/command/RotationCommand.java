package dev.samplespace.hypixelutils.command;

import dev.samplespace.hypixelutils.gui.RotationScreen;
import dev.samplespace.hypixelutils.util.Scheduler;
import dev.samplespace.hypixelutils.util.rotation.MapPool;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RotationCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "rotation";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/rotation [mode]";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("bwrotation", "maprotation");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Scheduler.get().schedule(() -> Minecraft.getMinecraft().displayGuiScreen(args.length == 0
                ? new RotationScreen(MapPool.VALUES)
                : new RotationScreen(Arrays.stream(args)
                    .map(String::toUpperCase)
                    .map(MapPool::valueOf)
                    .toArray(MapPool[]::new)
        )), 1);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return Arrays.stream(MapPool.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@NotNull ICommand iCommand) {
        return 0;
    }
}
