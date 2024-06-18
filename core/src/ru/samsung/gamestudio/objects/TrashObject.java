package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {

    private static final int paddingHorizontal = 30;

    private int livesLeft;
    private final int difficult;

    public TrashObject(int width, int height, String texturePath, World world, int difficult) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.TRASH_BIT,
                world
        );

        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = difficult;
        this.difficult = difficult;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
        if (difficult == 2){
            if (livesLeft == 1){
                this.texture = new Texture(GameResources.TRASH2HALF_IMG_PATH);
            }
        } else if (difficult == 3) {
            if (livesLeft == 2){
                this.texture = new Texture(GameResources.TRASH3TWOTHIRD_IMG_PATH);
            } else {
                this.texture = new Texture(GameResources.TRASH3ONETHIRD_IMG_PATH);
            }
        }
    }
}
