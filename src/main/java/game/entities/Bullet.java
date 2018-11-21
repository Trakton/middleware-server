package game.entities;

import game.GameConstants;

public class Bullet extends Sprite {

  public int direction;
  long startTime;
  int startX;

  public Bullet(int id, int x, int y, int direction) {
    super(id, x, y, GameConstants.BULLET_SPRITE);

    this.direction = direction;
    startX = x;
    startTime = System.nanoTime();
  }

  public void update() {
    x =
        (int)
            (startX
                + ((System.nanoTime() - startTime) / 1e9) * GameConstants.BULLET_SPEED * direction);
  }
}
