package game;

import game.entities.Player;
import game.entities.events.Event;
import game.events.EventsConsumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class GameLoop implements ActionListener {

    static public Player[] players;
    static public Queue<Event> events;
    static public GameState state;

    static long lastFrameTime;
    static long currentFrameTime;
    Timer timer;

    static public double deltaTime(){
        return (double)(currentFrameTime - lastFrameTime)/1_000_000_000.0;
    }

    public GameLoop() {
        state = new GameState();

        players = new Player[2];
        players[0] = new Player(GameConstants.PLAYER_ONE, GameConstants.INITIAL_PLAYER_1_X, GameConstants.INITIAL_PLAYER_Y);
        players[1] = new Player(GameConstants.PLAYER_TWO, GameConstants.INITIAL_PLAYER_2_X, GameConstants.INITIAL_PLAYER_Y);

        events = new LinkedList<Event>();

        timer = new Timer(0, this);
        timer.start();

        lastFrameTime = currentFrameTime = System.nanoTime();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EventsConsumer.consume();

        switch(state.state){
            case TO_START:
                state.updateCountdown();
            case STARTED:
                for(Player player : players) player.update();
                break;
            default:
                break;
        }

        lastFrameTime = currentFrameTime;
        currentFrameTime = System.nanoTime();
    }
}