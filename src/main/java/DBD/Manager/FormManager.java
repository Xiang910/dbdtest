package DBD.Manager;

import DBD.Room;
import NWTW.Engine.FormAPI.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;

import static DBD.Room.playerRoom;

public class FormManager {

    public static void sendAvailableRoomMenu(Player p) {
        SimpleForm form = new SimpleForm("房间列表");

        if (!Room.playerRoom.containsKey(p.getName())) {
            form.addButton("创建房间");
            for (Room room : Room.globalRoom) {
                form.addButton(room.getLeader().getName() + " 的房间");
            }
        }

        form.send(p, (player, formWindowSimple, index) -> {
            if (index == 0) {
                // 创建房间选项
                if (Room.playerRoom.containsKey(player.getName())) {
                    player.sendMessage("你已创建或加入了一个房间！");
                } else {
                    Room room = new Room(player, player.getName());
                    Room.playerRoom.put(player.getName(), room);
                    Room.globalRoom.add(room);
                    ItemManager.sendJoinedTeamItem(player);
                    player.sendMessage("已成功创建房间！");
                    player.getLevel().addSound(p, Sound.NOTE_PLING, 1, 1);
                }
            } else if (index > 0) {
                // 加入现有房间选项
                Room room = Room.globalRoom.get(index - 1);
                room.joinRoom(player);
            }
        });
    }

    public static void sendRoomMenu(Player p) {
        if (!Room.playerRoom.containsKey(p.getName())) {
            p.sendMessage("! 你还未加入任何房间 !");
            p.getLevel().addSound(p, Sound.NOTE_BASSATTACK, 1, 1);
        } else {
            Room party = Room.playerRoom.get(p.getName());
            SimpleForm form = new SimpleForm(TextFormat.DARK_RED + party.getLeader().getName() + "的房间");
            form.addButton(TextFormat.DARK_RED + party.getLeader().getName() + " 房主");
            for (Player player : party.getMembers()) {
                if (!player.getName().equals(party.getLeader().getName())) {
                    form.addButton(TextFormat.GRAY + player.getName() + " 成员");
                }
            }
            form.addButton("离开房间");

            form.send(p, (player, formWindowSimple, index) -> {
                if (index == 0) {
                    p.getLevel().addSound(p, Sound.NOTE_BASSATTACK, 1, 1);
                } else if (index >= 1 && index <= party.getMembers().size()) {
                    int memberIndex = index - 1;
                    Player member = party.getMembers().get(memberIndex);
                    if (memberIndex >= 0 && memberIndex < party.getMembers().size() - 1) {
                        SimpleForm playerForm = new SimpleForm(member.getName());
                        playerForm.addButton("踢出 " + member.getName());
                        playerForm.addButton("关闭");

                        playerForm.send(p, (player1, formWindowSimple1, index1) -> {
                            switch (index1) {
                                case 0:
                                    if (party.getLeader().equals(p)) {
                                        party.leaveRoom(member);
                                        member.sendMessage("你已被踢出队伍");
                                        ItemManager.sendLobbyItem(member);
                                        for (Player player2 : party.getMembers()) {
                                            player2.sendMessage(member.getName() + " 已被队长踢出队伍.");
                                        }
                                    } else {
                                        player1.sendMessage("只有房主可以踢出成员！");
                                    }
                                    break;
                                case 1:
                                    // 处理 "关闭" 按钮的逻辑
                                    break;
                            }
                        });
                    }
                } else if (index == party.getMembers().size() + 1) {
                    if (party.getLeader().equals(p)) {
                        for (Player player2 : party.getMembers()) {
                            if (player2 != null) {
                                player2.getLevel().addSound(p, Sound.BEACON_DEACTIVATE, 1, 1);
                                player2.sendMessage("房间已解散！");
                                Room.playerRoom.remove(player2.getName());
                                ItemManager.sendLobbyItem(player2);
                            }
                        }
                        Room.globalRoom.remove(party);
                    } else {
                        p.getLevel().addSound(p, Sound.BEACON_DEACTIVATE, 1, 1);
                        p.sendMessage("你离开了房间！");
                        party.leaveRoom(p);
                        ItemManager.sendLobbyItem(p);
                    }
                }
            });
        }
    }

