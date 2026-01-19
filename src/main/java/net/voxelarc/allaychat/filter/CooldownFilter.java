package net.voxelarc.allaychat.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CooldownFilter implements ChatFilter {

    private final AllayChatPlugin plugin;

    private Cache<UUID, Byte> cooldownCache;

    private boolean enabled = true;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("cooldown.enabled", true);
        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("cooldown.message"));

        double seconds = plugin.getFilterConfig().getDouble("cooldown.seconds", 5);
        long ms = (long) (seconds * 1000);
        cooldownCache = CacheBuilder.newBuilder().expireAfterWrite(ms, TimeUnit.MILLISECONDS).build();
    }

    @Override
    public Result checkMessage(Player player, String message) {
        if (!enabled) return ChatFilter.ALLOWED;
        if (player.hasPermission("allaychat.bypass.cooldown")) return ChatFilter.ALLOWED;

        if (cooldownCache.getIfPresent(player.getUniqueId()) != null) {
            ChatUtils.sendMessage(player, blockedMessage);
            return ChatFilter.DISALLOWED;
        } else {
            cooldownCache.put(player.getUniqueId(), (byte) 0);
        }
        
        return ChatFilter.ALLOWED;
    }

}
