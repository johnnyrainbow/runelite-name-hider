package net.runelite.client.plugins.chatFilterUsername;

import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ClanManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.chatfilter.ChatFilterConfig;
import net.runelite.client.ui.overlay.OverlayManager;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Account",
        description = "Sync RuneLite config settings with your Google account",
        tags = {"external", "google", "integration"},
        enabledByDefault = true
)
public class ChatFilterPlugin extends Plugin {
    private static final String REPLACEMENT_NAME = "Mod Weath";
    public static String getReplacementName() {
        return REPLACEMENT_NAME;
    }
    public static String modTag = "<img=1> ";
    @Inject
    private Client client;

    @Inject
    private ChatFilterConfig config;

    @Inject
    private ClanManager clanManager;

    @Inject
    private ChatMessageManager chatMessageManager;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private FilterOverlays overlay;
    @Provides
    ChatFilterConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ChatFilterConfig.class);
    }
    @Inject
    private KeyManager keyManager;


    @Override
    protected void startUp() throws Exception {
        client.refreshChat();
        overlayManager.add(overlay);
        keyManager.registerKeyListener(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        client.refreshChat();
    }

    @Subscribe
    public void onOverheadTextChanged(OverheadTextChanged event) {
        String message = event.getActor().getOverheadText();
        event.getActor().setOverheadText(FilterUtil.filterOutName(client, message));
    }


    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        MessageNode node = chatMessage.getMessageNode();
        node.setRuneLiteFormatMessage(FilterUtil.filterOutName(client, chatMessage.getMessage()));
        String setName = chatMessage.getName();
        if(!setName.contains(modTag) && FilterUtil.isMatchingName(client, setName)) {
            setName = modTag + setName;
        }
        node.setName(FilterUtil.filterOutName(client, setName ));
        chatMessageManager.update(chatMessage.getMessageNode());
    }
}
