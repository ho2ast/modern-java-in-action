package chapter9;

import java.util.ArrayList;
import java.util.List;

public interface Observer {

  void notify(String tweet);
}

class NYTimes implements Observer {
  @Override
  public void notify(String tweet) {
    if (tweet != null && tweet.contains("money")) {
      System.out.println("Breaking news in NY! " + tweet);
    }
  }
}

class Guardian implements Observer {
  @Override
  public void notify(String tweet) {
    if (tweet != null && tweet.contains("queen")) {
      System.out.println("Breaking news in London! " + tweet);
    }
  }
}

interface Subject {
  void registerObserver(Observer observer);

  void notifyObserver(String tweet);
}

class Feed implements Subject {
  private final List<Observer> observer = new ArrayList<>();

  @Override
  public void registerObserver(Observer observer) {
    this.observer.add(observer);
  }

  @Override
  public void notifyObserver(String tweet) {
    observer.forEach(o -> o.notify(tweet));
  }
}

