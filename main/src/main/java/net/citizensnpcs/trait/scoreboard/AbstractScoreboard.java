package net.citizensnpcs.trait.scoreboard;

public interface AbstractScoreboard {

    AbstractTeam getTeam(String name);

    void removeTeam(String name);

    AbstractTeam createTeam(String name);
}
