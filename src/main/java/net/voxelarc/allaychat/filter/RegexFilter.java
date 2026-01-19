package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegexFilter implements ChatFilter {

    private final AllayChatPlugin plugin;
    private final Set<Pattern> patterns = new HashSet<>();

    private boolean enabled = false;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("regex.enabled");

        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("regex.message"));

        plugin.getFilterConfig().getStringList("regex.regex-list").forEach(pattern -> {
            try {
                patterns.add(Pattern.compile(pattern));
            } catch (Exception e) {
                plugin.getLogger().warning("Invalid regex pattern: " + pattern);
            }
        });
    }

    @Override
    public Result checkMessage(Player player, String message) {
        if (!enabled) return ChatFilter.ALLOWED;
        if (player.hasPermission("allaychat.bypass.regex")) return ChatFilter.ALLOWED;

        for (Pattern pattern : patterns) {
            if (pattern.matcher(message).find()) {
                ChatUtils.sendMessage(player, blockedMessage);
                return ChatFilter.DISALLOWED;
            }
        }

        return ChatFilter.ALLOWED;
    }

}
