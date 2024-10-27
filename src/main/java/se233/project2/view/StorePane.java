package se233.project2.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class StorePane extends StackPane {
    GridPane gridPane = new GridPane();
    int hp, points, fireRate, bulletSpeed;
    int hpPrice, fireRatePrice, bulletSpeedPrice;
    int hpLvl, fireRateLvl, bulletSpeedLvl;

    Button upgradeHpBtn, upgradeFireRateBtn, upgradeBulletSpeedBtn, backBtn;
    Label pointLbl, hpLbl, fireRateLbl, bulletSpeedLbl, hpPriceLbl, fireRatePriceLbl, bulletSpeedPriceLbl;
//    HBox topBox, hpBox, fireRateBox, bulletSpeedBox;

    public StorePane() {
//        topBox = new HBox(600);
//        hpBox = new HBox(10);
//        fireRateBox = new HBox(10);
//        bulletSpeedBox = new HBox(10);
        backBtn = new Button("Back");
        upgradeHpBtn = new Button("Upgrade");
        upgradeFireRateBtn = new Button("Upgrade");
        upgradeBulletSpeedBtn = new Button("Upgrade");
        loadData();
//        topBox.getChildren().addAll(backBtn, pointLbl);
//        hpBox.getChildren().addAll(hpLbl, hpPriceLbl, upgradeHpBtn);
//        fireRateBox.getChildren().addAll(fireRateLbl, fireRatePriceLbl, upgradeFireRateBtn);
//        bulletSpeedBox.getChildren().addAll(bulletSpeedLbl, bulletSpeedPriceLbl, upgradeBulletSpeedBtn);
//
//        topBox.setPrefWidth(800);
//        hpBox.setPrefWidth(800);
//        fireRateBox.setPrefWidth(800);
//        bulletSpeedBox.setPrefWidth(800);

        backBtn.setAlignment(Pos.CENTER_LEFT);
        pointLbl.setAlignment(Pos.CENTER_RIGHT);


        gridPane.add(backBtn, 0, 0);
        gridPane.add(pointLbl, 2, 0);
        gridPane.add(hpLbl, 0, 1);
        gridPane.add(hpPriceLbl, 1, 1);
        gridPane.add(upgradeHpBtn, 2, 1);

        gridPane.add(fireRateLbl, 0, 2);
        gridPane.add(fireRatePriceLbl, 1, 2);
        gridPane.add(upgradeFireRateBtn, 2, 2);

        gridPane.add(bulletSpeedLbl, 0, 3);
        gridPane.add(bulletSpeedPriceLbl, 1, 3);
        gridPane.add(upgradeBulletSpeedBtn, 2, 3);

        gridPane.setHgap(50);
        gridPane.setVgap(50);
        gridPane.setAlignment(Pos.CENTER);
        this.setPrefSize(800, 600);
        this.getChildren().add(gridPane);
        this.setAlignment(Pos.CENTER);
    }

    private void loadData() {
        points = GameMenu.gameDataMap.get("Points");
        hp = GameMenu.gameDataMap.get("HP");
        fireRate = GameMenu.gameDataMap.get("FireRate");
        bulletSpeed = GameMenu.gameDataMap.get("BulletSpeed");

        hpLvl = (hp - 3);
        fireRateLvl = fireRate - 3;
        bulletSpeedLvl = (bulletSpeed - 20) / 5;

        hpPrice = (int) Math.pow(2, hpLvl);
        fireRatePrice = (int) Math.pow(2, fireRateLvl);
        bulletSpeedPrice = (int) Math.pow(2, bulletSpeedLvl);

        pointLbl = new Label("Points: " + points);

        hpLbl = new Label("HP: " + hp + "\t\t\t\t Lvl: " + hpLvl);
        hpPriceLbl = new Label("Price: " + hpPrice);

        fireRateLbl = new Label("FireRate: " + fireRate + "\t\t\t Lvl: " + fireRateLvl);
        fireRatePriceLbl = new Label("Price: " + fireRatePrice);

        bulletSpeedLbl = new Label("BulletSpeed: " + bulletSpeed + "\t\t Lvl:" + bulletSpeedLvl);
        bulletSpeedPriceLbl = new Label("Price: " + bulletSpeedPrice);
    }

}
