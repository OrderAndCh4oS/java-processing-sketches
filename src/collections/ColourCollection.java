package collections;

import utilities.Colours;

import java.util.HashMap;

public class ColourCollection {
    HashMap<String, Colours> colours = new HashMap<>();

    public ColourCollection() {
        colours.put("bw", new Colours(0x0B0B0B, 0xADADAD, 0x6E6E6E, 0x858585, 0x949494));
        colours.put("candy", new Colours(0x181F2E, 0xFF9C7A, 0xFAE769, 0x58FF86, 0xFF2BE4, 0x6DFF2B));
        colours.put("autumn", new Colours(0x000D08, 0xE2F26B, 0x8C8372, 0xF27C38, 0xF21D1D));
        colours.put("candyfloss", new Colours(0x04090D, 0xF25C78, 0xBABDBF, 0xF2D8CE, 0xA62D2D));
        colours.put("mocha", new Colours(0x0D0C0B, 0x262523, 0xF2D6B3, 0xA67449, 0x594B3F));
        colours.put("spearmint", new Colours(0x0F0C05, 0x4FF588, 0xA0AAA1, 0xCEF5BA, 0x2CA833));
        colours.put("furnace", new Colours(0x0D0C0B, 0xFFB952, 0xFF7829, 0xFF441F, 0xFF0F01, 0xFF4F6F));
        colours.put("azure", new Colours(0x0C080F, 0x8017FF, 0x4911FF, 0x171AFF, 0x1D68FF, 0x6DC0FF));
        colours.put("lime", new Colours(0x010F03, 0xC0FC0A, 0x02EBA5, 0x14DE25, 0x65FF24, 0x20FF7B));
        colours.put("rubine", new Colours(0x2B1829, 0xFF5740, 0xE83A49, 0xFF4DB5, 0xE03AE8, 0xAD39E3));
    }

    public Colours get(String name) {
        return colours.get(name);
    }

    public void add(String name, Colours set) {
        colours.put(name, set);
    }
}
