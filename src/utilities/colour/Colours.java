package utilities.colour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static utilities.Random.random;

public class Colours {
    ArrayList<Integer> _colours = new ArrayList<>();
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

    public ArrayList<Integer> getAllWithWhite() {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_white);
        return colours;
    }

    public ArrayList<Integer> getAllWithBlack() {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_black);
        return colours;
    }

    public ArrayList<Integer> getAllWithBlackAndWhite() {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_black);
        colours.add(_white);
        return colours;
    }

    public ArrayList<Integer> getBlackAndWhiteOnly() {
        ArrayList<Integer> colours = new ArrayList<>();
        colours.add(_black);
        colours.add(_white);
        return colours;
    }

    public Integer get(int i) {
        return _colours.get(i % this._colours.size());
    }

    public Integer getWithWhite(int i) {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_white);
        return colours.get(i % colours.size());
    }

    public Integer getWithBlack(int i) {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_black);
        return colours.get(i % colours.size());
    }

    public Integer getWithAll(int i) {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_black);
        colours.add(_white);
        return colours.get(i % colours.size());
    }

    public int rand() {
        return _colours.get(new Random().nextInt(_colours.size()));
    }

    public int randWithBlack() {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_black);
        return colours.get(new Random().nextInt(colours.size()));
    }

    public int randWithWhite() {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_white);
        return colours.get(new Random().nextInt(colours.size()));
    }

    public int randAll() {
        ArrayList<Integer> colours = new ArrayList<>(_colours);
        colours.add(_black);
        colours.add(_white);
        return colours.get(new Random().nextInt(colours.size()));
    }

    public int black() {
        return _black;
    }

    public int blackOrWhite() {
        ArrayList<Integer> colours = new ArrayList<>();
        colours.add(_black);
        colours.add(_white);
        return colours.get(new Random().nextInt(colours.size()));
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
