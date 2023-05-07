package me.nkdevz.simplechangeserver;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public final class SimpleChangeServer extends JavaPlugin implements Listener {

    public static SimpleChangeServer instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getPluginManager().registerEvents(this, this);
        System.out.println("Initialized.");
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down.");
    }

    @EventHandler
    public void onJoinChangeServer(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        playerServer(p, "lobby01");
    }

    public static void playerServer(Player p, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            p.sendPluginMessage(instance, "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        }
        catch (Exception e) {
            p.sendMessage("Error connecting to server: " + server);
        }
    }
}
