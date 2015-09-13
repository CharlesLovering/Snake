package sample;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by loveringc on 7/14/2015.
 */
public class Game {
    private Main main;
    public Group board;
    public ScoreBoard scoreBoard;
    private Pointer pointer;
    private ArrayList<SnakeDisplay> snakeDisplays;
    private ArrayList<FruitDisplay> fruitDisplays;
    private FruitDisplay fruitDisplay;
    private Rectangle r;
    private StackPane current_display;
    private double NODE_SIZE;
    private static boolean WRAP = false;
    private boolean WRAPPED = false;
    private Transition gameTransition;
    public int fruitCount;
    private int multiplier;

    /**
     * tail_X and tail_Y are the tail's X & Y before update
     */
    private int tail_X;
    private int tail_Y;


    private char[][] locations;
    public GameState gameState;

    private double BOARD_DIMN;
    private int BOARD_SIZE;
    private static final double BUFFER = 3;
    private static final double SPACING = 3;
    private int SPEED = 175;

    Logger logger = Logger.getLogger("Game");

    Game(int BOARD_SIZE, double BOARD_DIMN, Main main){
        this.main = main;
        this.board = createBoard(BOARD_SIZE, BOARD_DIMN);
        this.BOARD_DIMN = BOARD_DIMN;
        this.BOARD_SIZE = BOARD_SIZE;
        int x = (int)(Math.random()*BOARD_SIZE);
        int y = (int)(Math.random()*BOARD_SIZE);
        this.pointer = new Pointer(x,y,randomCourse());
        locations = new char[BOARD_SIZE][BOARD_SIZE];
        this.snakeDisplays = new ArrayList<SnakeDisplay>();
        this.fruitDisplays = new ArrayList<FruitDisplay>();
        this.multiplier = 1;
        this.scoreBoard = new ScoreBoard(300);
    }

    public void start(){
        startLocations();
        pointerLocation();
        addFruit();
        gameState = GameState.START;
    }

    public void gameDynamic(){
        if (gameState == GameState.PLAYING){
            try {
                gameTransition.setOnFinished(Event -> this.play());
            } catch (Exception e){
                PauseTransition pt = new PauseTransition(Duration.millis(1));
                gameTransition = pt;
                gameTransition.play();
                gameTransition.setOnFinished(Event -> this.play());
            }
        }
        if (gameState == GameState.LOST){
            addLoseScreen(BOARD_DIMN);
        }
        if (gameState == GameState.PAUSED){
            addPauseScreen(BOARD_DIMN);
        }
    }

    private void play() {
        int lastIndex = snakeDisplays.size() - 1;
        this.tail_X = snakeDisplays.get(lastIndex).X;
        this.tail_Y = snakeDisplays.get(lastIndex).Y;
        locations[this.tail_Y][this.tail_X] = 'o';
        Transition tt = new TranslateTransition();
        ParallelTransition pt = new ParallelTransition();

        for(int i = lastIndex; i > -1; i--){
            if (i != 0){
                tt = snakeDisplays.get(i).updateDisplay(snakeDisplays.get(i - 1).X, snakeDisplays.get(i - 1).Y, NODE_SIZE, BUFFER, SPACING, SPEED);
                pt.getChildren().add(tt);
            } else {
                pointer.updatePointer();
                if (!updateLocations()) {
                    tt = snakeDisplays.get(0).updateDisplay(pointer.get__X(), pointer.get__Y(), NODE_SIZE, BUFFER, SPACING, SPEED);
                    pt.getChildren().add(tt);
                } else {
                    if (WRAP) {
                        tt = snakeDisplays.get(0).updateDisplayWrap(pointer.get__X(), pointer.get__Y(), NODE_SIZE, BUFFER, SPACING, SPEED, pointer.get__D());
                        pt.getChildren().add(tt);
                    } else {
                        setGameState(GameState.LOST);
                    }
                }
            }
        }
        pt.play();
        //tt.play();
        //tt.setOnFinished(Event -> {return;});
        gameTransition = pt;

        //double check for fruit
        fruit_display_check();


        gameDynamic();
    }

