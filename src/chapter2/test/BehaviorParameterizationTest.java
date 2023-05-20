package chapter2.test;

import chapter2.Apple;
import chapter2.AppleGreenColorPredicate;
import chapter2.BehaviorParameterization;
import chapter2.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BehaviorParameterizationTest {

    @Test
    void filterGreenApples() {
        BehaviorParameterization bp = new BehaviorParameterization();
        List<Apple> inventory = List.of(
                new Apple(Color.GREEN, 150),
                new Apple(Color.GREEN, 155),
                new Apple(Color.RED, 155),
                new Apple(Color.RED, 140)
        );

        List<Apple> greenApples = bp.filterApplesByColor(inventory, Color.GREEN);
        Assertions.assertEquals(2, greenApples.size());
    }

    @Test
    @DisplayName("동작 파라미터화 적용")
    void filterApples() {
        BehaviorParameterization bp = new BehaviorParameterization();
        List<Apple> inventory = List.of(
                new Apple(Color.GREEN, 150),
                new Apple(Color.GREEN, 155),
                new Apple(Color.RED, 155),
                new Apple(Color.RED, 140)
        );

        List<Apple> greenApples = bp.filterApples(inventory, new AppleGreenColorPredicate());
        Assertions.assertEquals(2, greenApples.size());
    }
}