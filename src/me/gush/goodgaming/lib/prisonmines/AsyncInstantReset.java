package me.gush.goodgaming.lib.prisonmines;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FaweQueue;
import com.boydti.fawe.object.FaweQueue.ProgressType;
import com.boydti.fawe.object.RunnableVal2;
import com.boydti.fawe.util.TaskManager;

import java.util.logging.Level;
import me.gush.goodgaming.lib.prisonmines.PrisonQueueManager;
import me.gush.goodgaming.lib.prisonmines.ResetProgressTask;
import net.lightshard.prisonmines.event.mine.MinePreResetEvent;
import net.lightshard.prisonmines.mine.Mine;
import net.lightshard.prisonmines.mine.reset.ResetType;
import net.lightshard.prisonmines.persistence.file.config.SettingsConfig;
import net.lightshard.prisonmines.util.FrequencyDensityTable;
import net.lightshard.prisonmines.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

public class AsyncInstantReset
extends ResetType {
    public AsyncInstantReset() {
        super("AsyncInstant");
    }

    public void reset(final Mine mine) {
        PrisonQueueManager.getInstance().cancelActiveMineQueues(mine.getName());
        TaskManager.IMP.async(new Runnable(){

            @Override
            public void run() {
                if (mine.isResetting()) {
                    Logger.log((Level)Level.INFO, (String)(String.valueOf(String.valueOf(mine.getName())) + " tried to reset, but it was already resetting."));
                }
                if (!mine.hasRegion()) {
                    Logger.log((Level)Level.INFO, (String)(String.valueOf(String.valueOf(mine.getName())) + " tried to reset, but it doesn't have a region."));
                    return;
                }
                if (mine.getBlocks().isEmpty()) {
                    Logger.log((Level)Level.INFO, (String)(String.valueOf(String.valueOf(mine.getName())) + " tried to reset, but it doesn't have any blocks."));
                    return;
                }
                if (SettingsConfig.RESET_ONLYWHENPLAYERSONLINE.getBoolean().booleanValue() && Bukkit.getOnlinePlayers().size() == 0) {
                    return;
                }
                MinePreResetEvent minePreResetEvent = new MinePreResetEvent(mine);
                minePreResetEvent.call();
                if (minePreResetEvent.isCancelled()) {
                    return;
                }
                mine.setResetting(true);
                FrequencyDensityTable frequencyDensityTable = mine.getFrequencyTable();
                FaweQueue queue = FaweAPI.createQueue((String)mine.getRegion().getWorld().getName(), (boolean)true);
                for (Block block : mine.getRegion()) {
                    	MaterialData materialData = (MaterialData)frequencyDensityTable.random();
                    	queue.setBlock(block.getX(), block.getY(), block.getZ(), materialData.getItemTypeId(), (int)materialData.getData());
                }
                queue.setProgressTask((RunnableVal2)new ResetProgressTask(mine));
                queue.flush();
            }
        });
    }

}

