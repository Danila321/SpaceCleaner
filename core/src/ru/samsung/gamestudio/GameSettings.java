package ru.samsung.gamestudio;

public class GameSettings {

    // Device settings

    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;

    // Level

    public static int LEVEL = 1;

    // Physics settings

    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    public static float SHIP_FORCE_RATIO = 10;
    public static float TRASH_VELOCITY = 20;
    public static long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000; // in [ms] - milliseconds
    public static long STARTING_KIT_APPEARANCE_COOL_DOWN = 5000;
    public static int BULLET_VELOCITY = 200; // in [m/s] - meter per second
    public static int SHOOTING_COOL_DOWN = 1000; // in [ms] - milliseconds

    public static final short TRASH_BIT = 2;
    public static final short SHIP_BIT = 4;
    public static final short BULLET_BIT = 8;
    public static final short KIT_BIT = 16;

    // Object sizes

    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;
    public static final int TRASH2_WIDTH = 147;
    public static final int TRASH2_HEIGHT = 120;
    public static final int TRASH3_WIDTH = 200;
    public static final int TRASH3_HEIGHT = 200;
    public static final int KIT_WIDTH = 82;
    public static final int KIT_HEIGHT = 65;
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 45;



}
