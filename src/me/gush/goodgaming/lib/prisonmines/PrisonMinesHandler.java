package me.gush.goodgaming.lib.prisonmines;

import net.lightshard.prisonmines.PrisonMines;
import net.lightshard.prisonmines.mine.Mine;
import net.lightshard.prisonmines.mine.reset.ResetType;

public class PrisonMinesHandler {
    public static PrisonMinesHandler IMP = new PrisonMinesHandler();

    public boolean handleBlockBreak(int amount, Mine mine) {
        PrisonMines.getAPI().onBlockBreak(mine, amount);
        double percentMined = PrisonMines.getAPI().getPercentMined(mine);
        if (percentMined > 15.0) {
            mine.reset();
            return true;
        }
        return false;
    }

    public void setMinesResetType(ResetType asyncInstantReset) {
        for (Mine mine : PrisonMines.getAPI().getMines()) {
            mine.setResetType(asyncInstantReset);
        }
    }
}

