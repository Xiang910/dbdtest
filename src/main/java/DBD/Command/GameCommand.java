package DBD.Command;

import DBD.Manager.ItemManager;
import DBD.Room;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;

public class GameCommand extends VanillaCommand {

    public GameCommand() {
        super("dbd", "");
        this.commandParameters.clear();
    }

    public boolean execute(CommandSender p, String commandLabel, String[] args){
        if (p.isPlayer()){
            if (Room.playerRoom.containsKey(p.getName())){
                p.sendMessage("你已经加入队伍了. 无法使用该指令");
            }
            ItemManager.sendLobbyItem((Player) p);
        }
        return false;
    }
}
