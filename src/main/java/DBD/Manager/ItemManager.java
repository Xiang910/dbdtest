package DBD.Manager;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;

public class ItemManager {

    public static void sendLobbyItem(Player p){
        Server.getInstance().getLevelByName("DBDlobby1").generateChunk(162, 69);
        p.teleport(new Location(162 + 0.5, 41 + 1, 69 + 0.5, Server.getInstance().getLevelByName("DBDlobby1")).setYaw(180));

        p.getInventory().clearAll();
        p.getCursorInventory().clearAll();
        p.getOffhandInventory().clearAll();
        p.getEffects().clear();

        p.getFoodData().setLevel(20);
        p.getFoodData().setFoodSaturationLevel(5);
        p.setHealth(20);
        p.setFoodEnabled(false);

        p.setGamemode(Player.ADVENTURE);

        p.getInventory().setItem(4, cn.nukkit.item.Item.get(ItemID.IRON_SWORD).setCustomName("§l" + TextFormat.AQUA + "[§a加入游戏§b]").setLore("§a准备"));
        p.getInventory().setItem(0, cn.nukkit.item.Item.get(ItemID.ENCHANTED_BOOK).setCustomName("§l" + TextFormat.AQUA + "[§a游戏介绍§b]").setLore("§a介绍"));
    }

    public static void sendJoinedTeamItem(Player p){
        p.getInventory().clearAll();
        p.getCursorInventory().clearAll();
        p.getOffhandInventory().clearAll();
        p.getEffects().clear();

        p.getFoodData().setLevel(20);
        p.getFoodData().setFoodSaturationLevel(5);
        p.setHealth(20);
        p.setFoodEnabled(false);

        p.setGamemode(Player.ADVENTURE);

        p.getInventory().setItem(4, cn.nukkit.item.Item.get(ItemID.DIAMOND_SWORD).setCustomName("§l" + TextFormat.AQUA + "[§a开始游戏§b]\n仅房主可使用"));
        p.getInventory().setItem(0, cn.nukkit.item.Item.get(ItemID.ENCHANTED_BOOK).setCustomName("§l" + TextFormat.AQUA + "[§a游戏介绍§b]").setLore("§a介绍"));
    }
}
