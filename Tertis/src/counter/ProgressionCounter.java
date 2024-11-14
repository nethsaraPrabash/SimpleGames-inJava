package counter;

public class ProgressionCounter extends Counter {


    public ProgressionCounter(int scale) {
        super(scale);
    }

    @Override
    public void figureStopped()
    {}

    @Override
    public void lineCleared(int combo)
    {
        pts += combo * scale;
        lines++;
    }
}
