package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by X enocidist on 8/15/15.
 */
public class Menu {
    VBox display;
    public Menu(double BOARD_DIMN){
        this.display = createDisplay(BOARD_DIMN);
    }

    private VBox createDisplay(double BOARD_DIMN){

        VBox h = new VBox();

        Rectangle baseBackground = new Rectangle();
        baseBackground.setHeight(BOARD_DIMN);
        baseBackground.setWidth(BOARD_DIMN);
        baseBackground.setArcWidth(15);
        baseBackground.setArcHeight(15);
        baseBackground.setFill(Color.CADETBLUE);

        //title box
        //hbox
        VBox titleHBox = new VBox();

        Rectangle titleBox = new Rectangle();
        titleBox.setWidth(BOARD_DIMN * 3/5);
        titleBox.setHeight(BOARD_DIMN * 1/7);
        titleBox.setArcHeight(15);
        titleBox.setArcWidth(15);
        titleBox.setFill(Color.GRAY);

        Text text = new Text("SNAKE");
        text.setFill(Color.BLACK);
        text.setFont(new Font(25));

        titleHBox.getChildren().addAll(titleBox, text);
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(BOARD_DIMN * 2/5 * (-1));

        //new game
        VBox titleHBoxNew = new VBox();

        Rectangle titleBoxNew = new Rectangle();
        titleBoxNew.setWidth(BOARD_DIMN * 3/5);
        titleBoxNew.setHeight(BOARD_DIMN * 1/7);
        titleBoxNew.setArcHeight(15);
        titleBoxNew.setArcWidth(15);
        titleBoxNew.setFill(Color.GRAY);

        Text textN = new Text("New Game");
        textN.setFill(Color.BLACK);
        textN.setFont(new Font(25));

        titleHBoxNew.getChildren().addAll(titleBoxNew, textN);
        titleHBoxNew.setAlignment(Pos.CENTER);
        //titleHBoxNew.setTranslateY(BOARD_DIMN * 2/5 * (-1));

        //rules
        VBox titleHBoxRule = new VBox();

        Rectangle titleBoxRule = new Rectangle();
        titleBoxRule.setWidth(BOARD_DIMN * 3/5);
        titleBoxRule.setHeight(BOARD_DIMN * 1/7);
        titleBoxRule.setArcHeight(15);
        titleBoxRule.setArcWidth(15);
        titleBoxRule.setFill(Color.GRAY);

        Text textR = new Text("Controls");
        textR.setFill(Color.BLACK);
        textR.setFont(new Font(25));

        titleHBoxRule.getChildren().addAll(titleBoxRule, textR);
        titleHBoxRule.setAlignment(Pos.CENTER);
        titleHBoxRule.setTranslateY(BOARD_DIMN * 1/7 * (1));




        h.getChildren().add(baseBackground);
        //h.getChildren().add(titleHBox);
        //h.getChildren().add(titleHBoxNew);
       // h.getChildren().add(titleHBoxRule);


        //new game




        //instructions



        return h;
    }



}
