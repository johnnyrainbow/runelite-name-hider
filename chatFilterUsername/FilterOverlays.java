package net.runelite.client.plugins.chatFilterUsername;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.KeyEvent;

public class FilterOverlays extends Overlay implements KeyListener {
    private Client client;

    @Inject
    public FilterOverlays(Client client) {
        this.client = client;
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        hideNameFromInterfaces();
        return null;
    }


    private void hideNameFromInterfaces() {
        Widget chatboxName = client.getWidget(WidgetInfo.CHATBOX_INPUT);
        System.out.println(chatboxName.getText());
        String setName = FilterUtil.filterOutName(client, chatboxName.getText());

        if(!setName.contains(ChatFilterPlugin.modTag)) {
            setName = ChatFilterPlugin.modTag + setName;
        }
        chatboxName.setText(setName);
        Widget dialogue = client.getWidget(WidgetInfo.DIALOG_PLAYER);
        if (dialogue != null) {
            for (Widget w : dialogue.getStaticChildren()) {

                w.setText(FilterUtil.filterOutName(client, w.getText()));
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
