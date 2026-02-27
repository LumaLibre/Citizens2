package net.citizensnpcs.trait.scoreboard;

import net.megavex.scoreboardlibrary.api.team.enums.NameTagVisibility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public interface AbstractTeam {

    void addEntry(String entry);

    boolean hasEntry(String entry);

    void removeEntry(String entry);

    ChatColor getColor();

    NameTags getNameTagVisibility();

    CollisionRule getCollisionRule();

    void setColor(ChatColor color);

    void setNameTagVisibility(NameTags visibility);

    void setCollisionRule(CollisionRule rule);

    Object getDelegate();

    String getName();

    void sendToPlayer(Player player, SendMode mode);

    int getSize();


    interface TeamOption<P> {
        P getFoliaValue();
        Team.OptionStatus getBukkitValue();

        static <E extends TeamOption<P>, P> E fromPacket(E[] values, P packetValue) {
            for (E e : values) {
                if (e.getFoliaValue().equals(packetValue)) return e;
            }
            return values[0];
        }

        static <E extends TeamOption<?>> E fromBukkit(E[] values, Team.OptionStatus bukkitValue) {
            for (E e : values) {
                if (e.getBukkitValue() == bukkitValue) return e;
            }
            return values[0];
        }
    }

    enum NameTags implements TeamOption<NameTagVisibility> {
        ALWAYS_SHOW(NameTagVisibility.ALWAYS, Team.OptionStatus.ALWAYS),
        NEVER_SHOW(NameTagVisibility.NEVER, Team.OptionStatus.NEVER),
        HIDE_FOR_OTHER_TEAMS(NameTagVisibility.HIDE_FOR_OTHER_TEAMS, Team.OptionStatus.FOR_OTHER_TEAMS),
        HIDE_FOR_OWN_TEAM(NameTagVisibility.HIDE_FOR_OWN_TEAM, Team.OptionStatus.FOR_OWN_TEAM);

        private final NameTagVisibility packetBasedValue;
        private final Team.OptionStatus bukkitValue;

        NameTags(NameTagVisibility packetBasedValue, Team.OptionStatus bukkitValue) {
            this.packetBasedValue = packetBasedValue;
            this.bukkitValue = bukkitValue;
        }

        @Override
        public NameTagVisibility getFoliaValue() {
            return packetBasedValue;
        }

        @Override
        public Team.OptionStatus getBukkitValue() {
            return bukkitValue;
        }

        public static NameTags fromPacket(NameTagVisibility value) {
            return TeamOption.fromPacket(values(), value);
        }

        public static NameTags fromBukkit(Team.OptionStatus value) {
            return TeamOption.fromBukkit(values(), value);
        }
    }


    enum CollisionRule implements TeamOption<net.megavex.scoreboardlibrary.api.team.enums.CollisionRule> {
        ALWAYS(net.megavex.scoreboardlibrary.api.team.enums.CollisionRule.ALWAYS, Team.OptionStatus.ALWAYS),
        NEVER(net.megavex.scoreboardlibrary.api.team.enums.CollisionRule.NEVER, Team.OptionStatus.NEVER),
        PUSH_OTHER_TEAMS(net.megavex.scoreboardlibrary.api.team.enums.CollisionRule.PUSH_OTHER_TEAMS, Team.OptionStatus.FOR_OTHER_TEAMS),
        PUSH_OWN_TEAM(net.megavex.scoreboardlibrary.api.team.enums.CollisionRule.PUSH_OWN_TEAM, Team.OptionStatus.FOR_OWN_TEAM);

        private final net.megavex.scoreboardlibrary.api.team.enums.CollisionRule packetBasedValue;
        private final Team.OptionStatus bukkitValue;

        CollisionRule(net.megavex.scoreboardlibrary.api.team.enums.CollisionRule packetBasedValue, Team.OptionStatus bukkitValue) {
            this.packetBasedValue = packetBasedValue;
            this.bukkitValue = bukkitValue;
        }

        @Override
        public net.megavex.scoreboardlibrary.api.team.enums.CollisionRule getFoliaValue() {
            return packetBasedValue;
        }

        @Override
        public Team.OptionStatus getBukkitValue() {
            return bukkitValue;
        }

        public static CollisionRule fromPacket(net.megavex.scoreboardlibrary.api.team.enums.CollisionRule value) {
            return TeamOption.fromPacket(values(), value);
        }

        public static CollisionRule fromBukkit(Team.OptionStatus value) {
            return TeamOption.fromBukkit(values(), value);
        }
    }

    enum SendMode {
        ADD_OR_MODIFY(0),
        REMOVE(1);

        private final int value;

        SendMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
