package unitable.generators;

import unitable.dataelements.CatalogElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CatalogElementGenerator {

    private static int id;
    private static final String[] namePresets = {
            "Samsung",
            "Intel",
            "Asus",
            "Dell",
            "Gigabyte",
            "MSI",
            "Palit",
            "BenQ",
            "SVEN",
            "Cooler Master"
    };

    private static final int MIN_COUNT_ELEMENTS = 5;
    private static final int MAX_COUNT_ELEMENTS = 100;

    public static List<CatalogElement> getCatalogElementList() {
        List<CatalogElement> list = new LinkedList<>();

        id = 0;
        Random rnd = new Random();
        int count;

        count = rnd.nextInt(MAX_COUNT_ELEMENTS - MIN_COUNT_ELEMENTS) + MIN_COUNT_ELEMENTS;
        for (int i = 0; i < count; i++) {
            list.add(new CatalogElement(++id, namePresets[rnd.nextInt(namePresets.length)]));
        }

        return list;
    }

}
