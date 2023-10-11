package chapter9;

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


//    람다표현식을 메서드 참조로 리팩터링 하기

  }
}
