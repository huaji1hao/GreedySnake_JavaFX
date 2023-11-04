package example.model;

import example.utils.ImageUtil;

import java.awt.Graphics;
import java.util.Random;

public class Food extends SnakeObject {

    public Food() {
        this.alive = true; // Use 'alive' instead of 'l'

        // Assuming ImageUtil.images is a Map<String, Image>
        this.image = ImageUtil.images.get(String.valueOf(new Random().nextInt(10)));

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        this.x = (int) (Math.random() * (870 - width + 10));
        this.y = (int) (Math.random() * (560 - height - 40));
    }

    public void eaten(Snake snake) {
        if (snake.getRectangle().intersects(this.getRectangle()) && alive && snake.isAlive()) {
            this.alive = false; // Use 'alive' instead of 'l'
            snake.changeLength(snake.getLength() + 1); // Use 'setLength' instead of 'changeLength'
            snake.score += 521;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (alive) { // Only draw if the food is alive
            g.drawImage(image, x, y, null);
        }
    }
}
