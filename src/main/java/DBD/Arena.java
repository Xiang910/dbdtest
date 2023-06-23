package DBD;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;

import java.util.Random;

public class Arena {

    public static void teleportToReadyRoom(Player p){
        Random rand = new Random();
        int randomTick = rand.nextInt(81) + 60; //3s to 7s
        int randomTickToSec = randomTick / 20; // to seconds
        p.sendMessage("正在准备游戏场地中... (预计等待时间 :" + randomTickToSec + ")");
        p.setImmobile(true);
        Server.getInstance().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {

                p.setImmobile(false);
            }
        }, randomTick);
    }
}
