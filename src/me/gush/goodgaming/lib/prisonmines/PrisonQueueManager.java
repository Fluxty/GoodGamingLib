package me.gush.goodgaming.lib.prisonmines;

import com.boydti.fawe.object.FaweQueue;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PrisonQueueManager {
    Map<FaweQueue, String> activeQueues = new ConcurrentHashMap<FaweQueue, String>();
    private static PrisonQueueManager instance = new PrisonQueueManager();

    public void addQueue(FaweQueue queue, String prisonName) {
        this.activeQueues.put(queue, prisonName);
    }

    public void cancelActiveMineQueues(String name) {
        ArrayList<FaweQueue> applicableQueues = new ArrayList<FaweQueue>();
        for (Map.Entry<FaweQueue, String> queue2 : this.activeQueues.entrySet()) {
            if (!queue2.getValue().equalsIgnoreCase(name)) continue;
            queue2.getKey().dequeue();
            queue2.getKey().clear();
            applicableQueues.add(queue2.getKey());
        }
        for (FaweQueue queue : applicableQueues) {
            this.activeQueues.remove((Object)queue);
        }
    }

    public static PrisonQueueManager getInstance() {
        return instance;
    }
}