    /*public static void sendRoomMenu(Player p) {
        if (!Room.playerRoom.containsKey(p.getName())) {
            p.sendMessage("! 你还未加入任何房间 !");
            p.getLevel().addSound(p, Sound.NOTE_BASSATTACK, 1, 1);
        } else {
            Room party = Room.playerRoom.get(p.getName());
            SimpleForm form = new SimpleForm(TextFormat.DARK_RED + party.getLeader().getName() + "的房间");
            form.addButton(TextFormat.DARK_RED + party.getLeader().getName() + " 房主");
            for (Player player : party.getMembers()) {
                if (!player.getName().equals(party.getLeader().getName())) {
                    form.addButton(TextFormat.GRAY + player.getName() + " 成员");
                }
            }
            form.addButton("离开房间");

            form.send(p, (player, formWindowSimple, index) -> {
                if (index == 0) {
                    p.getLevel().addSound(p, Sound.NOTE_BASSATTACK, 1, 1);
                } else if (index >= 1 && index <= party.getMembers().size()) {
                    var memberIndex = index - 1;
                    Player member = party.getMembers().get(index - 1);
                    if (memberIndex >= 0 && memberIndex < party.getMembers().size() - 1) {
                        var members = party.getMembers().get(memberIndex);
                        SimpleForm playerForm = new SimpleForm(member.getName());
                        playerForm.addButton("踢出 " + member.getName());
                        playerForm.addButton("关闭");

                        playerForm.send(p, (player1, formWindowSimple1, index1) -> {
                            switch (index1) {
                                case 0:
                                    party.leaveRoom(members);
                                    members.sendMessage("你已被踢出队伍");
                                    ItemManager.sendLobbyItem(members);
                                    for (Player player2 : party.getMembers()) {
                                        player2.sendMessage(members.getName() + " 已被队长踢出队伍.");
                                    }
                                    break;
                                case 1:
                                    // 处理 "关闭" 按钮的逻辑
                                    break;
                            }
                        });
                    }
                } else if (index == 1){
                    for (Player player2 : party.getMembers()) {
                        if (player2 != null) {
                            player2.getLevel().addSound(p, Sound.BEACON_DEACTIVATE, 1, 1);
                            player2.sendMessage("房间已解散！");
                            party.leaveRoom(player2);
                            Room.globalRoom.remove(playerRoom.get(player2.getName()));
                        }
                    }
                }
            });
        }
    } */

        public static void sendHowToPlayForm(Player p){
        SimpleForm form = new SimpleForm("游戏玩法");
        form.setContent(
                "Dead by Daylight是一款多人(4vs1) 的恐怖游戏。\n" +
                "游戏由其中一人扮演野蛮杀手，另外四人扮演逃生者。\n" + "\n逃生者们将试图从野蛮杀手手中奋力逃脱，从而让自己免去被残忍杀害的危险。\n" +
                        "\nDead by daylight 是款1对4的非对称竞技游戏。 \n" +
                        "每场游戏由一名玩家扮演杀手，四名玩家扮演幸存者，地图上共会有5台发电机，\n" +
                        "而幸存者需修好其中的五台，让大门通电以开启大门逃出地图。 而杀手则是在这段期间要追猎幸存者，\n" +
                        "阻止幸存者逃出地图。\n" +
                        "------------------------\n" +
                        "人类在一场游戏里面可以做的事情有\n" +
                        "修发电机\n" +
                        "救人下钩\n" +
                        "拆钩子（需有工具箱）\n" +
                        "躲柜子\n" +
                        "翻窗\n" +
                        "下板\n" + "治疗别人\n" +
                        "治疗自己（需有技能或是有医疗箱）\n" +
                        "拉开大门\n" +
                        "------------------------\n" +
                        "杀手在一场游戏里面可以做的事情有\n" +
                        "砍人\n" +
                        "砍倒人\n" +
                        "把倒地的幸存者抱起\n" +
                        "把幸存者挂上钩\n" +
                        "踹发电机（发电机进度开始倒退）\n-来自网络资料");
        form.addButton("退出");

        form.send(p, (((player, formWindowSimple, i) -> {
            switch (i){
                case 0 : {
                    break;
                }
            }
        })));
    }

}
