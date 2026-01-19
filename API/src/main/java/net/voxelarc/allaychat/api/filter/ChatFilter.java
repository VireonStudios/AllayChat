package net.voxelarc.allaychat.api.filter;

import org.bukkit.entity.Player;

public interface ChatFilter {

    Result ALLOWED = new Result(true, "");
    Result DISALLOWED = new Result(false, "");

    void onEnable();

    /**
     * @param message the message to check
     * @return true if event should be cancelled, false otherwise
     */
    Result checkMessage(Player player, String message);

    record Result(boolean allow, String message) {
    }

}
