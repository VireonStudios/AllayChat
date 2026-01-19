package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CapsFilter implements ChatFilter {

    private final AllayChatPlugin plugin;

    private int maxCaps = 3;
    private boolean enabled = true;
    private boolean bypassUsernames = false;
    private boolean lowerCase = false;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("caps.enabled", true);
        bypassUsernames = plugin.getFilterConfig().getBoolean("caps.bypass-usernames", false);
        lowerCase = plugin.getFilterConfig().getBoolean("caps.lower-case", false);
        maxCaps = plugin.getFilterConfig().getInt("caps.max-caps", 3);
        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("caps.message"));
    }

    @Override
    public Result checkMessage(Player player, String message) {
        if (!enabled) return ChatFilter.ALLOWED;
        if (player.hasPermission("allaychat.bypass.caps")) return ChatFilter.ALLOWED;

        if (bypassUsernames) {
            String[] split = message.split(" ");
            int totalPlayerNameCaps = 0;
            for (String msg : split) {
                if (plugin.getPlayerManager().getAllPlayers().stream().noneMatch(msg::equals)) continue;
                totalPlayerNameCaps += capsCount(msg);
            }

            if (capsCount(message) - totalPlayerNameCaps >= maxCaps && !lowerCase) {
                ChatUtils.sendMessage(player, blockedMessage);
                return ChatFilter.DISALLOWED;
            } else if (lowerCase) {
                return new Result(true, message.toLowerCase());
            }

            return ChatFilter.ALLOWED;
        }

        if (capsCount(message) >= maxCaps && !lowerCase) {
            ChatUtils.sendMessage(player, blockedMessage);
            return ChatFilter.DISALLOWED;
        } else if (lowerCase) {
            return new Result(true, message.toLowerCase());
        }

        return ChatFilter.ALLOWED;
    }

    public static int capsCount(String text) {
        int capsCount = 0;
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c))
                capsCount++;
        }

        return capsCount;
    }

}
