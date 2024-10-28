package se233.project2.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se233.project2.Launcher;

import static se233.project2.controller.StorePaneController.*;

public class StorePane extends StackPane {
    GridPane gridPane = new GridPane();
    ImageView bgView;
    int hp, points, fireRate, bulletSpeed;
    int hpPrice, fireRatePrice, bulletSpeedPrice;
    int hpLvl, fireRateLvl, bulletSpeedLvl;

    Button upgradeHpBtn, upgradeFireRateBtn, upgradeBulletSpeedBtn, backBtn;
    Label pointLbl, hpLbl, fireRateLbl, bulletSpeedLbl, hpPriceLbl, fireRatePriceLbl, bulletSpeedPriceLbl;
    Label hpLvlLbl, fireRateLvlLbl, bulletSpeedLvlLbl;

    public StorePane() {
        this.setPrefSize(800, 600);
        bgView = new ImageView(new Image(Launcher.class.getResourceAsStream("bg.jpg")));
        bgView.setFitWidth(800);
        bgView.setFitHeight(600);
        createBtns();
        loadData();
        createLabels();
        setUpGridPane();
        addStyles();
        addControllers();
        this.getChildren().addAll(bgView, gridPane);
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
    }
    
    private void createLabels() {
        pointLbl = new Label("Points: " + points);

        hpLbl = new Label("HP: " + hp);
        hpLvlLbl = new Label("Lvl: " + hpLvl);
        hpPriceLbl = new Label("Price: " + hpPrice);

        fireRateLbl = new Label("FireRate: " + fireRate);
        fireRateLvlLbl = new Label("Lvl: " + fireRateLvl);
        fireRatePriceLbl = new Label("Price: " + fireRatePrice);

        bulletSpeedLbl = new Label("BulletSpeed: " + bulletSpeed);
        bulletSpeedLvlLbl = new Label("Lvl:" + bulletSpeedLvl);
        bulletSpeedPriceLbl = new Label("Price: " + bulletSpeedPrice);
    }
    
    public void updateLabels() {
        pointLbl.setText("Points: " + points);

        hpLbl.setText("HP: " + hp);
        hpLvlLbl.setText("Lvl: " + hpLvl);
        hpPriceLbl.setText("Price: " + hpPrice);

        fireRateLbl.setText("FireRate: " + fireRate);
        fireRateLvlLbl.setText("Lvl: " + fireRateLvl);
        fireRatePriceLbl.setText("Price: " + fireRatePrice);

        bulletSpeedLbl.setText("BulletSpeed: " + bulletSpeed);
        bulletSpeedLvlLbl.setText("Lvl:" + bulletSpeedLvl);
        bulletSpeedPriceLbl.setText("Price: " + bulletSpeedPrice);
    }

    private void createBtns() {
        backBtn = new Button("Back");
        upgradeHpBtn = new Button("Upgrade");
        upgradeFireRateBtn = new Button("Upgrade");
        upgradeBulletSpeedBtn = new Button("Upgrade");
    }

    private void setUpGridPane() {
        gridPane.add(backBtn, 0, 0);
        gridPane.add(pointLbl, 6, 0);
        gridPane.add(hpLbl, 0, 1);
        gridPane.add(hpLvlLbl, 1, 1);
        gridPane.add(hpPriceLbl, 5, 1);
        gridPane.add(upgradeHpBtn, 6, 1);

        gridPane.add(fireRateLbl, 0, 2);
        gridPane.add(fireRateLvlLbl, 1, 2);
        gridPane.add(fireRatePriceLbl, 5, 2);
        gridPane.add(upgradeFireRateBtn, 6, 2);

        gridPane.add(bulletSpeedLbl, 0, 3);
        gridPane.add(bulletSpeedLvlLbl, 1, 3);
        gridPane.add(bulletSpeedPriceLbl, 5, 3);
        gridPane.add(upgradeBulletSpeedBtn, 6, 3);

        gridPane.setHgap(50);
        gridPane.setVgap(50);
        gridPane.setPrefSize(800, 600);
        gridPane.setAlignment(Pos.CENTER);
    }

    private void addStyles() {
        getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
        backBtn.getStyleClass().add("button");
        upgradeHpBtn.getStyleClass().add("button");
        upgradeFireRateBtn.getStyleClass().add("button");
        upgradeBulletSpeedBtn.getStyleClass().add("button");

        hpLbl.getStyleClass().add("scoreLabel");
        hpPriceLbl.getStyleClass().add("scoreLabel");
        hpLvlLbl.getStyleClass().add("scoreLabel");

        fireRateLbl.getStyleClass().add("scoreLabel");
        fireRatePriceLbl.getStyleClass().add("scoreLabel");
        fireRateLvlLbl.getStyleClass().add("scoreLabel");

        bulletSpeedLbl.getStyleClass().add("scoreLabel");
        bulletSpeedPriceLbl.getStyleClass().add("scoreLabel");
        bulletSpeedLvlLbl.getStyleClass().add("scoreLabel");

        pointLbl.getStyleClass().add("scoreLabel");
    }

    private void addControllers() {
        backBtn.setOnAction(event -> setOnBackBtn());
        upgradeHpBtn.setOnAction(event -> setOnUpgradeHpBtn(this));
        upgradeFireRateBtn.setOnAction(event -> setOnUpgradeFireRateBtn(this));
        upgradeBulletSpeedBtn.setOnAction(event -> setOnUpgradeBulletSpeedBtn(this));
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getHpPrice() {
        return hpPrice;
    }

    public void setHpPrice(int hpPrice) {
        this.hpPrice = hpPrice;
    }

    public int getFireRatePrice() {
        return fireRatePrice;
    }

    public void setFireRatePrice(int fireRatePrice) {
        this.fireRatePrice = fireRatePrice;
    }

    public int getBulletSpeedPrice() {
        return bulletSpeedPrice;
    }

    public void setBulletSpeedPrice(int bulletSpeedPrice) {
        this.bulletSpeedPrice = bulletSpeedPrice;
    }

    public int getHpLvl() {
        return hpLvl;
    }

    public void setHpLvl(int hpLvl) {
        this.hpLvl = hpLvl;
    }

    public int getFireRateLvl() {
        return fireRateLvl;
    }

    public void setFireRateLvl(int fireRateLvl) {
        this.fireRateLvl = fireRateLvl;
    }

    public int getBulletSpeedLvl() {
        return bulletSpeedLvl;
    }

    public void setBulletSpeedLvl(int bulletSpeedLvl) {
        this.bulletSpeedLvl = bulletSpeedLvl;
    }
    
    
}
