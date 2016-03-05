package net.md_5.bungee.api.chat;

import java.util.List;
import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatStringBuilder;

import static com.google.common.base.Preconditions.checkNotNull;

public class ScoreComponent extends BaseComponent {

    private final String name, objective;

    public ScoreComponent(String name, String objective) {
        this.name = checkNotNull(name);
        this.objective = checkNotNull(objective);
    }

    public String getName() {
        return name;
    }

    public String getObjective() {
        return objective;
    }

    @Override
    public ScoreComponent duplicate() {
        return new ScoreComponent(getName(), getObjective());
    }

    private String plainTextContent() {
        return getName() + ':' + getObjective();
    }

    @Override
    void toPlainText(StringBuilder builder) {
        builder.append(plainTextContent());
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyTextContent(ChatStringBuilder builder, ChatColor color, Set<ChatColor> decorations) {
        builder.format(color, decorations);
        builder.append(plainTextContent());
    }

    @Override
    protected void toStringTerminal(List<String> fields) {
        fields.add("name=\"" + getName() + '"');
        fields.add("objective=\"" + getObjective() + '"');
        super.toStringTerminal(fields);
    }
}
