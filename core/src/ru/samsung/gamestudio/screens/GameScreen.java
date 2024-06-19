package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.*;
import ru.samsung.gamestudio.components.*;
import ru.samsung.gamestudio.managers.ContactManager;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.objects.BulletObject;
import ru.samsung.gamestudio.objects.KitObject;
import ru.samsung.gamestudio.objects.ShipObject;
import ru.samsung.gamestudio.objects.TrashObject;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    GameSession gameSession;
    ShipObject shipObject;

    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ArrayList<KitObject> kitArray;

    ContactManager contactManager;

    // PLAY state UI
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;
    TextView freezeTimeText;
    TextView freezeReadyText;
    ButtonView freezeButton;

    // PAUSED state UI
    ImageView fullBlackoutView;
    TextView pauseTextView;
    ButtonView homeButton;
    ButtonView continueButton;

    // ENDED state UI
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();

        contactManager = new ContactManager(myGdxGame.world);

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        kitArray = new ArrayList<>();

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        pauseButton = new ButtonView(
                605, 1200,
                46, 54,
                GameResources.PAUSE_IMG_PATH
        );

        freezeTimeText = new TextView(myGdxGame.commonWhiteFont, 50, 395);
        freezeReadyText = new TextView(myGdxGame.commonWhiteFont, 35, 395);
        freezeButton = new ButtonView(
                25, 300,
                80, 80,
                GameResources.FREEZE_IMG_PATH
        );

        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, 282, 842, "Pause");
        homeButton = new ButtonView(
                138, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );
        continueButton = new ButtonView(
                393, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Continue"
        );

        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );

    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {

        handleInput();

        if (gameSession.state == GameState.PLAYING) {
            if (gameSession.shouldSpawnTrash()) {
                TrashObject trashObject;
                switch (GameSettings.LEVEL) {
                    case 1:
                        // Первый уровень сложности
                        trashObject = new TrashObject(
                                GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                                GameResources.TRASH_IMG_PATH,
                                myGdxGame.world,
                                1
                        );
                        trashArray.add(trashObject);
                        break;
                    case 2:
                        // Второй уровень сложности
                        switch (new Random().nextInt(2)) {
                            case 0:
                                //Препятствие первой сложности
                                trashObject = new TrashObject(
                                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                                        GameResources.TRASH_IMG_PATH,
                                        myGdxGame.world,
                                        1
                                );
                                trashArray.add(trashObject);
                                break;
                            case 1:
                                //Препятствие второй сложности
                                trashObject = new TrashObject(
                                        GameSettings.TRASH2_WIDTH, GameSettings.TRASH2_HEIGHT,
                                        GameResources.TRASH2FULL_IMG_PATH,
                                        myGdxGame.world,
                                        2
                                );
                                trashArray.add(trashObject);
                                break;
                        }
                        break;
                    case 3:
                        // Третий уровень сложности
                        switch (new Random().nextInt(4)) {
                            case 0:
                            case 1:
                                //Препятствие первой сложности
                                trashObject = new TrashObject(
                                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                                        GameResources.TRASH_IMG_PATH,
                                        myGdxGame.world,
                                        1
                                );
                                trashArray.add(trashObject);
                                break;
                            case 2:
                                //Препятствие второй сложности
                                trashObject = new TrashObject(
                                        GameSettings.TRASH2_WIDTH, GameSettings.TRASH2_HEIGHT,
                                        GameResources.TRASH2FULL_IMG_PATH,
                                        myGdxGame.world,
                                        2
                                );
                                trashArray.add(trashObject);
                                break;
                            case 3:
                                //Препятствие третьей сложности
                                trashObject = new TrashObject(
                                        GameSettings.TRASH3_WIDTH, GameSettings.TRASH3_HEIGHT,
                                        GameResources.TRASH3FULL_IMG_PATH,
                                        myGdxGame.world,
                                        3
                                );
                                trashArray.add(trashObject);
                                break;
                        }
                        break;
                }

            }

            if (gameSession.shouldSpawnKit()) {
                KitObject kitObject = new KitObject(
                        GameSettings.KIT_WIDTH, GameSettings.KIT_HEIGHT,
                        GameResources.KIT_IMG_PATH,
                        myGdxGame.world);
                kitArray.add(kitObject);
            }

            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }

            if (gameSession.isFreezing()) {
                if (gameSession.freezingTime() >= 1) {
                    backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_FREEZE_IMG_PATH);
                    freezeTimeText.setText(String.valueOf(gameSession.freezingTime()));
                    freezeReadyText.setText("");
                } else {
                    gameSession.stopFreeze();
                    backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
                }
            } else {
                if (gameSession.reloadFreezingTime() >= 1) {
                    freezeTimeText.setText(String.valueOf(gameSession.reloadFreezingTime()));
                    freezeReadyText.setText("");
                } else {
                    freezeTimeText.setText("");
                    freezeReadyText.setText("Ready!");
                }
            }

            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            updateTrash();
            updateBullets();
            updateKit();
            backgroundView.move();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            liveView.setLeftLives(shipObject.getLiveLeft());

            myGdxGame.stepWorld();
        }

        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    if (freezeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        if (gameSession.reloadFreezingTime() <= 0) {
                            gameSession.freeze();
                        }
                    } else {
                        shipObject.move(myGdxGame.touch);
                    }
                    break;

                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;

                case ENDED:

                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }

        }
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        for (KitObject kit : kitArray) kit.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        freezeTimeText.draw(myGdxGame.batch);
        freezeReadyText.draw(myGdxGame.batch);
        freezeButton.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();

    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {

            boolean hasToBeDestroyed = !trashArray.get(i).isAlive() || !trashArray.get(i).isInFrame();

            if (!trashArray.get(i).isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }

    private void updateKit() {
        for (int i = 0; i < kitArray.size(); i++) {
            boolean hasToBeDestroyed = !kitArray.get(i).isAlive();

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(kitArray.get(i).body);
                kitArray.remove(i--);
            }
        }
    }

    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }

    private void restartGame() {

        for (int i = 0; i < trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }

        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );

        bulletArray.clear();
        gameSession.startGame();
    }

}
