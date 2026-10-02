package net.voxelarc.allaychat.api.chat;

import net.kyori.adventure.text.event.ClickEvent;

import java.util.List;

public record ChatFormat(String group, String format, Hover hover, Click click) {

    public record Hover(List<String> message) {

    }

    public record Click(String command) {

    }

}
