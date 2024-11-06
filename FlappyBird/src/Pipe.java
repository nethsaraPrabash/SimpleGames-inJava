import java.awt.*;

public class Pipe {

    int x;
    int y;
    int width;
    int height;
    Image img;
    boolean passed = false;

    public Pipe(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.width = width;
        this.y = y;
        this.height = height;
        this.img = img;
        this.passed = passed;
    }
}
