package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    private static final int BOARD_SIZE = 15; //15
    private static final double BOARD_DIMN = 600; //600
    private static final double BUFFER = 45;
    private static final double SPACING = 7;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Menu menu = new Menu(BOARD_DIMN);
        Game game = new Game(BOARD_SIZE, BOARD_DIMN, this);
        VBox root = new VBox();
        root.setSpacing(SPACING);
        root.getChildren().add(game.scoreBoard.scoreboard);
        root.getChildren().add(game.board);
        //test:
        //root.getChildren().add(menu.display);


        //end test
        //Image image = new Image(getClass().getResourceAsStream("snake1.ico"));
        //primaryStage.getIcons().add(image);



        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, BOARD_DIMN + BUFFER, BOARD_DIMN + BUFFER + SPACING * 2);
        scene.setOnKeyPressed((e) -> handleKeyPressed(e, game));
        primaryStage.setResizable(false); //false
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.show();
        game.start();

    }

    private void handleKeyPressed(KeyEvent e, Game game) {
        KeyCode keyPressed = e.getCode();

        if(game.gameState == GameState.START){
            game.setGameState(GameState.PLAYING);
            game.gameDynamic();
            return;
        }

        if ((keyPressed == KeyCode.UP) || (keyPressed == KeyCode.W)) {
            game.switchCourse(Course.UP);
        }
        if ((keyPressed == KeyCode.DOWN) || (keyPressed == KeyCode.S)) {
            game.switchCourse(Course.DOWN);
        }
        if ((keyPressed == KeyCode.RIGHT) || (keyPressed == KeyCode.D)) {
            game.switchCourse(Course.RIGHT);
        }
        if ((keyPressed == KeyCode.LEFT) || (keyPressed == KeyCode.A)) {
            game.switchCourse(Course.LEFT);
        }
        if ((keyPressed == KeyCode.SPACE)|| (keyPressed == KeyCode.P)) {
            if (game.gameState == GameState.PLAYING) {
                game.setGameState(GameState.PAUSED);
                return;
            } else if (game.gameState == GameState.PAUSED) {
                game.continueGame();
                return;
            } else if (game.gameState == GameState.LOST) {
                restart();
                return;
            }
        }
    }

    public void restart(){
        try {
            primaryStage.close();
            start(this.primaryStage);
        } catch (Exception e){

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}