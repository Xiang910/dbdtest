package DBD;

import Core.Commands.Hub;
import DBD.Command.GameCommand;
import DBD.Listener.ArenaListener;
import DBD.Listener.LobbyListener;
import cn.nukkit.plugin.PluginBase;

public class Loader extends PluginBase {

    public static Loader instance;

    public static Loader getInstance(){
        return instance;
    }



    public void onEnable(){
        getLogger().info("Dead by Daylight Version :" + getDescription().getVersion());
        getLogger().info("██████╗ ██████╗ ██████╗ ");
        getLogger().info("██╔══██╗██╔══██╗██╔══██╗");
        getLogger().info("██║  ██║██████╔╝██║  ██║");
        getLogger().info("██║  ██║██╔══██╗██║  ██║");
        getLogger().info("██████╔╝██████╔╝██████╔╝");
        getLogger().info("╚═════╝ ╚═════╝ ╚═════╝ ");
        registerListenerNCommand();
    }

    public void onDisable(){
        getLogger().info("Plugins Disabling..");
    }

    public void registerListenerNCommand(){
        getServer().getPluginManager().registerEvents(new LobbyListener(), this);
        getServer().getPluginManager().registerEvents(new ArenaListener(), this);
        getServer().getCommandMap().register("dbd", new GameCommand());
    }

}