    private boolean updateLocations(){
        try {
            switch(locations[pointer.get__Y()][pointer.get__X()]){
                case 'f':
                    //locations
                    printLocations();
                    int lastIndex = snakeDisplays.size() - 1;
                    board.getChildren().remove(fruitDisplay);
                    grow();
                    addFruit();
                    increase_speed_huh();
                    locations[pointer.get__Y()][pointer.get__X()]='s';
                    printLocations();
                    //growth
                    break;
                case 's':
                    logger.log(Level.INFO, "ATE YOURSELF!");
                    printLocations();
                    setGameState(GameState.LOST);
                    break;
                case 'o':
                    locations[pointer.get__Y()][pointer.get__X()]='s';
                    break;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            if (WRAP) {
                pointer.wrap(BOARD_SIZE);
                locations[pointer.get__Y()][pointer.get__X()]='s';
                logger.log(Level.INFO, "Wrapping");
                return true;
            } else {
                logger.log(Level.INFO, "Crashed into wall!!");
                printLocations();
                setGameState(GameState.LOST);
                return true;
            }
        }
    }
    private void increase_speed_huh(){
        if ((fruitCount % 5) == 0){
            this.multiplier++;
            updateSpeed();
        }
    }
    public void updateSpeed(){
        if (SPEED > 100){
            SPEED -= 25;
            scoreBoard.updateScore(5);
        } else if (SPEED > 75){
            SPEED -= 5;
            scoreBoard.updateScore(10);
        } else if (SPEED > 65){
            SPEED -= 3;
            scoreBoard.updateScore(100);
        }
    }

    private void grow(){
        fruitCount++;
        scoreBoard.updateScore(this.multiplier);
        SnakeDisplay snakeDisplay = new SnakeDisplay(NODE_SIZE);
        SnakeDisplay before = snakeDisplays.get(snakeDisplays.size() - 1);
       // snakeDisplay.setDisplay(before.X, before.Y, NODE_SIZE, BUFFER, SPACING);
        snakeDisplay.setDisplay(tail_X, tail_Y, NODE_SIZE, BUFFER, SPACING);


        board.getChildren().add(snakeDisplay);
        //locations[before.X][before.Y] = 's'; //this is the wrong X Y
        //locations[tail_X][tail_Y] = 's';


        snakeDisplays.add(snakeDisplay);

    }

    private void pointerLocation(){
        locations[pointer.get__Y()][pointer.get__X()] = 's';
        SnakeDisplay snake = new SnakeDisplay(NODE_SIZE);
        snake.setDisplay(pointer.get__X(), pointer.get__Y(), NODE_SIZE, BUFFER, SPACING);
        board.getChildren().add(snake);
        snakeDisplays.add(snake);
    }

    private void startLocations(){
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                locations[i][j] = 'o';
            }
        }
    }

    public void printLocations(){
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                System.out.print(locations[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private Group createBoard(int BOARD_SIZE, double BOARD_DIMN) {
        Group g = new Group();
        Rectangle r = createBackGroundRectangle(BOARD_DIMN);
        r.setFill(Color.DARKSLATEGRAY);
        g.getChildren().add(r);
        this.NODE_SIZE = (BOARD_DIMN - (2 * BUFFER) - (SPACING * (BOARD_SIZE - 1)))/BOARD_SIZE;
        for(int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                Rectangle n = new Rectangle();

                StackPane stackPane = new StackPane();
                //Text text = new Text (i + ", " + j);
                //text.setFill(Color.WHITE);
                n.setHeight(this.NODE_SIZE);
                n.setWidth(this.NODE_SIZE);
                stackPane.setTranslateX(BUFFER + SPACING * i + this.NODE_SIZE * i);
                stackPane.setTranslateY(BUFFER + SPACING * j + this.NODE_SIZE * j);
                stackPane.getChildren().addAll(n);
                g.getChildren().add(stackPane);
            }
        }
        return g;
    }

    private Rectangle createBackGroundRectangle(double BOARD_DIMN){
        Rectangle r = new Rectangle();
        r.setHeight(BOARD_DIMN);
        r.setWidth(BOARD_DIMN);
        r.setFill(null);
        colorTransition(r);
        fadeTransition(r);
        return r;
    }

    private StrokeTransition colorTransition(Rectangle r){
        StrokeTransition st = new StrokeTransition(Duration.INDEFINITE, r, Color.DARKSLATEGREY, Color.BLUEVIOLET);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        return st;
    }
    private FadeTransition fadeTransition(Rectangle r){
        FadeTransition ft = new FadeTransition(Duration.INDEFINITE, r);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return ft;
    }
    private void addFruit(){
        int x = (int)(Math.random()*BOARD_SIZE);
        int y = (int)(Math.random()*BOARD_SIZE);


        switch(locations[y][x]){
            case 'o':
                if (!fruitDisplays.isEmpty()) {
                    for (FruitDisplay fruit : fruitDisplays) {
                        board.getChildren().remove(fruit);
                    }
                }
                if ((x == pointer.get__X())&&((y == pointer.get__Y()))){
                    addFruit();
                }
                System.out.println("X = " + y + ", Y = " + x);
                FruitDisplay fd = new FruitDisplay(NODE_SIZE);
                fd.setDisplay(x, y, NODE_SIZE, BUFFER, SPACING);
                board.getChildren().add(fd);
                fruitDisplay = fd;
                fruitDisplays.add(fd);
                locations[y][x]='f';
                break;
            case 's':
                addFruit();
                break;
        }
    }

    public void switchCourse(Course course){
        this.pointer.changeDirection(course);
    }
    public void setGameState(GameState state){
        this.gameState = state;
    }
    public Course randomCourse(){
        Course value = null;
        int rand = (int)(Math.random() * 4);
        switch (rand){
            case 0:
                value = Course.UP;
                break;
            case 1:
                value = Course.DOWN;
                break;
            case 2:
                value = Course.RIGHT;
                break;
            case 3:
                value = Course.LEFT;
                break;
        }
        return value;
    }
    public void addLoseScreen(double BOARD_DIMN){
        StackPane sp = new StackPane();
        Rectangle r = new Rectangle();
        r.setHeight(BOARD_DIMN);
        r.setWidth(BOARD_DIMN);
        r.setFill(Color.DARKGRAY);
        r.setOpacity(.8);

        Text t = new Text();
        t.setText("GAME OVER");
        t.setFont(new Font("Calibri", 45));
        t.setFill(Color.FIREBRICK);
        t.setTranslateY(-25);

        //button
        StackPane button = new StackPane();
        button.setTranslateY(25);

        Rectangle rb = new Rectangle();
        rb.setHeight(50);
        rb.setWidth(100);
        rb.setFill(Color.DARKGREY);
        rb.setArcHeight(5);
        rb.setArcWidth(5);

        Text tb = new Text();
        tb.setText("Try Again!");
        tb.setFont(new Font("Calibri", 20));
        tb.setFill(Color.BLUE);
        button.getChildren().addAll(rb,tb);
        button.setOnMouseClicked(e -> main.restart());

        sp.getChildren().addAll(r,t,button);
        current_display = sp;
        //p.setOnKeyPressed(Event -> handleKeyPressedNew(Event));
        board.getChildren().add(sp);
    }
    public void addPauseScreen(double BOARD_DIMN){
        StackPane sp = new StackPane();
        Rectangle r = new Rectangle();
        r.setHeight(BOARD_DIMN);
        r.setWidth(BOARD_DIMN);
        r.setFill(Color.DARKGRAY);
        r.setOpacity(.8);

        Text t = new Text();
        t.setText("PAUSED");
        t.setFont(new Font("Calibri", 45));
        t.setFill(Color.BLUEVIOLET);
        t.setTranslateY(-25);

        //button
        StackPane button = new StackPane();
        button.setTranslateY(25);

        Rectangle rb = new Rectangle();
        rb.setHeight(50);
        rb.setWidth(100);
        rb.setFill(Color.DARKGREY);
        rb.setArcHeight(5);
        rb.setArcWidth(5);

        Text tb = new Text();
        tb.setText("CONTINUE");
        tb.setFont(new Font("Calibri", 20));
        tb.setFill(Color.GREEN);
        button.getChildren().addAll(rb,tb);
        setGameState(GameState.PLAYING);
        button.setOnMouseClicked(e -> continueGame());
        sp.getChildren().addAll(r,t,button);
        current_display = sp;
        board.getChildren().add(sp);
    }

    void fruit_display_check(){
        boolean fruit = false;

        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                if (locations[i][j] == 'f'){
                    fruit = true;
                }
            }
        }
        if (!fruit){
            addFruit();
        }

    }
    public void continueGame(){
        System.out.println("Entered cont game");
        board.getChildren().remove(current_display);
        setGameState(GameState.PLAYING);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(250));
        gameTransition = pauseTransition;
        gameTransition.play();
        gameDynamic();
    }
    /*
    private void handleKeyPressed(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if ((keyPressed == KeyCode.SPACE)) {
           continueGame();
        }
    }
    private void handleKeyPressedNew(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if (keyPressed == KeyCode.SPACE) {
            main.restart();
        }
    }
    */
}