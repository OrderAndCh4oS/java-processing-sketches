package utilities.colour;

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

    public ArrayList<Integer> getAll() {
        return _colours;
    }

    public Integer get(int i) {
        return _colours.get(i % this._colours.size());
    }

    public int rand() {
        return _colours.get(new Random().nextInt(_colours.size()));
    }

    public int bg() {
        return _bg;
    }

    public int size() {
        return _colours.size();
    }
}
