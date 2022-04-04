package ru.sbrf;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.sbrf.CityUtils.*;


public class Main {

    public static void main(String[] args) {
        List<City> cities = parse();

        print(cities); // Вывод данных массива с городами в консоль

        // Сортировка массива по наименованию городов
        //print(sortListNameCity(cities));
        // Сортировка массива по наименованию городов, используя lambda-выражения
        sortListNameCityV1(cities);
        print(cities);

        // Сортировка массива по федеральным округам и наименваниям городов в них
        //print(sortListDistrictAndNameCity(cities));
        // Сортировка V1 массива по федеральным округам и наименваниям городов в них
        sortListDistrictAndNameCityV1(cities);
        print(cities);

        //поиск города с наибольшим количеством жителей
        searchСityMaxPopulation(cities);
        //searchСityMaxPopulationV1(cities);

        //Поиск количества городов в разрезе регионов
        quantityOfCitiesByRegion(cities);
        //quantityOfCitiesByRegionV1(cities);


    }

    /**
     * Поиск количества городов в каждом из регионов (lambda-выражения)
     *
     * @param cities массив городов
     */
    private static void quantityOfCitiesByRegionV1(List<City> cities) {
        Map<String, Integer> regions = new HashMap<>();
        cities.forEach(city -> regions.merge(city.getRegion(), 1, Integer::sum));
        regions.forEach((k, v) -> System.out.println(MessageFormat.format(" {0} = {1}", k, v)));
    }

    //поиск количества городов в разрезе регионов

    private static void quantityOfCitiesByRegion(List<City> cities) {
        cities.stream()
                .collect(Collectors.groupingBy(
                        City::getRegion, Collectors.counting()))
                .forEach((s, count) -> System.out.println(s + " - " + count));

    }
/*
    Поиск города с наибольшим количеством жителей
     Преобразовать список городов в массив
     и путем перебора массива найти индекс элемента
     и значение с наибольшим количеством жителей города.
*/

    private static void searchСityMaxPopulation(List<City> cities) {
        City[] arr = cities.toArray(new City[0]);
        int max = 0;
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getPopulation() > max) {
                max = arr[i].getPopulation();
                index = i;
            }
        }
        System.out.println(MessageFormat.format("[{0}] = {1}", index, max));

    }

    /**
     * Поиск города с наибольшим количеством жителей с использованием lambda-выражений
     *
     * @param cities массив городов
     */
    private static void searchСityMaxPopulationV1(List<City> cities) {
        System.out.println(cities.stream().max(Comparator.comparing(City::getPopulation)));
    }

    /**
     * Сортировка массива городов по наименованию в алфавитном порядке по убыванию
     * без учета регистра, используя lambda-выражения
     *
     * @param cities массив городов
     */

    private static void sortListNameCityV1(List<City> cities) {
        cities.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }

    //Сортировка списка городов по наименованию
    // в алфавитном порядке по убыванию без учета регистра;
    private static List<City> sortListNameCity(List<City> cities) {
        List<City> sortCitiesName = cities.stream()
                .sorted(Comparator.comparing(City::getName))
                .collect(Collectors.toList());
        return sortCitiesName;
    }

    //Сортировка списка городов по федеральному округу и наименованию города
    // внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра;

    private static List<City> sortListDistrictAndNameCity(List<City> cities) {
        List<City> sortCitiesDistrictAndName = cities.stream()
                .sorted(Comparator.comparing(City::getDistrict))
                .sorted(Comparator.comparing(City::getName))
                .collect(Collectors.toList());
        return sortCitiesDistrictAndName;
    }

    /**
     * Сортировка массива городов по федеральному округу и наименованию города внутри каждого федерального округа
     * в алфавитном порядке по убыванию с учетом регистра
     * используя {@link java.util.Comparator}
     *
     * @param cities массив городов
     */

    private static void sortListDistrictAndNameCityV1(List<City> cities) {
        cities.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
    }

    /*
      Вывод в консоль списка городов
     */
    public static void print(List<City> cities) {
        cities.forEach(System.out::println);
    }

}
