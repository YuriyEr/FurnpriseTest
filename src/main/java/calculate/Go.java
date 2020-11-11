package calculate;

import java.util.HashMap;

public class Go {
    // случаи с горизонтальным резом не учитывал
    // технологический разрыв - взял только вертикальный

    public static void main(String[] args) {
        Area somePlate = new Area(17, 10); //площадь поверхности
        Area someMaterial = new Area(7, 2); //материал которым застилаем
        Area minSizePartElements = new Area(3,2); //размер минимального остатка элемента
        Integer someGap = 0;

        HashMap<String, Integer> result = getOptimalCount(somePlate, someMaterial, minSizePartElements, someGap);
        System.out.println("Кол-во целых единиц материала: " + result.get("Целые с запасом на обрезки")
                + "\nСреди " + result.get("Целые с запасом на обрезки") +
                ", " + (result.get("Целые с запасом на обрезки") - result.get("Целых"))
                + " целых кусков ушло на обрезки длинной до " + minSizePartElements.getLength()
                + "\nКол-во отрезков: " + result.get("Обрезки"));
    }

    static HashMap<String, Integer> getOptimalCount(Area plate, Area material, Area minimalPart, Integer gap) {
        HashMap<String, Integer> result;
        Integer countWholeElements; // кол-во целых элементов
        Integer countRows = plate.getWight() / material.getWight(); // кол-во рядов
        Integer countPartElemets; // кол-во отрезанных элементов
        Integer sizePartElement = 0; // кол-во использованных обрезков (целые не помещаются)
        Integer countAllWholeElementsWithParts = 0; // кол-во целых элементов с учетом запаса на резку

        if ((plate.getLength() - gap) % (material.getLength()+gap) == 0) { // если можем уложить целыми элементами без резки
            countWholeElements = plate.getLength() / material.getLength() * countRows;
            countPartElemets = 0;
        } else {
            int countWholeElementsInRow = plate.getLength() / (material.getLength() + gap);
            sizePartElement = plate.getLength() - ((material.getLength()+gap))*countWholeElementsInRow;
            countWholeElements = countWholeElementsInRow * countRows;
            countAllWholeElementsWithParts = countWholeElements;
            if (sizePartElement < minimalPart.getLength()) { // если минимальный размер остатка длинны меньше допустимого, то не заполняем
                countPartElemets = 0;
            } else {
                Integer partLenght = sizePartElement;
                countPartElemets = 0;
                for (int num = 0; num < countRows; num++) {
                    if (partLenght <= minimalPart.getLength()){
                        countPartElemets++;
                        countAllWholeElementsWithParts++;
                        partLenght = material.getLength();
                        partLenght -= sizePartElement;
                    } else {
                        if (countRows == 1) {
                            countAllWholeElementsWithParts++;
                        }
                        countPartElemets++;
                        partLenght -= sizePartElement;
                    }
                }
            }
        }
        result = new HashMap<String, Integer>();
        result.put("Целые с запасом на обрезки", countAllWholeElementsWithParts);
        result.put("Целых", countWholeElements);
        result.put("Обрезки", countPartElemets);
        return result;
    }
}
