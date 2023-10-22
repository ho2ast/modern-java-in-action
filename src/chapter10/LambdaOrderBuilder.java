package chapter10;

import java.util.function.Consumer;

public class LambdaOrderBuilder {

  public Order order = new Order();

  public static Order order(Consumer<LambdaOrderBuilder> consumer) {
    LambdaOrderBuilder builder = new LambdaOrderBuilder();
    consumer.accept(builder);
    return builder.order;
  }

  public void forConsumer(String customer) {
    order.setCustomer(customer);
  }

  public void buy(Consumer<LambdaTradeBuilder> consumer) {
    trade(consumer, Trade.Type.BUY);
  }

  public void sell(Consumer<LambdaTradeBuilder> consumer) {
    trade(consumer, Trade.Type.SELL);
  }

  private void trade(Consumer<LambdaTradeBuilder> consumer, Trade.Type type) {
    LambdaTradeBuilder builder = new LambdaTradeBuilder();
    builder.trade.setType(type);
    consumer.accept(builder);
    order.addTrade(builder.trade);
  }
}
