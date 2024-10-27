package se233.project2.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedSprite extends ImageView {
    int count, columns, rows, offsetX, offsetY, width, height, curIndex, curColumnIndex = 0, curRowIndex = 0;
    Timeline timeline;
    public AnimatedSprite(Image image, int count, int columns, int rows, int offsetX, int offsetY, int width, int height) {
        this.setImage(image);
        this.count = count;
        this.columns = columns;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> tick()));
        timeline.setCycleCount(3);
    }

    public void tick() {
        curColumnIndex = curIndex % columns;
        curRowIndex = curIndex / columns;
        curIndex = (curIndex + 1) % (columns * rows);
        interpolate();
    }

    public void start() {
        timeline.play();
    }

    protected void interpolate() {
        final int x = curColumnIndex * width + offsetX;
        final int y = curRowIndex * height + offsetY;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }

    public void setTimeline(int delay, int cycleCount) {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(delay), e -> tick()));
        timeline.setCycleCount(cycleCount);
    }
}
