package chapter10;

public class TradeBuilder {
  private final MethodChainOrderBuilder builder;
  public final Trade trade = new Trade();

  public TradeBuilder(MethodChainOrderBuilder builder, Trade.Type type, int quantity) {
    this.builder = builder;
    trade.setType(type);
    trade.setQuantity(quantity);
  }

  public StockBuilder stock(String symbol) {
    return new StockBuilder(builder, trade, symbol);
  }
}
