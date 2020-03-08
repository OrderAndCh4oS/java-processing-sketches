package utilities;

import java.util.ArrayList;
import java.util.Random;

public class Colours {
    ArrayList<Integer> _colours = new ArrayList<Integer>();
    int _bg;

    public Colours(int bg, int... colours) {
        _bg = bg;
        for (int colour : colours) {
            _colours.add(colour);
        }
    }

    public ArrayList<Integer> get() {
        return _colours;
    }

    public int rand() {
        return _colours.get(new Random().nextInt(_colours.size()));
    }

    public int bg() {
        return _bg;
    }
}
