package unitable.generators;

import unitable.dataelements.ClientElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ClientElementGenerator {

    private static int id;
    private static final String[] namePresets = {
            "Сергей",
            "Александр",
            "Иван",
            "Елена",
            "Ирина",
            "Дарья",
            "Ольга",
            "Михаил",
            "Татьяна",
            "Наталья"
    };
    private static final int MAX_CREDIT_VALUE = 10000;

    private static final int MIN_COUNT_ELEMENTS = 5;
    private static final int MAX_COUNT_ELEMENTS = 100;

    public static List<ClientElement> getClientElementList() {
        List<ClientElement> list = new LinkedList<>();

        id = 0;
        Random rnd = new Random();
        int count;

        count = rnd.nextInt(MAX_COUNT_ELEMENTS - MIN_COUNT_ELEMENTS) + MIN_COUNT_ELEMENTS;
        for (int i = 0; i < count; i++) {
            list.add(new ClientElement(++id,
                    namePresets[rnd.nextInt(namePresets.length)],
                    rnd.nextInt(MAX_CREDIT_VALUE)));
        }

        return list;
    }

}
