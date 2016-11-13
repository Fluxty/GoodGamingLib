package me.gush.goodgaming.lib.prisonmines;

import com.boydti.fawe.object.FaweQueue;
import com.boydti.fawe.object.RunnableVal2;
import me.gush.goodgaming.lib.GoodGamingLib;
import net.lightshard.prisonmines.event.mine.MinePostResetEvent;
import net.lightshard.prisonmines.mine.Mine;

public class ResetProgressTask
extends RunnableVal2<FaweQueue.ProgressType, Integer> {
    private final Mine mine;

    public ResetProgressTask(Mine mine) {
        this.mine = mine;
    }

    public void run(FaweQueue.ProgressType arg0, Integer arg1) {
        if (arg0 == FaweQueue.ProgressType.DONE) {
            GoodGamingLib.getInstance().getPrisonBlockDataStore().clearBlocks();
            this.mine.setResetting(false);
            this.mine.getPercentageReset().onReset();
            this.mine.getTimedReset().onReset();
            new MinePostResetEvent(this.mine).call();
        }
    }
}

