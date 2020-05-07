package utilities.colour;

import java.util.ArrayList;
import java.util.Random;

import static utilities.Random.random;

public class Colours {
    ArrayList<Integer> _colours = new ArrayList<Integer>();
    int _black;
    int _white;

    public Colours(int black, int white, int... colours) {
        _black = black;
        _white = white;
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

    public int black() {
        return _black;
    }

    public int white() {
        return _white;
    }

    public int bg() {
        return random(1) > 0.5f ? _white : _black;
    }

    public int size() {
        return _colours.size();
    }
}
