package chapter5;

import chapter4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Chapter5 {
    static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    static List<Dish> specialMenu = Arrays.asList(
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER)
    );

    public static void main(String[] args) {
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());

        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // takeWhile을 이용하면 주어진 조건에 반하는 결과가 나오면 중단하고 그전 까지 발견된 요소를 저장
        List<Dish> slicedMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        System.out.println(slicedMenu1);

        // dropWhile은 주어진 조건이 처음으로 거짓이 되는 지점 까지 발견된 요소를 버리고 나머지 요소를 저장
        List<Dish> slicedMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        System.out.println(slicedMenu2);

        List<Dish> dishes = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(toList());
        System.out.println("dishes = " + dishes);

        // skip(long n) n개의 요소를 제외한 스트림을 반환한다.
        List<Dish> dishes2 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());

        System.out.println("dishes2 = " + dishes2);

        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println("dishNames = " + dishNames);

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println("wordLengths = " + wordLengths);

        List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());
        System.out.println("dishNameLengths = " + dishNameLengths);

        List<String> words2 = Arrays.asList("Hello", "World");
        List<String[]> collect2 = words2.stream()
                .map(w -> w.split(""))
                .distinct()
                .collect(toList());
        System.out.println("collect2 = " + collect2);

        List<String> uniqueCharacter = words2.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println("uniqueCharacter = " + uniqueCharacter);

        // 적어도 한 요소와 일치하는지 확인
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("vegetarian food");
        }

        // 모든 요소와 일치하는지 검사
        boolean underKiloCalories = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);
        System.out.println("underKiloCalories = " + underKiloCalories);

        // 주어진 프레디케이트와 일치하는 요소는 없는지 확인(모두 일치하지 않아야 true)
        boolean overKiloCalories = menu.stream()
                .noneMatch(dish -> dish.getCalories() >= 1000);
        System.out.println("overKiloCalories = " + overKiloCalories);

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> first = someNumbers.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
                .findFirst();
        // 병렬처리에시에는 findAny()가 더 효과적이다.

        Integer reduce = someNumbers.stream().reduce(0, (a, b) -> a + b);
        Integer reduce2 = someNumbers.stream().reduce(0, Integer::sum);
        System.out.println(reduce);
        System.out.println(reduce2);

        Optional<Integer> max = someNumbers.stream().reduce(Integer::max);

        // map reduce 패턴
        Integer reduce1 = menu.stream()
                .map(m -> 1)
                .reduce(0, (a, b) -> a + b);
        System.out.println(reduce1);

        Integer calorieSum = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("calorieSum = " + calorieSum);
    }
}
