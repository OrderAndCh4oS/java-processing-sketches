package collections;

import utilities.colour.Colours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ColourCollection {
    private final HashMap<String, Colours> _colours = new HashMap<>();

    public ColourCollection() {
        _colours.put("bw", new Colours(0xff0B0B0B, 0xffEDEDED, 0xffADADAD, 0xff6E6E6E, 0xff858585, 0xff949494, 0xff2B2B2B, 0xff5d5d5d));
        _colours.put("candy", new Colours(0xff181F2E, 0xffEDEEFF, 0xffFF9C7A, 0xffFAE769, 0xff58FF86, 0xffFF2BE4, 0xff6DFF2B));
        _colours.put("autumn", new Colours(0xff000D08, 0xffE2F26B, 0xff8C8372, 0xffF27C38, 0xffF21D1D));
        _colours.put("candyfloss", new Colours(0xff04090D, 0xffFFF1E3, 0xffF25C78, 0xffBABDBF, 0xffF2D8CE, 0xffA62D2D));
        _colours.put("mocha", new Colours(0xff0D0C0B, 0xffECEBE9, 0xff262523, 0xffF2D6B3, 0xffA67449, 0xff594B3F));
        _colours.put("spearmint", new Colours(0xff0F0C05, 0xffF6F3EE, 0xff4FF588, 0xffA0AAA1, 0xffCEF5BA, 0xff2CA833));
        _colours.put("furnace", new Colours(0xff0D0C0B, 0xffEEE9EA, 0xffFFB952, 0xffFF7829, 0xffFF441F, 0xffFF0F01, 0xffFF4F6F));
        _colours.put("azure", new Colours(0xff0C080F, 0xffF5F2FA, 0xff8017FF, 0xff4911FF, 0xff171AFF, 0xff1D68FF, 0xff6DC0FF));
        _colours.put("lime", new Colours(0xff010F03, 0xffEDFFF4, 0xffC0FC0A, 0xff02EBA5, 0xff14DE25, 0xff65FF24, 0xff20FF7B));
        _colours.put("rubine", new Colours(0xff2B1829, 0xffF6E8FF, 0xffFF5740, 0xffE83A49, 0xffFF4DB5, 0xffE03AE8, 0xffAD39E3));
        _colours.put("neon", new Colours(0xff0d0f0e, 0xff, 0xffECFF8D, 0xffEFFF4D, 0xffFF80CA, 0xff5BB393, 0xff45FFBD));
        _colours.put("pistachio-and-peach", new Colours(0xff0d0f0e, 0xffFAF4E9, 0xff9C36B3, 0xffFFDB80, 0xffE366FF, 0xff4DFFC2, 0xff0FBA8F));
        _colours.put("pastel", new Colours(0xff23364A, 0xffEDFBFD, 0xffFFB3EE, 0xff19CCFF, 0xffFFCF80, 0xffA3FF8C, 0xffFFE68C));
        _colours.put("sunset-beach", new Colours(0xff1C1714, 0xff, 0xffFF6E30, 0xff4DBEFF, 0xffD9CA9C, 0xffFFC508));
        _colours.put("toxic", new Colours(0xff1C1B19, 0xffFDFBEC, 0xffFFD15C, 0xffD1FF77, 0xff51DB9A, 0xff33FF9F));
        _colours.put("slate-and-peach", new Colours(0xff082026, 0xffE6F3FA, 0xff1C6277, 0xff103F4C, 0xffFFC3A1, 0xffFFA194));
        _colours.put("german", new Colours(0xff090B0D, 0xffFFF8E7, 0xff730217, 0xffF2CB05, 0xff55B304, 0xffD94F30));
        _colours.put("forest", new Colours(0xff001F16, 0xffD9DBD9, 0xff01402E, 0xff038C65, 0xff1AD9A3, 0xffBD2900, 0xff8C2203));
        _colours.put("rust", new Colours(0xff1C0C0B, 0xffF4E5DE, 0xffF2B872, 0xffA67A53, 0xff401714, 0xffA62626, 0xffF25041));
        _colours.put("oz", new Colours(0xff060B26, 0xffDAE8E8, 0xff034C8C, 0xff17A67D, 0xff1BBF62, 0xffF2D06B));
        _colours.put("warm-bw", new Colours(0xff1C1126, 0xffFAF7ED, 0xff262523, 0xffF2E8DF, 0xff0D0000, 0xffBFBFBF));
        _colours.put("warm-grey", new Colours(0xff152126, 0xffF0EDE9, 0xff2A3A40, 0xff475759, 0xffA6A397, 0xffF2DABD));
        _colours.put("cool-bw", new Colours(0xff0D0D0D, 0xffF7F2EB, 0xff595857, 0xffD9CEC1, 0xffA69C94, 0xffF2F2F2));
        _colours.put("dragons", new Colours(0xff33012B, 0xffF2F0F5, 0xffD93D66, 0xff8C0375, 0xff03A6A6, 0xffF2E1AC, 0xffF25C5C));
        _colours.put("constructivist", new Colours(0xff010D00, 0xffF2EFE6, 0xffF2E0C9, 0xffF21905, 0xffBF0404, 0xffF2E1AC, 0xff590202));
        _colours.put("lemon", new Colours(0xff010326, 0xffF2F1ED, 0xffF2E205, 0xffD9D6B0, 0xffA6A48A, 0xffF2CB05));
        _colours.put("plum", new Colours(0xff011C1F, 0xffDECAE6, 0xff8C1C5A, 0xff591541, 0xff260B22, 0xff025E73, 0xff026873));
        _colours.put("spy", new Colours(0xff0F1413, 0xffE6F2F0, 0xff77A69D, 0xff3E4A2D, 0xffD8D9D0, 0xffA67F38, 0xffD99748));
        _colours.put("happy-time", new Colours(0xff1F0F19, 0xffEFF0E4, 0xffD93D76, 0xffD96CB3, 0xff202840, 0xff50BFAB, 0xffF2B544));
        _colours.put("sci-fi", new Colours(0xff1D2426, 0xffE9F0F5, 0xff2C3840, 0xff7794A6, 0xffB4CBD9, 0xffD8E8F2));
        _colours.put("copper", new Colours(0xff1D2426, 0xffF7EBE4, 0xffD7D7D9, 0xffF2DCC2, 0xffBF947A, 0xff8C7264));
        _colours.put("orange", new Colours(0xff000000, 0xffE9EDF7, 0xff2E3138, 0xffFFFFFF, 0xffF5AEAE, 0xffFF6D05));
        _colours.put("salmon-lemon", new Colours(0xff0D0D0D, 0xffFAFAFC, 0xffF0F0F2, 0xffF2E63D, 0xffF2AB6D, 0xffF26D6D));
        _colours.put("hot-dusk", new Colours(0xff1A0F26, 0xffF2E9EA, 0xffD94883, 0xffF35B6B, 0xffD5469E, 0xff381C4F));
        _colours.put("cool-dusk", new Colours(0xff151329, 0xffF0F1F5, 0xffEC68F0, 0xffA055E9, 0xff9EB0F4, 0xff151329));
        _colours.put("contructivist-real", new Colours(0xffE8E5D7, 0xffF2EBE4, 0xffFF4100, 0xff16130C));
        _colours.put("blizzard", new Colours(0xffBBE3F9, 0xffFFFFFF, 0xffB6EEFC, 0xffE9FFFF, 0xffC3F5FF, 0xffEBF6F5));
        _colours.put("red-blue", new Colours(0xff210201, 0xffF5F5F5, 0xffD42019, 0xff0B71BF));
        _colours.put("order-and-chaos", new Colours(0xff171D30, 0xfff8f8fd, 0xffFFF73D, 0xff1E2A57, 0xffE3E4E8));
    }

    public Colours get(String name) {
        return _colours.get(name);
    }

    public Colours rand() {
        List<Colours> valuesList = new ArrayList<Colours>(_colours.values());
        int randomIndex = new Random().nextInt(valuesList.size());
        return valuesList.get(randomIndex);
    }

    public void add(String name, Colours set) {
        _colours.put(name, set);
    }

    public HashMap<String, Colours> getColours() {
        return _colours;
    }

    public HashMap<String, Colours> getColours(String... names) {
        HashMap<String, Colours> newColourGroup = new HashMap<>();
        for (String name : names) {
            newColourGroup.put(name, _colours.get(name));
        }

        return newColourGroup;
    }
}
