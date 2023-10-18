package chapter9;

import chapter4.Dish;
import chapter6.Chapter6;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
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


//    템플릿 메서드
//    알고리즘의 개요를 제시한 다음에 알고리즘의 일부를 고칠 수 있는 유연함을 제공해야 할 때 사용

//    abstract class OnlineBanking {
//      public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
//        Customer c = Database.getCustomerWithId(id);
//        makeCustomerHappy(c);
//      }
//
//      abstract void makeCustomerHappy(Customer customer);
//    }

//    람다 표현식을 전달해서 다양한 동작을 추가할 수 있다.
//    new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello " + c.getName());


//    옵저버
//    어떤 이벤트가 발생했을 때 한 객체(주제)가 다른 객체 리스트(옵저버)에 자동으로 알림을 보내야 하는 상황에 사용...?
    Feed f = new Feed();
//    f.registerObserver(new NYTimes());
//    f.registerObserver(new Guardian());
    f.registerObserver((tweet -> {
      if (tweet != null && tweet.contains("money")) {
        System.out.println("Breaking news in NY! " + tweet);
      }
    }));

    f.registerObserver((tweet -> {
      if (tweet != null && tweet.contains("queen")) {
        System.out.println("Breaking news in London! " + tweet);
      }
    }));

    f.notifyObserver("The queen said");


//    의무체인
//    한 객체가 어떤 작업을 처리한 다음 다른 객체로 결과를 전달하고
//    다른 객체도 해야 할 작업을 처리한 다음 또 다른 객체로 전달하는 식이다.
    ProcessingObject<String> p1 = new HeaderTextProcessing();
    ProcessingObject<String> p2 = new SpellCheckerProcessing();
    p1.setSuccessor(p2);
    String result1 = p1.handle("Aren't labdas really sexy?!!");
    System.out.println(result1);

    UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
    UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
    String result2 = pipeline.apply("Aren't labdas really sexy?!!");
    System.out.println(result2);

//    팩토리
//    인스턴스화 로직을 클라이언트에 노출하지 않고 객체를 만들 때 팩토리 디자인 패턴을 사용한다.
    Product pF1 = ProductFactory.createProduct("loan");

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

  private static abstract class ProcessingObject<T> {

    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
      this.successor = successor;
    }

    public T handle(T input) {
      T r = handleWork(input);
      if (successor != null) {
        return successor.handle(r);
      }
      return r;
    }

    abstract protected T handleWork(T input);

  }

  private static class HeaderTextProcessing extends ProcessingObject<String> {

    @Override
    public String handleWork(String text) {
      return "From Raoul, Mario and Alan: " + text;
    }

  }

  private static class SpellCheckerProcessing extends ProcessingObject<String> {

    @Override
    public String handleWork(String text) {
      return text.replaceAll("labda", "lambda");
    }

  }

  static private class ProductFactory {

    public static Product createProduct(String name) {
      switch (name) {
        case "loan":
          return new Loan();
        case "stock":
          return new Stock();
        case "bond":
          return new Bond();
        default:
          throw new RuntimeException("No such product " + name);
      }
    }

    public static Product createProductLambda(String name) {
      Supplier<Product> p = map.get(name);
      if (p != null) {
        return p.get();
      }
      throw new RuntimeException("No such product " + name);
    }
  }

  static private interface Product {}
  static private class Loan implements Product {}
  static private class Stock implements Product {}
  static private class Bond implements Product {}

  final static private Map<String, Supplier<Product>> map = new HashMap<>();
  static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
  }
}

