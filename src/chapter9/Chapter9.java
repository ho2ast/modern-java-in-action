package chapter9;

import chapter4.Dish;
import chapter6.Chapter6;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static chapter6.Chapter6.*;
import static chapter6.Chapter6.menu;
import static java.util.stream.Collectors.groupingBy;

public class Chapter9 {

  public static void main(String[] args) {
//    코드 가독성 개선
//    익명 클래스를 람다 표현식으로 리팩터링 하기
    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        System.out.println("Hello");
      }
    };

    Runnable r2 = () -> System.out.println("hello");

//    모든 익명 클래스를 람다 표현식으로 변활할 수 있는 것은 아니다.
//    첫째 익명 클래스에서 사용한 this와 super는 람다 표현식에서 다른 의미를 가진다.
//    익명 클래스에서 this는 익명클래스 자신을 가리키지만 람다에서 this는 람다를 감사는 클래스를 가르킨다.
//    둘째 익명 클래스는 감사고 있는 클래스의 변수를 가릴 수 있다. (쉐도우 변수)

    int a = 10;
    Runnable r3 = () -> {
//      int a = 2; 컴파일 에러
      System.out.println(a);
    };

    Runnable r4 = new Runnable() {
      @Override
      public void run() {
        int a = 2; // 컴파일 에러 없음
        System.out.println(a);
      }
    };


//    람다 표현식을 메서드 참조로 리팩터링 하기
//    6장의 칼로리 그룹화 코드를 리팩터링 해보자.
    Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
        menu.stream()
            .collect(groupingBy(dish -> {
              if (dish.getCalories() < 400) return CaloricLevel.DIET;
              else if (dish.getCalories() < 700) return CaloricLevel.NORMAL;
              else return CaloricLevel.FAT;
            }));

    // 람다 표현식을 별도의 메서드로 추출한 다음 groupingBy에 인수로 전달할 수 있다.
    Map<CaloricLevel, List<Dish>> dishesByCaloricLevel2 =
        menu.stream().collect(groupingBy(Dish::getCaloricLevel));

//    함수형 인터페이스 적용
//    조건부 연기 실행, 실행 어라운드 두 가지 확인

//    조건부 연기 실행
    Logger logger = Logger.getLogger("logger");
    if (logger.isLoggable(Level.FINER)) {
      logger.finer("Problem");
    }
//    logger의 상태가 isLoggable이라는 메서드에 의해 클라이언트 코드로 노출됨
    logger.log(Level.FINER, "problem");
//    logger 객체가 적절한 수준으로 설정되었는지 내부적으로 확인하는 log 메서드를 사용
//    하지만 위의 코드도 logger가 활성화 되어 있지 않더라도 항상 로깅 메세지를 평가한다.
    logger.log(Level.FINER, () -> "problem");

//    실행 어라운드
//    매번 같은 준비, 종료 과정을 반복적으로 수행하는 코드가 있다면 람다로 변환 가능


//    람다로 객체지향 디자인 패턴 리팩터링하기
//    전략, 템플릿 메서드, 옵저버, 의무 체인, 팩토리 패턴을 살펴보자

//    전략 패턴
//    한 유형의 알고리즘을 보유한 상태에서 런타임에 적절한 알고리즘을 선택하는 기법
//    예를 들어 텍스트 입력의 다양한 조건을 검증한다고 하자.
//    인터페이스 ValidationStrategy을 만들고 구현체 isAllLowerCase을 만들어 준다.
    Validator lowerCaseValidator = new Validator(new isAllLowerCase());
    boolean b1 = lowerCaseValidator.validate("bbb");
//    람다 표현식 사용
    Validator lowerCase = new Validator((String s) -> s.matches("[a-z]+"));
  }

  static class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy v) {
      this.strategy = v;
    }

    boolean validate(String s) {
      return strategy.execute(s);
    }
  }


  interface ValidationStrategy {
    boolean execute(String s);
  }

  static class isAllLowerCase implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
      return s.matches("[a-z]+");
    }
  }

}

