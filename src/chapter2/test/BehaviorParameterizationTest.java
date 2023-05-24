package chapter2.test;

import chapter2.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BehaviorParameterizationTest {

    BehaviorParameterization bp = new BehaviorParameterization();
    List<Apple> inventory = List.of(
            new Apple(Color.GREEN, 150),
            new Apple(Color.GREEN, 155),
            new Apple(Color.RED, 155),
            new Apple(Color.RED, 140)
    );

    @Test
    @DisplayName("동작 파라미터화 적용 전")
    void filterGreenApples() {
        List<Apple> greenApples = bp.filterApplesByColor(inventory, Color.GREEN);
        assertThat(greenApples.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("구현 클래스 사용")
    void filterApples() {
        List<Apple> greenApples = bp.filterApples(inventory, new AppleGreenColorPredicate());
        assertThat(greenApples.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("익명 클래스 사용")
    void filterApplesWithAnonymousClass() {
        List<Apple> redApples = bp.filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return Color.RED.equals(apple.getColor());
            }
        });

        assertThat(redApples.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("람다 표현식 사용")
    void filterApplesWithLambda() {
        List<Apple> result = bp.filterApples(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("리스트 형식으로 추상화 - 제네릭 메서드 사용")
    void filterWithAbstraction() {
        List<Apple> result = filter(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
        assertThat(result.size()).isEqualTo(2);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
}

interface Predicate<T> {
    boolean test(T t);
}
