package se233.project2;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.project2.controller.DrawingLoop;
import se233.project2.controller.GameLoop;
import se233.project2.model.*;
import se233.project2.view.GameStage;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameLoopTest {
    PlayerShip playerShip;
    GameStage gameStage;
    GameLoop gameLoop;
    Method updatePlayerShip, update;


    @BeforeAll
    public static void initJfxRuntime() { javafx.application.Platform.startup(() -> {}); }

    @BeforeEach
    public void setup() throws NoSuchMethodException {
        playerShip = new PlayerShip(0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("playership_sprite.png")), 3, 3, 1, 0, 0, 124, 240), 3, 62, 120);
        gameStage = new GameStage();
        gameStage.getChildren().add(playerShip);
        gameStage.setPlayerShip(playerShip);
        gameLoop = new GameLoop(gameStage);

        updatePlayerShip = gameLoop.getClass().getDeclaredMethod("updatePlayerShip");
        update = gameLoop.getClass().getDeclaredMethod("update");
        updatePlayerShip.setAccessible(true);
        update.setAccessible(true);
    }

    public void clockTickHelper() throws IllegalAccessException, InvocationTargetException {
        updatePlayerShip.invoke(gameLoop);
        update.invoke(gameLoop);
    }



    @Test
    public void givenPlayerShipCollidesWithAsteroid_thenPlayerShipDies() throws IllegalAccessException, InvocationTargetException {
        Asteroid asteroid =  new Asteroid(0, 0, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60), 1, 1);
        gameStage.getEnemyList().add(asteroid);
        clockTickHelper();
        Assertions.assertTrue(playerShip.isDead(), "PlayerShip dies");
    }

    @Test
    public void givenPlayerShotBoss_thenBossHpDecreasesBy1() throws IllegalAccessException, InvocationTargetException {
        Boss boss = new Boss(80, 0, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("enemyBlack3.png")), 1, 1, 1, 0, 0, 103, 84), 50, 103, 84);
        playerShip.getAnimatedSprite().setRotate(90);
        playerShip.shoot();
        gameStage.getEnemyList().add(boss);
        gameStage.getChildren().add(boss);
        clockTickHelper();
        Assertions.assertEquals(49, boss.getHp(), "Boss Hp decreases by 1");
    }

//    @Test
//    public void givenPlayerBombsBoss_thenBossHpDecreasesBy5() throws IllegalAccessException, InvocationTargetException {
//        Boss boss = new Boss(80, 0, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("enemyBlack3.png")), 1, 1, 1, 0, 0, 103, 84), 50, 103, 84);
//        playerShip.getAnimatedSprite().setRotate(90);
//        playerShip.shootBomb();
//        gameStage.getEnemyList().add(boss);
//        gameStage.getChildren().add(boss);
//        clockTickHelper();
//        Assertions.assertEquals(45, boss.getHp(), "Boss Hp decreases by 5");
//    }


    @Test
    public void givenPlayerShipShotAsteriod_thenScoreIncreasesBy1() throws IllegalAccessException, InvocationTargetException {
        playerShip.getAnimatedSprite().setRotate(90);
        Asteroid asteroid =  new Asteroid(80, 0, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60), 1, 1);
        playerShip.shoot();
        gameStage.getEnemyList().add(asteroid);
        gameStage.getChildren().add(asteroid);
        clockTickHelper();
        Assertions.assertEquals(1, playerShip.getScore(), "Score increases by 1");
    }

    @Test
    public void givenBulletCollidesWithAsteroid_thenBothShouldDie() throws IllegalAccessException, InvocationTargetException {
        playerShip.getAnimatedSprite().setRotate(90);
        playerShip.setY(10);
        Asteroid asteroid =  new Asteroid(80, 10, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60), 1, 1);
        playerShip.shoot();
        gameStage.getEnemyList().add(asteroid);
        gameStage.getChildren().add(asteroid);
        Bullet bullet = playerShip.getBulletList().get(0);
        System.out.println("this bullet is " + bullet);
        clockTickHelper();
        System.out.println(bullet.isDead() + " " + asteroid.isDead());
        Assertions.assertTrue(bullet.isDead(), "Bullet dies");
        Assertions.assertTrue(asteroid.isDead(), "Asteroid dies");
    }
}
