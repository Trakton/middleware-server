package game;

import game.events.EventsProducer;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame  {

    public static void main(String[] args) {
        GameLoop game = new GameLoop(10, 20);
        EventsProducer.handleToStart();
    }
}
