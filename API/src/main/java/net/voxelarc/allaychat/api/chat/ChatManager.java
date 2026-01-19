package net.voxelarc.allaychat.api.chat;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public interface ChatManager {

    void onEnable();

    ChatRenderer.ViewerUnaware getChatRenderer();

    /**
     * Processes a chat message.
     *
     * @param message the message to process.
     * @return the processed message.
     */
    Component formatMessage(Player player, Component message);

    /**
     * Handles the chat event.
     *
     * @return true if the event should be cancelled, false otherwise.
     */
    ChatFilter.Result handleMessage(Player player, String message);

    void handleChatEvent(AsyncChatEvent event);

    /**
     * Handles a private message.
     *
     * @param from the sender of the message.
     * @param to the recipient of the message.
     * @param message the message content.
     * @return true if the message was handled successfully, false otherwise.
     */
    boolean handlePrivateMessage(Player from, String to, String message);

    /**
     * Handles a staff chat message.
     *
     * @param from the sender of the message.
     * @param message the message content.
     */
    void handleStaffChatMessage(Player from, String message);

    String getLastMessagedPlayer(String playerName);

    Inventory getInventory(UUID id);

    void setInventory(UUID id, String playerName, Inventory inventory, InventoryType type);

    boolean isChatMuted();

    void setChatMuted(boolean muted);

    Component handleMentions(Player player, String messageContent, Component messageComponent);

    Component formatConnectionMessage(Player player, String type);

    enum InventoryType {
        SHULKER,
        INVENTORY,
        ENDER_CHEST,
    }

}
