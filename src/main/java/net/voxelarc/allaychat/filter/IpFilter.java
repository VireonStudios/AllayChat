package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class IpFilter implements ChatFilter {

    private final Pattern pattern = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");

    private final AllayChatPlugin plugin;

    private boolean enabled = true;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("ip.enabled", true);
        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("ip.message"));
    }

    @Override
    public Result checkMessage(Player player, String message) {
        if (!enabled) return ChatFilter.ALLOWED;
        if (player.hasPermission("allaychat.bypass.ip")) return ChatFilter.ALLOWED;

        if (pattern.matcher(message).find()) {
            ChatUtils.sendMessage(player, blockedMessage);
            return ChatFilter.DISALLOWED;
        }

        return ChatFilter.ALLOWED;
    }
}
