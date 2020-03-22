package collections;

import utilities.colour.Colours;

import java.util.HashMap;

public class ColourCollection {
    private HashMap<String, Colours> _colours = new HashMap<>();

    public ColourCollection() {
        _colours.put("bw", new Colours(0xff0B0B0B, 0xffADADAD, 0xff6E6E6E, 0xff858585, 0xff949494, 0xff2B2B2B, 0xff5d5d5d));
        _colours.put("candy", new Colours(0xff181F2E, 0xffFF9C7A, 0xffFAE769, 0xff58FF86, 0xffFF2BE4, 0xff6DFF2B));
        _colours.put("autumn", new Colours(0xff000D08, 0xffE2F26B, 0xff8C8372, 0xffF27C38, 0xffF21D1D));
        _colours.put("candyfloss", new Colours(0xff04090D, 0xffF25C78, 0xffBABDBF, 0xffF2D8CE, 0xffA62D2D));
        _colours.put("mocha", new Colours(0xff0D0C0B, 0xff262523, 0xffF2D6B3, 0xffA67449, 0xff594B3F));
        _colours.put("spearmint", new Colours(0xff0F0C05, 0xff4FF588, 0xffA0AAA1, 0xffCEF5BA, 0xff2CA833));
        _colours.put("furnace", new Colours(0xff0D0C0B, 0xffFFB952, 0xffFF7829, 0xffFF441F, 0xffFF0F01, 0xffFF4F6F));
        _colours.put("azure", new Colours(0xff0C080F, 0xff8017FF, 0xff4911FF, 0xff171AFF, 0xff1D68FF, 0xff6DC0FF));
        _colours.put("lime", new Colours(0xff010F03, 0xffC0FC0A, 0xff02EBA5, 0xff14DE25, 0xff65FF24, 0xff20FF7B));
        _colours.put("rubine", new Colours(0xff2B1829, 0xffFF5740, 0xffE83A49, 0xffFF4DB5, 0xffE03AE8, 0xffAD39E3));
        _colours.put("neon", new Colours(0xff0d0f0e, 0xffECFF8D, 0xffEFFF4D, 0xffFF80CA, 0xff5BB393, 0xff45FFBD));
        _colours.put("pistachio-and-peach", new Colours(0xff0d0f0e, 0xff9C36B3, 0xffFFDB80, 0xffE366FF, 0xff4DFFC2, 0xffEB38B));
        _colours.put("pastel", new Colours(0xff23364A, 0xffFFB3EE, 0xff19CCFF, 0xffFFCF80, 0xffA3FF8C, 0xffFFE68C));
        _colours.put("sunset-beach", new Colours(0xff1C1714, 0xffFF6E30, 0xff4DBEFF, 0xffD9CA9C, 0xffFFC508));
        _colours.put("toxic", new Colours(0xff1C1B19, 0xffFFD15C, 0xffD1FF77, 0xff51DB9A, 0xff33FF9F));
        _colours.put("slate-and-peach", new Colours(0xff082026, 0xff1C6277, 0xff103F4C, 0xffFFC3A1, 0xffFFA194));
        _colours.put("german", new Colours(0xff090B0D, 0xff730217, 0xffF2CB05, 0xff594302, 0xffD94F30));
        _colours.put("forest", new Colours(0xff001F16, 0xff01402E, 0xff038C65, 0xff1AD9A3, 0xff400E00, 0xff8C2203));
        _colours.put("rust", new Colours(0xff1C0C0B, 0xffF2B872, 0xffA67A53, 0xff401714, 0xffA62626, 0xffF25041));
        _colours.put("oz", new Colours(0xff060B26, 0xff034C8C, 0xff17A67D, 0xff1BBF62, 0xffF2D06B));
        _colours.put("warm-bw", new Colours(0xff1C1126, 0xff262523, 0xffF2E8DF, 0xff0D0000, 0xffBFBFBF));
        _colours.put("warm-grey", new Colours(0xff152126, 0xff2A3A40, 0xff475759, 0xffA6A397, 0xffF2DABD));
        _colours.put("cool-bw", new Colours(0xff0D0D0D, 0xff595857, 0xffD9CEC1, 0xffA69C94, 0xffF2F2F2));
        _colours.put("dragons", new Colours(0xff33012B, 0xffD93D66, 0xff8C0375, 0xff03A6A6, 0xffF2E1AC, 0xffF25C5C));
        _colours.put("constructivist", new Colours(0x010D00, 0xffF2E0C9, 0xffF21905, 0xffBF0404, 0xffF2E1AC, 0xff590202));
        _colours.put("lemon", new Colours(0x010326, 0xffF2E205, 0xffD9D6B0, 0xffA6A48A, 0xffF2CB05));
    }

    public Colours get(String name) {
        return _colours.get(name);
    }

    public void add(String name, Colours set) {
        _colours.put(name, set);
    }

    public HashMap<String, Colours> get_colours() {
        return _colours;
    }
}
