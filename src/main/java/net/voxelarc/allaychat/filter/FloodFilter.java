package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class FloodFilter implements ChatFilter {

    private final AllayChatPlugin plugin;

    private boolean enabled = true;
    private Pattern pattern = Pattern.compile("(.)\\1{10,}");

    private int maxMessageLength = 50;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("flood.enabled", true);
        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("flood.message"));
        int maxRepeating = plugin.getFilterConfig().getInt("flood.max-repeating", 10);
        maxMessageLength = plugin.getFilterConfig().getInt("flood.max-message-length", 50);
        try {
            pattern = Pattern.compile("(.)\\1{" + maxRepeating + ",}");
        } catch (Exception e) {
            plugin.getLogger().warning("Invalid regex pattern for flood filter.");
        }
    }

    @Override
    public Result checkMessage(Player player, String message) {
        if (!enabled) return ChatFilter.ALLOWED;
        if (player.hasPermission("allaychat.bypass.flood")) return ChatFilter.ALLOWED;

        if (message.length() > maxMessageLength || pattern.matcher(message).find()) {
            ChatUtils.sendMessage(player, blockedMessage);
            return ChatFilter.DISALLOWED;
        }

        return ChatFilter.ALLOWED;
    }

}
