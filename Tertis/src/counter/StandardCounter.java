package counter;

public class StandardCounter extends Counter {
    public StandardCounter(int scale) {
        super(scale);
    }

    @Override
    public void figureStopped()
    {
        pts += scale;
    }

    @Override
    public void lineCleared(int combo) {
        pts += scale;
        lines++;
    }
}
