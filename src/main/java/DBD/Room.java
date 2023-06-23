package DBD;

import cn.nukkit.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {

    private Player leader;
    private String partyName;
    private List<Player> members;

    public static HashMap<String, Room> playerRoom = new HashMap<>();
    public static ArrayList<Room> globalRoom = new ArrayList<>();

    public Room(Player leader, String partyName){
        this.leader = leader;
        this.partyName = partyName;
        this.members = new ArrayList<>();
        this.members.add(leader);
    }

    public String getPartyName(){
        return partyName;
    }

    public Player getLeader(){
        return leader;
    }

    public List<Player> getMembers(){
        return members;
    }

    public void joinRoom(Player p){
        members.add(p);
        Room.playerRoom.put(p.getName(), this);
    }

    public void leaveRoom(Player p){
        members.remove(p);
        Room.playerRoom.remove(p.getName());
    }

    public void closeRoom(Player p){
        members.remove(p);
        Room.globalRoom.remove(Room.playerRoom.get(p.getName()));
    }

    public static Room getPlayerRoom(String playerName) {
        return playerRoom.get(playerName);
    }

    //var room = new Room(p, "partyName");
    //playerRoom.put(p.getName(), room);
    //globalRoom.add(room);
}
