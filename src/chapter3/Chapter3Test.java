package chapter3;

import chapter2.Apple;
import chapter2.Color;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static java.util.Comparator.*;

public class Chapter3Test {
    @Test
    void functionalInterface() {
        Runnable r1 = () -> System.out.println("Hello World");
        r1.run();
    }

    @Test
    void executeAroundPattern() throws IOException {
        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(twoLine);
    }

    public String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/HO2AST/inflearn/modern-java-in-action/src/chapter3/data.txt"))) {
            return p.process(br);
        }

    }

    // testPredicate
    // 제네릭 메소드를 이용하고 Predicate<T>를 변수로 받는다.
    public <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                results.add(t);
            }
        }

        return results;
    }

    @Test
    void testPredicate() {
        List<String> stringList = List.of("a", "b", "", "c");
        // Predicate를 lambda를 이용하여 함수형 인터페이스(Predicate)를 인스턴스로 만드는 작업
        // 기술적으로는 인터페이스를 구현한 클래스의 인스턴스라고 할 수 있다
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        filter(stringList, nonEmptyStringPredicate).forEach(System.out::println);
    }

    // testConsumer
    // 제네릭 메소드를 이용하고 Consumer<T>를 변수로 받는다.
    public <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    @Test
    void testConsumer() {
        List<Integer> integerList = List.of(1, 2, 3, 4, 5);
        Consumer<Integer> consumer = (Integer i) -> System.out.println(i);
        Consumer<Integer> consumer1 = System.out::println;
        forEach(integerList, consumer);
    }


    // testFunction
    // 제네릭 메소드를 이용하고 Function<T>를 변수로 받는다.
    public <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> results = new ArrayList<>();
        for (T t : list) {
            results.add(f.apply(t));
        }

        return results;
    }

    @Test
    void testFunction() {
        List<String> stringList = List.of("a", "aa", "abcd");
        Function<String, Integer> function = (String s) -> s.length();
        map(stringList, function);
    }

    @Test
    void boxTest() {
        // 제네릭 파라미터는 참조형 변수만 받으므로 내부적으로 오토박싱이 발생하는 이는 메모리 비용이 소모된다.
        // 자바8에서는 이를 막기위해 특별한 함수형 인터페이스가 있다.
        Predicate<Integer> s = (Integer i) -> i % 2 != 0;
        IntPredicate s1 = (int i) -> i % 2 != 0;

        Assertions.assertThat(s.test(100)).isFalse();
        Assertions.assertThat(s1.test(100)).isTrue();
    }

    @Test
    void lambdaTest() {
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(Color.GREEN, 80),
                new Apple(Color.GREEN, 155),
                new Apple(Color.RED, 120)
        ));

        inventory.sort(new AppleComparator());

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });

        inventory.sort((o1, o2) -> o1.getWeight() - o2.getWeight());

        inventory.sort(comparing(Apple::getWeight));
    }
}
