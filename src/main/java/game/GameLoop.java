package game;

import game.entities.Player;
import game.entities.events.Event;
import game.events.EventsConsumer;
import game.events.EventsProducer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class GameLoop implements ActionListener {

    static Player[] players;
    static public Queue<Event> events;
    static public GameState state;

    static long lastFrameTime;
    static long currentFrameTime;
    Timer timer;

    static public double deltaTime(){
        return (double)(currentFrameTime - lastFrameTime)/1_000_000_000.0;
    }
    static public Player getPlayer(int id){ for(Player player: players) {if(player.id == id) return player;} return null;}
    static public Player getEnemy(int id){ for(Player player: players) {if(player.id != id) return player;} return null;}
    static public boolean isPlayerOne(int id){ return players[0].id == id;}

    public GameLoop(int playerOneID, int playerTwoID) {
        state = new GameState();

        players = new Player[2];
        players[0] = new Player(playerOneID, GameConstants.INITIAL_PLAYER_1_X, GameConstants.INITIAL_PLAYER_Y);
        players[1] = new Player(playerTwoID, GameConstants.INITIAL_PLAYER_2_X, GameConstants.INITIAL_PLAYER_Y);

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

                if(state.countdown <= 0.1){
                    EventsProducer.handleStarted();
                }
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