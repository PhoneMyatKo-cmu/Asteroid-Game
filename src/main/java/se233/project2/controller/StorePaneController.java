package se233.project2.controller;

import javafx.scene.Scene;
import se233.project2.Launcher;
import se233.project2.view.GameMenu;
import se233.project2.view.StorePane;

public class StorePaneController {
    public static void setOnBackBtn() {
        Scene scene = new Scene(new GameMenu());
        Launcher.primaryStage.setScene(scene);
    }

    public static void setOnUpgradeHpBtn(StorePane pane) {
        if (pane.getPoints() - pane.getHpPrice() < 0) {
            return;
        }
        pane.setPoints(pane.getPoints() - pane.getHpPrice());
        pane.setHp(pane.getHp() + 1);
        pane.setHpLvl(pane.getHpLvl() + 1);
        pane.setHpPrice((int) Math.pow(2, pane.getHpLvl()));
        pane.updateLabels();
        updateGameData(pane.getPoints(), "HP", pane.getHp());
    }

    public static void setOnUpgradeFireRateBtn(StorePane pane) {
        if (pane.getPoints() - pane.getFireRatePrice() < 0) {
            return;
        }
        pane.setPoints(pane.getPoints() - pane.getFireRatePrice());
        pane.setFireRate(pane.getFireRate() + 1);
        pane.setFireRateLvl(pane.getFireRateLvl() + 1);
        pane.setFireRatePrice((int) Math.pow(2, pane.getFireRateLvl()));
        pane.updateLabels();
        updateGameData(pane.getPoints(), "FireRate", pane.getFireRate());
    }

    public static void setOnUpgradeBulletSpeedBtn(StorePane pane) {
        if (pane.getPoints() - pane.getBulletSpeedPrice() < 0) {
            return;
        }
        pane.setPoints(pane.getPoints() - pane.getBulletSpeedPrice());
        pane.setBulletSpeed(pane.getBulletSpeed() + 5);
        pane.setBulletSpeedLvl(pane.getBulletSpeedLvl() + 1);
        pane.setBulletSpeedPrice((int) Math.pow(2, pane.getBulletSpeedLvl()));
        pane.updateLabels();
        updateGameData(pane.getPoints(), "BulletSpeed", pane.getBulletSpeed());
    }

    private static void updateGameData(int points, String field, int value) {
        GameMenu.gameDataMap.put("Points", points);
        GameMenu.gameDataMap.put(field, value);
    }
}
