package net.md_5.bungee.api;

import java.util.EnumSet;
import java.util.Set;

public class ChatStringBuilder implements Appendable {

    private final StringBuilder builder;
    private ChatColor oldColor, newColor;
    private final Set<ChatColor> oldDecorations, newDecorations;
    private boolean formatChanged = true;

    public ChatStringBuilder(String initial, ChatColor color, Set<ChatColor> decorations) {
        builder = initial != null ? new StringBuilder(initial) : new StringBuilder();

        oldColor = newColor = color;

        oldDecorations = decorations != null ? EnumSet.copyOf(decorations) : EnumSet.noneOf(ChatColor.class);
        newDecorations = EnumSet.noneOf(ChatColor.class);
    }

    public ChatStringBuilder() {
        this(null, ChatColor.RESET, null);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    private void refreshComplete() {
        builder.append(oldColor = newColor);

        oldDecorations.clear();
        for(ChatColor deco : newDecorations) {
            oldDecorations.add(deco);
            builder.append(deco);
        }
    }

    private void refreshIfChanged() {
        if(!formatChanged) return;

        // If color changed, a complete refresh is required
        if(oldColor != newColor) {
            refreshComplete();
            return;
        }

        // If any decorations were removed, a complete refresh is required
        for(ChatColor deco : oldDecorations) {
            if(!newDecorations.contains(deco)) {
                refreshComplete();
                return;
            }
        }

        // If the only change is added decorations, they can just be appended
        for(ChatColor deco : newDecorations) {
            if(oldDecorations.add(deco)) {
                builder.append(deco);
            }
        }
    }

    @Override
    public ChatStringBuilder append(CharSequence text) {
        if(text.length() != 0) {
            refreshIfChanged();
            builder.append(text);
        }
        return this;
    }

    @Override
    public ChatStringBuilder append(CharSequence text, int start, int end) {
        if(start < end) {
            refreshIfChanged();
            builder.append(text, start, end);
        }
        return this;
    }

    @Override
    public Appendable append(char c) {
        refreshIfChanged();
        builder.append(c);
        return this;
    }

    public void append(Object thing) {
        append(String.valueOf(thing));
    }

    public void color(ChatColor color) {
        if(newColor != color) {
            formatChanged = true;
            newColor = color;
        }
    }

    public void decoration(ChatColor decoration, boolean on) {
        if(on) {
            if(newDecorations.add(decoration)) {
                formatChanged = true;
            }
        } else {
            if(newDecorations.remove(decoration)) {
                formatChanged = true;
            }
        }
    }

    public void decorations(Set<ChatColor> decorations) {
        for(ChatColor deco : ChatColor.DECORATIONS) {
            decoration(deco, decorations.contains(deco));
        }
    }

    public void format(ChatColor color, Set<ChatColor> decorations) {
        color(color);
        decorations(decorations);
    }
}
