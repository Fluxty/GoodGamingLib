package me.gush.goodgaming.lib.listener;

import me.gush.goodgaming.lib.GoodGamingLib;
import me.gush.goodgaming.lib.prisonmines.PrisonMinesHandler;
import net.lightshard.prisonmines.PrisonMines;
import net.lightshard.prisonmines.mine.Mine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GGListener
implements Listener {
    private final GoodGamingLib ggLib;

    public GGListener(GoodGamingLib ggLib) {
        this.ggLib = ggLib;
    }

    @EventHandler(priority=EventPriority.NORMAL)
    void onBreakBlock(BlockBreakEvent event) {
        Mine mine = PrisonMines.getAPI().getByLocation(event.getBlock().getLocation());
        if (mine == null) {
            return;
        }
        if (mine.isResetting()) {
            event.setCancelled(true);
            return;
        }
        PrisonMinesHandler.IMP.handleBlockBreak(1, mine);
    }
}

