package com.model;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class PendingGame {
    private List<TSUser> players;
    private TestCultura testCultura;

    public PendingGame(List<TSUser> players, TestCultura testCultura) {
        this.players = players;
        this.testCultura = testCultura;
    }

    public List<TSUser> getPlayers() {
        return players;
    }

    public void setPlayers(List<TSUser> players) {
        this.players = players;
    }

    public TestCultura getTestCultura() {
        return testCultura;
    }

    public void setTestCultura(TestCultura testCultura) {
        this.testCultura = testCultura;
    }
}
