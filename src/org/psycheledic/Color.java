package org.psycheledic;

/**
 * Created by shenberg on 5/5/15.
 */
public class Color {
    public int r;
    public int g;
    public int b;
    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color lerp(Color other, float t) {
        return new Color((int)(other.r*t + r*(1-t)), (int)(other.g*t + g*(1-t)),(int)(other.b*t + b*(1-t)));
    }
}
