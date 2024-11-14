package counter;

public abstract class Counter {

    int scale, pts = 0, lines = 0;

    public Counter(int scale) {
        this.scale = scale;
    }

    public void figureStopped()
    {

    }

    public void lineCleated(int combo){}

    public int getPoints(){return pts;}

    public int getLines()
    {return lines;}

    public abstract void lineCleared(int combo);
}
