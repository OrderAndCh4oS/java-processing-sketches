package utilities.colour;

import java.util.ArrayList;
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

    public Integer get(int i) {
        return _colours.get(i % this._colours.size());
    }

    public int rand() {
        return _colours.get(new Random().nextInt(_colours.size()));
    }

    public int randWithBlack() {
        ArrayList<Integer> coloursWithBlack = new ArrayList<>(_colours);
        coloursWithBlack.add(_black);
        return coloursWithBlack.get(new Random().nextInt(coloursWithBlack.size()));
    }

    public int randWithWhite() {
        ArrayList<Integer> coloursWithWhite = new ArrayList<>(_colours);
        coloursWithWhite.add(_white);
        return coloursWithWhite.get(new Random().nextInt(coloursWithWhite.size()));
    }

    public int randAll() {
        ArrayList<Integer> coloursAll = new ArrayList<>(_colours);
        coloursAll.add(_black);
        coloursAll.add(_white);
        return coloursAll.get(new Random().nextInt(coloursAll.size()));
    }

    public int black() {
        return _black;
    }

    public int blackOrWhite() {
        ArrayList<Integer> blackAndWhite = new ArrayList<>();
        blackAndWhite.add(_black);
        blackAndWhite.add(_white);
        return blackAndWhite.get(new Random().nextInt(blackAndWhite.size()));
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
