package game;

import com.caio.middleware.MiddlewareException;
import game.entities.Player;
import game.entities.events.Event;
import game.events.EventsConsumer;
import game.events.EventsProducer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;

public class GameLoop implements ActionListener {

  static Player[] players;
  public static Queue<Event> events;
  public static GameState state;

  static long lastFrameTime;
  static long currentFrameTime;
  Timer timer;

  public static double deltaTime() {
    return (double) (currentFrameTime - lastFrameTime) / 1_000_000_000.0;
  }

  public static Player getPlayer(int id) {
    for (Player player : players) {
      if (player.id == id) return player;
    }
    return null;
  }

  public static Player getEnemy(int id) {
    for (Player player : players) {
      if (player.id != id) return player;
    }
    return null;
  }

  public static boolean isPlayerOne(int id) {
    return players[0].id == id;
  }

  public GameLoop(int playerOneID, int playerTwoID) {
    state = new GameState();

    players = new Player[2];
    players[0] =
        new Player(playerOneID, GameConstants.INITIAL_PLAYER_1_X, GameConstants.INITIAL_PLAYER_Y);
    players[1] =
        new Player(playerTwoID, GameConstants.INITIAL_PLAYER_2_X, GameConstants.INITIAL_PLAYER_Y);

    events = new LinkedList<Event>();

    timer = new Timer(0, this);
    timer.start();

    lastFrameTime = currentFrameTime = System.nanoTime();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    EventsConsumer.consume();

    switch (state.state) {
      case TO_START:
        state.updateCountdown();

        if (state.countdown <= 0.1) {
          try {
            EventsProducer.handleStarted();
          } catch (MiddlewareException e1) {
            e1.printStackTrace();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      case STARTED:
        for (Player player : players) {
          try {
            player.update();
          } catch (MiddlewareException e1) {
            e1.printStackTrace();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
        break;
      default:
        break;
    }

    lastFrameTime = currentFrameTime;
    currentFrameTime = System.nanoTime();
  }
}
