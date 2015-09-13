package sample;

import javafx.animation.ScaleTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Xenocidist on 7/15/15.
 */
public class FruitDisplay extends StackPane{
    public int X;
    public int Y;

    protected FruitDisplay(double NODE_SIZE){
        Rectangle r = new Rectangle();
        r.setHeight(NODE_SIZE);
        r.setWidth(NODE_SIZE);
        r.setFill(Color.GREEN);
        r.setOpacity(0);

        Rectangle r1 = new Rectangle();
        r1.setHeight(NODE_SIZE / 3);
        r1.setWidth(NODE_SIZE / 3);
        r1.setFill(Color.PURPLE);

        this.getChildren().addAll(r, r1);
    }

    public void setDisplay(int x, int y, double NODE_SIZE, double BUFFER, double SPACING){
        //updatePosition();
        this.X = x;
        this.Y = y;

        this.setTranslateX(BUFFER + SPACING * x + NODE_SIZE * x);
        this.setTranslateY(BUFFER + SPACING * y + NODE_SIZE * y);

        ScaleTransition st = new ScaleTransition(Duration.millis(200), this);
        st.setFromX(.6);
        st.setFromY(.6);
        st.setToX(1);
        st.setToY(1);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.play();
    }
}

/*
package sample;

import javafx.animation.ScaleTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class FruitDisplay {
    StackPane display;
    public int X;
    public int Y;

    protected FruitDisplay(double NODE_SIZE){
        StackPane stackPane = new StackPane();
        Rectangle r = new Rectangle();
        r.setHeight(NODE_SIZE);
        r.setWidth(NODE_SIZE);
        r.setFill(Color.GREEN);
        r.setOpacity(0);

        Rectangle r1 = new Rectangle();
        r1.setHeight(NODE_SIZE / 3);
        r1.setWidth(NODE_SIZE / 3);
        r1.setFill(Color.PURPLE);

        stackPane.getChildren().addAll(r, r1);
        display = stackPane;
    }

    public void setDisplay(int x, int y, double NODE_SIZE, double BUFFER, double SPACING){
        //updatePosition();
        display.setTranslateX(BUFFER + SPACING * x + NODE_SIZE * x);
        display.setTranslateY(BUFFER + SPACING * y + NODE_SIZE * y);

        ScaleTransition st = new ScaleTransition(Duration.millis(200), display);
        st.setFromX(.6);
        st.setFromY(.6);
        st.setToX(1);
        st.setToY(1);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.play();
    }
}
 */