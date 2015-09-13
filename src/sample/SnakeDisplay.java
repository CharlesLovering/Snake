package sample;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by loveringc on 7/14/2015.
 */
public class SnakeDisplay extends StackPane {
    int X;
    int Y;

    protected SnakeDisplay(double NODE_SIZE){
        Rectangle r = new Rectangle();
        r.setHeight(NODE_SIZE);
        r.setWidth(NODE_SIZE);
        r.setFill(Color.LIMEGREEN);
        this.getChildren().add(r);
    }



    public void setDisplay(int x, int y, double NODE_SIZE, double BUFFER, double SPACING){
        this.X = x;
        this.Y = y;
        //updatePosition();
        this.setTranslateX(BUFFER + SPACING * x + NODE_SIZE * x);
        this.setTranslateY(BUFFER + SPACING * y + NODE_SIZE * y);
    }
    public TranslateTransition updateDisplay(int x, int y, double NODE_SIZE, double BUFFER, double SPACING, int SPEED){
        this.X = x;
        this.Y = y;
        //updatePosition();
        TranslateTransition tt = new TranslateTransition(Duration.millis(SPEED), this);
        tt.setFromX(this.getTranslateX());
        tt.setFromY(this.getTranslateY());
        tt.setToX(BUFFER + SPACING * x + NODE_SIZE * x);
        tt.setToY(BUFFER + SPACING * y + NODE_SIZE * y);
        //tt.play();
        //tt.setOnFinished(Event -> System.out.println("PLAYED TRANSITION"));
        return tt;
    }

    public void updatePosition(){
        Text text = new Text("X = " + X + "  Y =" + Y);
        text.setFill(Color.BLACK);
        this.getChildren().remove(1);
        this.getChildren().add(1, text);
    }


    public ScaleTransition updateDisplayWrap(int x, int y, double NODE_SIZE, double BUFFER, double SPACING, int SPEED, Course direction) {
        this.X = x;
        this.Y = y;
        //Transition Two
        ScaleTransition st = new ScaleTransition(Duration.millis(SPEED), this);
        /*
        switch (direction) {
            case UP:
                st.setFromX(BUFFER + SPACING * x + NODE_SIZE * x);
                st.setFromY(BUFFER + SPACING * (y + 1) + NODE_SIZE * (y + 1));
                System.out.println("wrapped to bottom");
                break;
            case DOWN:
                st.setFromX(BUFFER + SPACING * x + NODE_SIZE * x);
                st.setFromY(BUFFER + SPACING * (y - 1) + NODE_SIZE * (y - 1));
                System.out.println("wrapped to top");
                break;
            case RIGHT:
                st.setFromX(BUFFER + SPACING * (x - 1) + NODE_SIZE * (x - 1));
                st.setFromY(BUFFER + SPACING * y + NODE_SIZE * y);
                System.out.println("wrapped to left");
                break;
            case LEFT:
                st.setFromX(BUFFER + SPACING * (x + 1) + NODE_SIZE * (x + 1));
                st.setFromY(BUFFER + SPACING * y + NODE_SIZE * y);
                System.out.println("wrapped to right");
                break;
        }
        */
        //st.setFromX(BUFFER + SPACING * x + NODE_SIZE * x);
        //st.setFromY(BUFFER + SPACING * y + NODE_SIZE * y);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1);
        st.setToY(1);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        this.setTranslateY(BUFFER + SPACING * y + NODE_SIZE * y);
        this.setTranslateX(BUFFER + SPACING * x + NODE_SIZE * x);
        return st;
    }
}

/*
public class SnakeDisplay extends StackPane {
    int X;
    int Y;
    StackPane display;

    protected SnakeDisplay(double NODE_SIZE){
        StackPane stackPane = new StackPane();
        Rectangle r = new Rectangle();
        r.setHeight(NODE_SIZE);
        r.setWidth(NODE_SIZE);
        r.setFill(Color.LIMEGREEN);
        stackPane.getChildren().add(r);
        display = stackPane;
    }



    public void setDisplay(int x, int y, double NODE_SIZE, double BUFFER, double SPACING){
        this.X = x;
        this.Y = y;
        //updatePosition();
        display.setTranslateX(BUFFER + SPACING * x + NODE_SIZE * x);
        display.setTranslateY(BUFFER + SPACING * y + NODE_SIZE * y);
    }
    public TranslateTransition updateDisplay(int x, int y, double NODE_SIZE, double BUFFER, double SPACING, int SPEED){
        this.X = x;
        this.Y = y;
        //updatePosition();
        TranslateTransition tt = new TranslateTransition(Duration.millis(SPEED), display);
        tt.setFromX(display.getTranslateX());
        tt.setFromY(display.getTranslateY());
        tt.setToX(BUFFER + SPACING * x + NODE_SIZE * x);
        tt.setToY(BUFFER + SPACING * y + NODE_SIZE * y);
        //tt.play();
        //tt.setOnFinished(Event -> System.out.println("PLAYED TRANSITION"));
        return tt;
    }

    public void updatePosition(){
        Text text = new Text("X = " + X + "  Y =" + Y);
        text.setFill(Color.BLACK);
        display.getChildren().remove(1);
        display.getChildren().add(1, text);
    }


    public ScaleTransition updateDisplayWrap(int x, int y, double NODE_SIZE, double BUFFER, double SPACING, int SPEED, Course direction) {
        this.X = x;
        this.Y = y;
        //Transition Two
        ScaleTransition st = new ScaleTransition(Duration.millis(SPEED), display);
        /*
        switch (direction) {
            case UP:
                st.setFromX(BUFFER + SPACING * x + NODE_SIZE * x);
                st.setFromY(BUFFER + SPACING * (y + 1) + NODE_SIZE * (y + 1));
                System.out.println("wrapped to bottom");
                break;
            case DOWN:
                st.setFromX(BUFFER + SPACING * x + NODE_SIZE * x);
                st.setFromY(BUFFER + SPACING * (y - 1) + NODE_SIZE * (y - 1));
                System.out.println("wrapped to top");
                break;
            case RIGHT:
                st.setFromX(BUFFER + SPACING * (x - 1) + NODE_SIZE * (x - 1));
                st.setFromY(BUFFER + SPACING * y + NODE_SIZE * y);
                System.out.println("wrapped to left");
                break;
            case LEFT:
                st.setFromX(BUFFER + SPACING * (x + 1) + NODE_SIZE * (x + 1));
                st.setFromY(BUFFER + SPACING * y + NODE_SIZE * y);
                System.out.println("wrapped to right");
                break;
        }
        */
//st.setFromX(BUFFER + SPACING * x + NODE_SIZE * x);
//st.setFromY(BUFFER + SPACING * y + NODE_SIZE * y);
/*
st.setFromX(1);
        st.setFromY(1);
        st.setToX(1);
        st.setToY(1);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        display.setTranslateY(BUFFER + SPACING * y + NODE_SIZE * y);
        display.setTranslateX(BUFFER + SPACING * x + NODE_SIZE * x);
        return st;
        }
        }
 */