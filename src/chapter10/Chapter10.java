package chapter10;

import static chapter10.NestedFunctionOrderBuilder.*;

public class Chapter10 {

//  람다를 이용한 도메인 전용 언어
//  DSL은 특정 도메인을 대상으로 만들어진 특수 프로그래밍 언어
//  예를 들어 SQL query는 DSL 이지만 외부적(external)이라고 하는데 쿼리를 파싱하고 평가하는 API를 제공하기 때문이다
//  반면 stream의 메서드 체인은 플루언트 스타일이라고 하며 내부적(internal)이라고 할 수 있다.

//  DSL이란 특정 비즈니스 도메인을 인터페이스로 만든 API라고 할 수 있다.
//  자바의 Stream 인터페이스는 네이티브 자바 API에 작은 내부 DSL을 적용한 좋은 예이다.
//  스트림 API는 컬렉션을 조작하는 DSL이다.

  public static void main(String[] args) {
//  Order, Stock, Trade 세개의 주식 주문 모델을 생성했다.
//  해당 도메인의 객체를 이용하여 주식 거래주문을 만들어 본다.
    Order order = new Order();
    order.setCustomer("남동호");

    Trade trade1 = new Trade();
    trade1.setType(Trade.Type.BUY);

    Stock stock1 = new Stock();
    stock1.setSymbol("삼성전자가즈아");
    stock1.setMarket("KOSPI");

    trade1.setStock(stock1);
    trade1.setPrice(50000);
    trade1.setQuantity(10000);
    order.addTrade(trade1);

//    굉장히 장황한 코드가 되었다.
//    메서드 체인을 통해 DSL을 구현해보자.
    Order order2 = MethodChainOrderBuilder.forCustomer("BigBank")
        .buy(80)
        .stock("IBM")
        .on("NYE")
        .at(120.00)
        .sell(50)
        .stock("삼성전자")
        .on("KOSPI")
        .at(375.00)
        .end();

    System.out.println(order2);
  }

//  위의 기능을 중첩죈 함수(Nested Function)를 이용하여 DSL 패턴을 만들어 보자
//  NestedFunctionOrderBuilder 을 구현한다.
  Order order = order("Dongho",
      buy(10,
          stock("SAMSUN", "KOSPI"), at(70000))
  );


//  람다 표현식을 이용한 함수 시퀀싱
//  LambdaOrderBuilder 를 구현한다.
  Order order2 = LambdaOrderBuilder.order(o -> {
    o.forConsumer("Dongho");
    o.buy(t -> {
      t.quantity(10);
      t.price(70000);
      t.stock(s -> {
        s.symbol("SMAsuNG");
        s.market("KOSPI");
      });
    });
  });
}
