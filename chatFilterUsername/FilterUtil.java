package net.runelite.client.plugins.chatFilterUsername;

import net.runelite.api.Client;

public class FilterUtil {
    public static String filterOutName(Client client, String input) {

        String myName = client.getLocalPlayer().getName().toLowerCase();
        String incomingMessageOrName = filterStupidJagexSpacesShit(input.toLowerCase());

        if (incomingMessageOrName.contains(myName)) {
            return incomingMessageOrName.replace(myName, ChatFilterPlugin.getReplacementName());
        }
        return input;
    }
    public static boolean isMatchingName(Client client, String input) {

        String myName = client.getLocalPlayer().getName().toLowerCase();
        String incomingMessageOrName = filterStupidJagexSpacesShit(input.toLowerCase());

        if (incomingMessageOrName.contains(myName)) {
            return true;
        }
        return false;
    }

    private static String filterStupidJagexSpacesShit(String input) {
        char cc = 160;
        return input.replace(cc, ' ');
    }
}
