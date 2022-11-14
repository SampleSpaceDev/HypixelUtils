package me.lachy.hypixelutils.gui;

import me.lachy.hypixelutils.util.APIInteraction;
import me.lachy.hypixelutils.util.rotation.BedwarsMap;
import me.lachy.hypixelutils.util.rotation.MapPool;
import me.lachy.hypixelutils.util.rotation.MinecraftColour;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class RotationScreen extends GuiScreen {

    private static final String TITLE = "BedWars Map Rotation";
    private final MapPool[] mapPools;
    private final SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", Locale.ENGLISH);
    private Date lastRotation;
    private boolean loaded = false;


    public RotationScreen(MapPool... mapPools) {
        this.mapPools = mapPools;
        APIInteraction.get().refreshMapCache(this.mapPools).thenAccept(aVoid -> this.loaded = true);
        APIInteraction.get().getLastRotation().thenAccept(instant -> this.lastRotation = Date.from(instant));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        this.fontRendererObj.drawStringWithShadow(TITLE,
                (float) ((this.width / 4) - (this.fontRendererObj.getStringWidth(TITLE) / 2)),
                100/3f,
                0xFF5555FF);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);

        if (!this.loaded) {
            this.drawCenteredString(this.fontRendererObj, "Refeshing...", this.width / 2, 120, MinecraftColour.BLUE.getARGB());
        } else {
            this.drawCenteredString(this.fontRendererObj, String.format("Last Rotation: %s", this.formatter.format(this.lastRotation)), this.width / 2, this.height - 110, MinecraftColour.BLUE.getARGB());
        }

        int colWidth = this.width / (this.mapPools.length);
        BedwarsMap hoveredMap = null;
        for (int i = 0; i < this.mapPools.length; i++) {
            BedwarsMap map = this.drawPool(this.mapPools[i], colWidth * i + (colWidth / 2), mouseX, mouseY);
            if (map != null) {
                hoveredMap = map;
            }
        }

        if (hoveredMap != null) {
            this.drawMapHover(mouseX, mouseY, hoveredMap);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private BedwarsMap drawPool(MapPool pool, int x, int mouseX, int mouseY) {
        int y = 140;

        this.drawCenteredString(this.fontRendererObj, pool.getNiceName(), x, y, MinecraftColour.BLUE.getARGB());
        y += 20;

        BedwarsMap hoveredMap = null;
        for (BedwarsMap map : APIInteraction.get().getMapCache(pool)) {
            if (this.drawMapLabel(map, x, y, mouseX, mouseY)) {
                hoveredMap = map;
            }
            y += 16;
        }
        return hoveredMap;
    }

    private boolean drawMapLabel(BedwarsMap map, int x, int y, int mouseX, int mouseY) {
        String mapName = map.getName();
        int nameWidth = this.fontRendererObj.getStringWidth(mapName);
        x -= nameWidth / 2;
        this.fontRendererObj.drawStringWithShadow(mapName, x, y, map.getFestival().getColour().getARGB());

        return mouseX >= x && mouseY >= y && mouseX <= x + nameWidth && mouseY <= y + this.fontRendererObj.FONT_HEIGHT;
    }

    private void drawMapHover(int mouseX, int mouseY, BedwarsMap map) {
        this.drawHoveringText(Arrays.asList(
                String.format("%s%s%s", EnumChatFormatting.BOLD, map.getFestival().getColour().asMCP(), map.getName()),
                String.format("Festival: %s%s", map.getFestival().getColour().asMCP(), map.getFestival().name())
        ), mouseX, mouseY);
    }
}
