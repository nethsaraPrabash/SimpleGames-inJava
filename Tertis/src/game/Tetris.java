package game;
import counter.*;
import des.Block;
import des.ColoredTable;

import java.awt.*;
import java.util.Random;


public class Tetris {
    ColoredTable ct;     //TODO
    public boolean end = false;
    public int h, w, x, y, ghx, ghy;
    // ghx/y - ghost figure
    Block[] b = new Block[]{Block.fig1, Block.fig2, Block.fig3,
            Block.fig4, Block.fig5, Block.fig6, Block.fig7};
    Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.CYAN,
            Color.YELLOW};
    public Block cur = null,
            next = getNext(),
            hold = null;
    public boolean drawcur = false,
            holdf = false;
    //int points = 0, lines = 0;
    public int ptinc = 0;

    public Counter ptc = new ProgressionCounter(100);
    //new StandardCounter(100);

    public Tetris(int x, int y) {
        ct = new ColoredTable(x, y);
        h = x;
        w = y;
    }

    private void gen() {
        ptinc = 0;
        if(next == null)
            next = getNext();
        if(!canGen()) {
            cur = null;
            end = true;
            return;
        }
        x = 0;
        y = w / 2 - next.w / 2;
        cur = next;
        next = getNext();
        drawcur = true;
        holdf = false;
        calcghost();
    }

    public boolean checkmove() {
        int ch = cur.h,
                cw = cur.w;
        for(int i = 0; i < ch; i++)
            for(int j = 0; j < cw; j++) {
                if(cur.block[i][j] &&
                        x - ch + i >= 0 &&
                        ct.t[x - ch + i][y + j] && drawcur)
                    end = true;
                if (x - ch + i + 1 == h ||
                        (cur.block[i][j] &&
                                x - ch + i >= 0 &&
                                ct.t[x - ch + i + 1][y + j]))
                    return false;
            }
        return true;
    }

    public boolean move() {
        if(end)
            return false;
        boolean continuemv = true;
        if(cur == null)
            gen();
        if (checkmove()) x++;
        else if(!end) {
            continuemv = false;
            if(drawcur) {
                ptc.figureStopped();
                int ch = cur.h,
                        cw = cur.w;
                for(int i = 0; i < ch; i++)
                    for(int j = 0; j < cw; j++)
                        if (x - ch + i >= 0 &&
                                cur.block[i][j]) {
                            ct.t[x - ch + i][y + j] = true;
                            ct.code[x - ch + i][y + j] = 1;
                            ct.c[x - ch + i][y + j] = cur.color;
                        }
            }
            if(!checkLine())
                gen();
        }
        calcghost();
        return continuemv;
        //gameover();  //checking gameover
    }

    public boolean gameover() {
        if(end)
            return true;
        for(int i = 0; i < w; i++)
            if (ct.t[0][i]) {
                end = true;
                return true;
            }
        return false;
    }

    public ColoredTable table() {
        ColoredTable pt = new ColoredTable(h, w);
        for(int i = 0; i < h; i++)
            for(int j = 0; j < w; j++) {
                pt.t[i][j] = ct.t[i][j];
                pt.c[i][j] = ct.c[i][j];
                pt.code[i][j] = ct.code[i][j];
            }
        if(!end && cur != null && drawcur) {
            int ch = cur.h,
                    cw = cur.w;
            for(int i = 0; i < ch; i++)
                for(int j = 0; j < cw; j++)
                    if(cur.block[i][j]) {
                        if (x - ch + i >= 0) {
                            pt.t[x  - ch + i][y + j] = true;
                            pt.code[x  - ch + i][y + j] = 1;
                            pt.c[x  - ch + i][y + j] = cur.color;
                        }

                        if(ghx - x >= cur.h) {
                            pt.code[ghx + i][ghy + j] = 2;
                            pt.c[ghx + i][ghy + j] = cur.color;
                        }
                    }
        }
        return pt;
    }

    private Block getNext() {
        Random r = new Random();
        Block tmp = b[r.nextInt(b.length)];
        tmp.color = colors[r.nextInt(colors.length)];
        return tmp;
    }

    private boolean canGen() {
        int nx = 0,
                ny = w / 2 - next.w / 2,
                nh = next.h,
                nw = next.w;
        for(int i = 0; i < nh; i++)
            for(int j = 0; j < nw; j++) {
                if(next.block[i][j] &&
                        nx - nh + i >= 0 &&
                        ct.t[nx - nh + i][ny + j]) {
                    end = true;
                    return false;
                }
            }
        return true;
    }

    public void moveLeft() {
        if(end)
            return;
        int ch = cur.h,
                cw = cur.w;
        for(int i = 0; i < ch; i++)
            for(int j = 0; j < cw; j++) {
                if (y + j - 1 < 0 ||
                        (cur.block[i][j] &&
                                x - ch + i >= 0 &&
                                ct.t[x - ch + i][y + j - 1]))
                    return;
            }
        y--;
        calcghost();
    }

    public void moveRight() {
        if(end)
            return;
        int ch = cur.h,
                cw = cur.w;
        for(int i = 0; i < ch; i++)
            for(int j = 0; j < cw; j++) {
                if (y + j + 1 == w ||
                        (cur.block[i][j] &&
                                x - ch + i >= 0 &&
                                ct.t[x - ch + i][y + j + 1]))
                    return;
            }
        y++;
        calcghost();
    }

    public void rotate() {
        if(end)
            return;
        Block rot = cur.spin();
        int ny = y;
        while(rot.w + ny > w)
            ny--;
        if(rot.h + x > h)
            return;
        int rh = rot.h,
                rw = rot.w;
        for(int i = 0; i < rh; i++)
            for(int j = 0; j < rw; j++) {
                if (rot.block[i][j] &&
                        x - rh + i >= 0 &&
                        ct.t[x - rh + i][ny + j])
                    return;
            }
        y = ny;
        rot.color = cur.color;
        cur = rot;
        calcghost();
    }

    private boolean checkLine() {
        for(int k = h - 1; k > 0; k--) {
            boolean ln = true;
            for(int j = 0; j < w; j++)
                ln &= ct.t[k][j];
            if(ln) {
                for(int i = k; i > 0; i--)
                    for(int j = 0; j < w; j++) {
                        ct.t[i][j] = ct.t[i - 1][j];
                        ct.code[i][j] = ct.code[i - 1][j];
                        //ct.t[i][j] = ct.t[i - 1][j];
                        ct.c[i][j] = ct.c[i - 1][j];
                    }
                ptc.lineCleared(++ptinc);
                //points += (++ptinc) * 100;
                //lines++;
                drawcur = false;
                return true;
            }
        }
        return false;
    }

    private void calcghost() {
        if(end)
            return;
        int xinc = -1;
        for(int i = 0; i < cur.w; i++) {
            int d = 0, inc = 0;
            for(int j = 0; j < cur.h; j++)
                if(cur.block[j][i])
                    d = j + x + 1;
            while((d < h && !ct.t[d][y + i])) {
                inc++;
                d++;
            }
            if(xinc == -1 || xinc > inc)
                xinc = inc;
        }
        ghx = x + xinc;
        ghy = y;
    }

    public void swap() {
        if(!holdf) {
            if(hold == null) {
                //hold = cur;
                hold = cur.original();
                gen();
            } else {
                Block tmp = hold;
                //hold = cur;
                hold = cur.original();
                cur = tmp;
                x = 0;
                y = w / 2 - cur.w / 2;
                calcghost();
            }
            holdf = true;
        }
    }
}
