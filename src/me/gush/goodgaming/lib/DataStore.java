package me.gush.goodgaming.lib;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;

public class DataStore {
    private List<Block> brokenBlocks = new ArrayList<Block>();

    public List<Block> getBrokenBlocks() {
        return this.brokenBlocks;
    }

    public void clearBlocks() {
        this.brokenBlocks.clear();
    }

    public void addBlock(Block block) {
        this.brokenBlocks.add(block);
    }
}

