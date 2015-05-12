package javazoom.jl.player;

/**
 * Created by shenberg on 5/12/15.
 */
public interface AudioListener {

    public void GotSamples(short[] samples, int len);
}
