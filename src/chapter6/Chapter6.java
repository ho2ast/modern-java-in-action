package chapter6;

import chapter4.Dish;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Chapter6 {
  /**
   * <p> {@link java.util.stream.Collectors} 인터페이스 구현은 스트림의 요소를 어떤 식으로 도출할지 지정한다.
   */
  public enum CaloricLevel {
    DIET, NORMAL, FAT
  }

  public static List<Dish> menu = Arrays.asList(
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

    // 스트림에서 최대, 최소값 연산
    Optional<Dish> max = menu.stream()
        .max(Comparator.comparingInt(Dish::getCalories));

    Optional<Dish> min = menu.stream()
        .min(Comparator.comparingInt(Dish::getCalories));

    Integer sum = menu.stream()
        .collect(summingInt(Dish::getCalories));

    IntSummaryStatistics summaryStatistics = menu.stream()
        .collect(summarizingInt(Dish::getCalories));

    // 문자열 연결
    String menus = menu.stream()
        .map(Dish::getName)
        .collect(joining(", "));
    System.out.println(menus);

    Integer collect = menu.stream()
        .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));

    // grouping
    Map<Dish.Type, List<Dish>> collect1 = menu.stream()
        .collect(groupingBy(Dish::getType));
    System.out.println("collect1 = " + collect1);

    // 칼로리 별로 그룹화하기
    Map<CaloricLevel, List<Dish>> collect2 = menu.stream()
        .collect(groupingBy(dish -> {
          if (dish.getCalories() < 400) return CaloricLevel.DIET;
          else if (dish.getCalories() < 700) return CaloricLevel.NORMAL;
          else return CaloricLevel.FAT;
        }));
    System.out.println(collect2);

    // 다수준 그룹화
    Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect3 = menu.stream()
        .collect(
            groupingBy(Dish::getType,
                groupingBy(dish -> {
                  if (dish.getCalories() <= 400)
                    return CaloricLevel.DIET;
                  else if (dish.getCalories() < 700)
                    return CaloricLevel.NORMAL;
                  else return CaloricLevel.FAT;
                }))
        );
    System.out.println(collect3);

    // 서브그룹의 데이터 수집
    Map<Dish.Type, Long> typesCount = menu.stream()
        .collect(groupingBy(Dish::getType, counting()));
    System.out.println("typesCount = " + typesCount);

    // 분할 (Predicate를 분류함수로 사용하는 그룹화 기능), 특수한 종류의 그룹화
    Map<Boolean, List<Dish>> collect4 = menu.stream()
        .collect(partitioningBy(Dish::isVegetarian));
    System.out.println("collect4 = " + collect4);

    Map<Boolean, Map<Dish.Type, List<Dish>>> collect5 = menu.stream()
        .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    System.out.println("collect5 = " + collect5);


  }
}
