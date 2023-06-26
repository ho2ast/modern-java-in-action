package chapter5;

import chapter4.Dish;
import com.sun.jdi.IntegerValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
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

        // 실전 연습
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> no1 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted((o1, o2) -> o1.getValue() - o2.getValue())
                .collect(toList());
        System.out.println("no1 = " + no1);

        // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> no2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println("no2 = " + no2);

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> no3 = transactions.stream()
                .map(transaction -> transaction.getTrader())
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println("no3 = " + no3);

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        String no4 = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        System.out.println("no4 = " + no4);

        // 5. 밀라노에 거래자가 있는가?
        boolean no5 = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milano"));
        System.out.println("no5 = " + no5);

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        List<Integer> no6 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(toList());
        System.out.println("no6 = " + no6);

        // 7. 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> no7 = transactions.stream()
                .map(transaction -> transaction.getValue())
                .reduce(Integer::max);
        System.out.println("no7 = " + no7);

        // 8. 전체 트랜잭션 중 최솟값은 얼마인가?
        Optional<Integer> no8 = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        System.out.println("no8 = " + no8);



        // 기본형 특화 스트림
        int sum = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        OptionalInt max1 = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        int i = max1.orElse(1);

        // 숫자 범위
        // 1과 100 포함
        IntStream intStream = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(intStream.count());

        // 1과 100 포함 안함
        IntStream range = IntStream.range(1, 100);
    }
}
