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
        ArrayList<Integer> coloursWithWhite = new ArrayList<>(_colours);
        coloursWithWhite.add(_white);
        return coloursWithWhite;
    }

    public ArrayList<Integer> getAllWithBlack() {
        ArrayList<Integer> coloursWithBlack = new ArrayList<>(_colours);
        coloursWithBlack.add(_black);
        return coloursWithBlack;
    }

    public Integer get(int i) {
        return _colours.get(i % this._colours.size());
    }

    public Integer getWithWhite(int i) {
        ArrayList<Integer> coloursWithWhite = new ArrayList<>(_colours);
        coloursWithWhite.add(_white);
        return coloursWithWhite.get(i % coloursWithWhite.size());
    }

    public Integer getWithBlack(int i) {
        ArrayList<Integer> coloursWithBlack = new ArrayList<>(_colours);
        coloursWithBlack.add(_black);
        return coloursWithBlack.get(i % coloursWithBlack.size());
    }

    public Integer getWithAll(int i) {
        ArrayList<Integer> coloursWithAll = new ArrayList<>(_colours);
        coloursWithAll.add(_black);
        coloursWithAll.add(_white);
        return coloursWithAll.get(i % coloursWithAll.size());
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
