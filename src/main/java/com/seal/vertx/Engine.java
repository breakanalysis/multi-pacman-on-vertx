package com.seal.vertx;

import com.seal.vertx.domain.Action;
import com.seal.vertx.domain.GameState;
import com.seal.vertx.domain.Player;
import com.seal.vertx.domain.PlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jacobsznajdman on 26/10/17.
 */
public class Engine {
    private GameState current;

    public Engine() {
    }

    public void start() {
        this.current = GameState.initial();
    }

    public GameState update(Map<String, Action> actions) {
        GameState transforming = current;
        for (String userId : actions.keySet()) {
            transforming = updateState(transforming, userId, actions.get(userId));
        }
        current = updateMechanics(transforming);
        return current;
    }

    private GameState updateMechanics(GameState transforming) {
        return transforming;
    }

    private GameState updateState(GameState transforming, String userId, Action action) {
        List<PlayerState> newPlayers;
        switch (action) {
            case UP:
                return transforming;
            case DOWN:
                return transforming;
            case LEFT:
                return transforming;
            case RIGHT:
                return transforming;
            case JOIN:
                boolean playerExists = current.playerStates.stream().anyMatch(ps -> ps.player.id.equals(userId));
                if (playerExists) {
                    return transforming;
                }
                newPlayers = new ArrayList<>();
                Player newPlayer = Player.randomPlayer(transforming, userId);
                PlayerState newState = PlayerState.randomState(transforming, newPlayer);
                newPlayers.add(newState);
                newPlayers.addAll(transforming.playerStates);
                return new GameState(newPlayers, transforming.maze);
            case QUIT:
                newPlayers = transforming.playerStates.stream().filter(ps -> !ps.player.id.equals(userId))
                        .collect(Collectors.toList());
                return new GameState(newPlayers, transforming.maze);
            default:
                throw new RuntimeException("Unimplemented action");
        }
    }

}
