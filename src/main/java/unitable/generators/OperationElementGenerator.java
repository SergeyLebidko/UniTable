package unitable.generators;

import unitable.dataelements.OperationElement;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class OperationElementGenerator {

    private static int id;
    private static final LocalDate[] datePreset = {
            LocalDate.of(2019, 1, 3),
            LocalDate.of(2019, 1, 15),
            LocalDate.of(2019, 2, 4),
            LocalDate.of(2019, 2, 20),
            LocalDate.of(2019, 3, 3),
            LocalDate.of(2019, 3, 17),
            LocalDate.of(2019, 4, 8),
            LocalDate.of(2019, 4, 24),
            LocalDate.of(2019, 5, 9),
            LocalDate.of(2019, 5, 18)
    };
    private static final String[] namePresets = {
            "Добавить",
            "Удалить",
            "Получить",
            "Сохранить",
            "Переслать",
            "Перехватить",
            "Обработать",
            "Записать",
            "Вернуть",
            "Редактировать"
    };
    private static final int MAX_COUNT_VALUE = 1000;

    private static final int MIN_COUNT_ELEMENTS = 5;
    private static final int MAX_COUNT_ELEMENTS = 100;

    public static List<OperationElement> getOperationElementList() {
        List<OperationElement> list = new LinkedList<>();

        id = 0;
        Random rnd = new Random();

        int count;

        count = rnd.nextInt(MAX_COUNT_ELEMENTS - MIN_COUNT_ELEMENTS) + MIN_COUNT_ELEMENTS;
        for (int i = 0; i < count; i++) {
            list.add(new OperationElement(++id,
                    datePreset[rnd.nextInt(datePreset.length)],
                    namePresets[rnd.nextInt(namePresets.length)],
                    rnd.nextInt(MAX_COUNT_VALUE)));
        }

        return list;
    }

}
