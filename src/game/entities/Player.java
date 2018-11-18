package game.entities;

import game.GameConstants;
import game.GameLoop;
import game.events.EventsProducer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {

    public int lives;
    public int bulletCount;
    public List<Bullet> bullets;


    public Player(int id, int x, int y){
        super(id, x, y, GameConstants.PLAYER_SPRITE);

        lives = GameConstants.INITIAL_LIVES;
        bullets = new ArrayList<Bullet>();
        bulletCount = 0;
    }

    public void update(){
        List<Bullet> bullets = new ArrayList<Bullet>();
        Player enemy = GameLoop.getEnemy(id);

        for(Bullet bullet : this.bullets){
            bullet.update();

            if(!bullet.outOfBounds() && !bullet.intersects(enemy)){
                bullets.add(bullet);
            } else if (bullet.intersects(enemy)){
                EventsProducer.handleUser(enemy.id, enemy.lives - 1);

                if(enemy.lives == 1){
                    EventsProducer.handleOver(id);
                }
            }
        }

        this.bullets = bullets;
    }

    public void fire(){
        int direction = GameLoop.isPlayerOne(id)? 1 : -1;
        int x = this.x + this.sprite.getWidth(null) * direction;
        int y = this.y + this.sprite.getHeight(null) / 2;
        bullets.add(new Bullet(bulletCount++, x, y, direction));
    }

    public void moveUp(){
        y -= GameConstants.PLAYER_SPEED;
    }

    public void moveDown(){
        y += GameConstants.PLAYER_SPEED;
    }
}
