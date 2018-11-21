package game.entities;

import game.GameConstants;
import java.awt.*;
import javax.swing.*;

public class Sprite {

  public int x;
  public int y;
  public int id;
  public Image sprite;

  public Sprite(int id, int x, int y, String sprite) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.sprite = (new ImageIcon(sprite)).getImage();
  }

  public boolean outOfBounds() {
    return x < 0 - sprite.getWidth(null)
        || x > GameConstants.BOARD_WIDTH
        || y < 0 - sprite.getHeight(null)
        || y > GameConstants.BOARD_HEIGHT;
  }

  public Rectangle getBounds() {
    return new Rectangle(x, y, sprite.getWidth(null), sprite.getHeight(null));
  }

  public boolean intersects(Sprite sprite) {
    return this.getBounds().intersects(sprite.getBounds());
  }
}
