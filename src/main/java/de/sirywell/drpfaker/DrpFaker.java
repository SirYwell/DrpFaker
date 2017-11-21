package de.sirywell.drpfaker;

import com.github.psnrigner.*;
import com.google.gson.JsonObject;

import javax.swing.*;

public class DrpFaker {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Discord Rich Presence Faker");
        frame.setContentPane(new DrpGui(frame).getMainPanel());
        frame.setVisible(true);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void setPresence(String clientId, DiscordRichPresence presence) {
        run(clientId, presence);
    }

    private static void run(String applicationId, DiscordRichPresence discordRichPresence) {
        DiscordRpc discordRpc = new DiscordRpc();
        DiscordEventHandler discordEventHandler = new DiscordEventHandler() {
            public void ready() {
                System.err.println("CLASS READY");
            }

            public void disconnected(ErrorCode errorCode, String message) {
                System.err.println("CLASS DISCONNECTED : " + errorCode + " " + message);
            }

            public void errored(ErrorCode errorCode, String message) {
                System.err.println("CLASS ERRORED : " + errorCode + " " + message);
            }

            public void joinGame(String joinSecret) {
                System.err.println("CLASS JOIN GAME : " + joinSecret);
            }

            public void spectateGame(String spectateSecret) {
                System.err.println("CLASS SPECTATE GAME : " + spectateSecret);
            }

            public void joinRequest(DiscordJoinRequest joinRequest) {
                System.err.println("CLASS JOIN REQUEST : " + joinRequest);
            }
        };

        try {
            discordRpc.init(applicationId, discordEventHandler, true, (String)null);
            discordRpc.runCallbacks();
            discordRpc.updatePresence(discordRichPresence);
            Thread.sleep(5000L);
            discordRpc.runCallbacks();
        } catch (InterruptedException var12) {

        } finally {
            discordRpc.shutdown();
        }

    }

    public static JsonObject toJsonObject(String clientId, DiscordRichPresence presence) {
        JsonObject object = new JsonObject();
        object.addProperty("client_id", clientId);
        if(presence.getState() != null && !presence.getState().isEmpty()) {
            object.addProperty("state", presence.getState());
        }
        if(!presence.getDetails().isEmpty()) {
            object.addProperty("details", presence.getDetails());
        }
        if(!presence.getLargeImageKey().isEmpty()) {
            object.addProperty("large_image_key", presence.getLargeImageKey());
        }
        if(!presence.getLargeImageText().isEmpty()) {
            object.addProperty("large_image_text", presence.getLargeImageText());
        }
        if(!presence.getSmallImageKey().isEmpty()) {
            object.addProperty("small_image_key", presence.getSmallImageKey());
        }
        if(!presence.getSmallImageText().isEmpty()) {
            object.addProperty("small_image_text", presence.getSmallImageText());
        }
        if(presence.getStartTimestamp() != 0) {
            object.addProperty("start_timestamp", presence.getStartTimestamp());
        }
        if(presence.getEndTimestamp() != 0) {
            object.addProperty("end_timestamp", presence.getEndTimestamp());
        }
        return object;
    }
}
