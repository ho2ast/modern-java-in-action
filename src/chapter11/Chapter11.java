package chapter11;

import java.util.Optional;

public class Chapter11 {
  public static void main(String[] args) {
//    null 대신 Optional 클래스

//    값이 없는 상황을 어떻게 처리할까?
//    getCarInsuranceName(Person person) 을 호출할 때 자동차를 소유하지 않으면 NullPointerException 이 발생한다.
//    NPE를 피하기 위해서 다양한 곳에 null 확인 코드를 추가 할 것이다.
//    null 확인 코드가 추가된 것처럼 반복 패턴 코드를 깊은 의심이라고 부른다.

//    java 8 에서는 선택형 값의 영향을 받아서 Optional 이라는 클래스를 제공한다.
//    Optional은 선택형 값을 캡슐화 하는 클래스이다.
//    값이 있으면 Optional은 값을 감싼다. 값이 없으면 Optional.empty()로 반환한다.

//    Person 객체의 필드 Car와 Car 객체의 필드 Insurance를 Optional로 감쌌다.
//    이는 각 필드가 존재할 수도 아닐 수도 있다는 것을 명확하게 알 수 있게 해준다.
//    Insurance의 name은 Optional 감싸지 않았는데 보험회사의 이름은 반드시 있어야 하므로
//    만약 없다면 없는 이유를 명확하게 밝혀내야 한다.

//    Optional 적용 패턴
//    빈 Optional
    Optional<Car> optCar = Optional.empty();

//    null이 아닌 값으로 Optional 만들기
    Optional<Car> optCar2 = Optional.of(new Car());

//    null 값으로 Optional 만들기
    Optional<Car> car = Optional.ofNullable(new Car());

//    map 으로 optional 값을 추출하고 변환하기
    Optional<Insurance> optInsurance = Optional.ofNullable(new Insurance());
    Optional<String> name = optInsurance.map(Insurance::getName);
//    Optional의 map 메서드는 4장과 5장에서 살펴본 스트림의 map 메서드와 개념적으로 비슷하다.

//    flatMap으로 Optional 객체 연결
    Optional<Person> optPerson = Optional.of(new Person());
//    Optional<String> name =
//        optPerson.map(Person::getCar)
//            .map(Car::getInsurance)
//            .map(Insurance::getName);
//    위 코드는 컴파일 되지 않는다. getCar는 Optional<Car>을 반환하므로 Optional<Optional<Car>> 형식의 객체다.
//    flatMap을 사용하면 인수로 받은 함수를 적용해서 생성된 각각의 스트림에서 콘텐츠만 남긴다.
//    즉, 함수를 적용해서 생성된 모든 스트림이 하나의 스트림으로 병합되어 평준화된다.
//    이차원 Optional이 하나의 삼각형을 포함하는 하나의 Optional로 바뀐다.
        Optional<String> name2 =
        optPerson.flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName);
  }

  public static String getCarInsuranceName(Person person) {
//    null 확인 코드 추가
//    if (person != null) {
//      Car car = person.getCar();
//      if (car != null) {
//        Insurance insurance = car.getInsurance();
//        if (insurance != null) {
//          return insurance.getName();
//        }
//      }
//    }
    return "Unknown";
//    return person.getCar().getInsurance().getName();
  }
}
