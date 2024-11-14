package des;

import java.awt.*;

public class Block {
    public boolean [][] block;
    public int h, w;
    public Color color = Color.BLACK;
    public int spinx = 0, spiny = 0;
    public Block orig = null;

    Block(boolean [][] b) {
        block = b;
        h = b.length;
        w = b[0].length;
    }

    Block(boolean [][] b, Color c) {
        block = b;
        h = b.length;
        w = b[0].length;
        color = c;
    }

    public static final Block fig1 =
            new Block(new boolean[][]{{true, true}, {true, true}});
    public static final Block fig2 =
            new Block(new boolean[][]{{true, true, false}, {false, true, true}});
    public static final Block fig3 =
            new Block(new boolean[][]{{false, true, true}, {true, true, false}});
    public static final Block fig4 =
            new Block(new boolean[][]{{true, true, true}, {false, false, true}});
    public static final Block fig5 =
            new Block(new boolean[][]{{true, true, true}, {true, false, false}});
    public static final Block fig6 =
            new Block(new boolean[][]{{true, true, true}, {false, true, false}});
    public static final Block fig7 =
            new Block(new boolean[][]{{true, true, true, true}});

    public Block spin() {
        Block t = new Block(new boolean [w][h]);
        int i, j;
        for(i = 0; i < h; i++)
            for(j = 0; j < w; j++)
                t.block[j][h - i - 1] = this.block[i][j];
        if(orig == null)
            t.orig = this;
        else
            t.orig = orig;
        return t;
    }

    public Block original() {
        if(orig == null)
            return this;
        else
            return orig;
    }
}
