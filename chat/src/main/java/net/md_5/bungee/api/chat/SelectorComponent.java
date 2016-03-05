package net.md_5.bungee.api.chat;

import java.util.List;
import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatStringBuilder;

import static com.google.common.base.Preconditions.checkNotNull;

public class SelectorComponent extends BaseComponent {

    private final String selector;

    public SelectorComponent(String selector) {
        this.selector = checkNotNull(selector);
    }

    public String getSelector() {
        return selector;
    }

    @Override
    public SelectorComponent duplicate() {
        return new SelectorComponent(getSelector());
    }

    @Override
    void toPlainText(StringBuilder builder) {
        builder.append(selector);
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyTextContent(ChatStringBuilder builder, ChatColor color, Set<ChatColor> decorations) {
        builder.format(color, decorations);
        builder.append(getSelector());
    }

    @Override
    protected void toStringTerminal(List<String> fields) {
        fields.add("selector=\"" + getSelector() + '"');
        super.toStringTerminal(fields);
    }
}
