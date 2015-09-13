package sample;

/**
 * Created by loveringc on 7/14/2015.
 */
public class Pointer {
    private Course direction;
    private int __X;
    private int __Y;

    Pointer(int x, int y, Course direction){
        this.__X = x;
        this.__Y = y;
        this.direction = direction;
    }
    public void updatePointer(){
        switch (this.direction) {
            case UP:
                __Y -= 1;
                break;
            case DOWN:
                __Y += 1;
                break;
            case RIGHT:
                __X += 1;
                break;
            case LEFT:
                __X -= 1;
                break;
        }
    }
    public void wrap(int BOARD_SIZE){
        switch (this.direction) {
            case UP:
                System.out.println("wrapped to bottom");
                __Y = BOARD_SIZE - 1;
                break;
            case DOWN:
                System.out.println("wrapped to top");
                __Y = 0;
                break;
            case RIGHT:
                System.out.println("wrapped to left");
                __X = 0;
                break;
            case LEFT:
                System.out.println("wrapped to right");
                __X = BOARD_SIZE - 1;
                break;
        }
    }
    public int get__X(){
        return __X;
    }
    public int get__Y(){
        return __Y;
    }
    public Course get__D() { return direction; }
    public void changeDirection(Course newDirection){
        if (((newDirection == Course.UP) || (newDirection == Course.DOWN)) && ((this.direction == Course.UP) || (this.direction == Course.DOWN))){
            return;
        }
        if (((newDirection == Course.LEFT) || (newDirection == Course.RIGHT)) && ((this.direction == Course.LEFT) || (this.direction == Course.RIGHT))){
            return;
        }
        this.direction = newDirection;
    }
}