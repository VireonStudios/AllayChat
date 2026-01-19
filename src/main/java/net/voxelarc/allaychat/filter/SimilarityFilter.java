package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.*;

@RequiredArgsConstructor
public class SimilarityFilter implements ChatFilter {

    private final AllayChatPlugin plugin;

    private final Map<UUID, List<Message>> lastMessages = new HashMap<>();

    private int distance = 5;
    private int messagesToCheck = 5;
    private int maxSeconds = 60;

    private boolean enabled = true;

    private Component blockMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("similarity.enabled", true);
        maxSeconds = plugin.getFilterConfig().getInt("similarity.max-time-between", 60);
        distance = plugin.getFilterConfig().getInt("similarity.distance", 3);
        messagesToCheck = plugin.getFilterConfig().getInt("similarity.messages-to-check", 5);
        blockMessage = ChatUtils.format(plugin.getFilterConfig().getString("similarity.message"));
    }

    @Override
    public Result checkMessage(Player player, String message) {
        if (!enabled) return ChatFilter.ALLOWED;
        if (player.hasPermission("allaychat.bypass.similarity")) return ChatFilter.ALLOWED;

        List<Message> messages = lastMessages.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (messages.stream().anyMatch(msg -> isSimilar(msg, message))) {
            ChatUtils.sendMessage(player, blockMessage);
            return ChatFilter.ALLOWED;
        }

        messages.add(new Message(message, System.currentTimeMillis()));
        lastMessages.put(player.getUniqueId(), messages);

        if (messages.size() > messagesToCheck) {
            messages.removeFirst();
        }

        return ChatFilter.ALLOWED;
    }

    private boolean isSimilar(Message message, String text) {
        if (message.timestamp < System.currentTimeMillis() - (1000L * maxSeconds)) {
            return false;
        }

        return getLevenshteinDistance(message.message, text) <= distance;
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            final CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1];
        int d[] = new int[n + 1];
        int _d[];

        int i;
        int j;

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        return p[n];
    }


    private record Message(String message, long timestamp) {

    }

}
