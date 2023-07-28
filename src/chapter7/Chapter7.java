package chapter7;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Chapter7 {
  public static void main(String[] args) {
    Long reduce = Stream.iterate(1L, i -> i + 1)
        .limit(10)
        .reduce(0L, Long::sum);
    System.out.println("reduce = " + reduce);

    // 리듀싱 연산을 여러 청크에 병렬로 수행한다.
    Long reduce2 = Stream.iterate(1L, i -> i + 1)
        .limit(10)
        .parallel()
        .reduce(0L, Long::sum);
    System.out.println(reduce2);

//    벤치마크를 돌려보면 전통적인 for loop를 이용하는게 가장 빨랐다.
//    박싱된 객체가 만들어지므로 언박싱을 해야한다, 반복작업은 병렬로 수행할 수 있는 독립 단위로 나누기 어렵다.

//    LongStram.rangeClosed라는 메서드를 이용해보자
//    기본형 long을 직접 사용하므로 박싱, 언박싱의 오버헤드가 사라진다.
//    쉽게 청크로 분할할 수 있는 숫자 범위를 생산한다.
    long reduce1 = LongStream.rangeClosed(1, 10)
        .parallel()
        .reduce(0L, Long::sum);
    System.out.println("reduce1 = " + reduce1);
//    병렬 실행할 때에도 올바른 자료구조를 선택해야 한다.

//    병렬 스트림의 잘못된 사용
//    아래의 코드를 병렬로 접근 하면 데이터 레이스 문제가 발생할 수 있다.
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, 10_000_000L).forEach(accumulator::add);
    System.out.println("accumulator.total = " + accumulator.total);

//    병렬 스트림과 병렬 계산에서는 공유된 가변 상태를 피해야 한다.
    Accumulator accumulator2 = new Accumulator();
    LongStream.rangeClosed(1, 10_000_000L).parallel().forEach(accumulator::add);
    System.out.println("accumulator2.total = " + accumulator2.total);


  }
}

class Accumulator {
  public long total = 0;

  public void add(long value) {
    total += value;
  }
}