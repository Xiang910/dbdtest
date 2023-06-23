package DBD.Listener;

import Core.Loader;
import DBD.Manager.FormManager;
import DBD.Manager.ItemManager;
import DBD.Room;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.ItemID;

import static DBD.Room.getPlayerRoom;
import static DBD.Room.playerRoom;

public class LobbyListener implements Listener {

    @EventHandler
    public void onLevelChange(EntityLevelChangeEvent e){
        var entity = e.getEntity();
        var target = e.getTarget();
        var toWorld = target.getName();
        var party = getPlayerRoom(entity.getName());

        if (toWorld.equalsIgnoreCase("DBDlobby1")){
            if (entity instanceof Player) {
                if (Loader.playerPlace.containsValue("Lobby")) {
                    Loader.playerPlace.remove(entity.getName());
                    Loader.playerPlace.put(entity.getName(), "DBD");
                    ItemManager.sendLobbyItem((Player) entity);
                }
            }
        } else if (toWorld.equalsIgnoreCase("world")) {
            if (party != null && party.getLeader() != null && party.getLeader().getLevelName().equals("world")) {
                for (Player player : party.getMembers()) {
                    if (player != null) {
                        player.sendMessage("房间已解散！");
                        party.leaveRoom(player);
                        Room.globalRoom.remove(playerRoom.get(player.getName()));
                    }
                }
            } else if (party != null) {
                party.leaveRoom((Player) entity);
                for (Player member : party.getMembers()){
                    member.sendMessage( entity.getName() + " 已离开房间");
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        var p = e.getPlayer();

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        var p = e.getPlayer();
        var item = e.getItem();


        if (p.getLevel().equals((Object) Server.getInstance().getLevelByName("DBDlobby1"))) {
            if (e.getAction().equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR) || e.getAction().equals(PlayerInteractEvent.Action.LEFT_CLICK_AIR) || e.getAction().equals(PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)) {
                if (item != null) {
                    if (item.hasCustomName()) {
                        if (item.getId() == ItemID.IRON_SWORD) {
                            FormManager.sendAvailableRoomMenu(p);
                        } else if (item.getId() == ItemID.DIAMOND_SWORD) {
                            FormManager.sendRoomMenu(p);
                        } else if (item.getId() == ItemID.ENCHANTED_BOOK) {
                            FormManager.sendHowToPlayForm(p);
                        }
                    }
                }
            }
        }
    }
}
