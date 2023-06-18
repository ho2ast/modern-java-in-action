package chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Chapter4 {
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

    public static void main(String[] args) {
        List<String> threeHighCaloricDishNames =
                menu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .limit(3)
                        .collect(toList());
        System.out.println("threeHighCaloricDishNames = " + threeHighCaloricDishNames);
        Stream<Dish> stream = menu.stream();
        stream.forEach(System.out::println);
//        stream.forEach(System.out::println);

        /**
         * 스트림은 내부반복 사용 일반적인 for-each는 외부 반복
         *
         * 스트림의 연산에는 중간연산과 최종연산이 있다.
         * 연결할 수 있는 스트림 연산을 중간연산 스트림을 닫는 연산을 최종 연산이라고 한다.
         */

        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        List<String> collect = menu.stream()
                .map(Dish::getName)
                .collect(toList());

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());

        List<Integer> collect1 = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());

    }
}
