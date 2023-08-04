package chapter8;

import java.util.*;
import java.util.stream.Collectors;

public class Chapter8 {
  public static void main(String[] args) {
//    Collection의 팩토리를 이용하여 쉽게 생성할 수 있다.
    List<String> listFactory = List.of("a", "b", "c");
//    UnsupportedOperationException 발생
//    listFactory.add("d");
//    listFactory.set(0, "d");

    Set<String> setFactory = Set.of("a", "b", "c");
    Map<String, String> mapFactory = Map.of("a", "b", "b", "c");
    Map<String, String> stringStringMap = Map.ofEntries(Map.entry("key", "value"));

    Transaction t1 = new Transaction("abcd1234");
    Transaction t2 = new Transaction("1234abcd1234");
    ArrayList<Transaction> transactionsList = new ArrayList<>();
    transactionsList.add(t1);
    transactionsList.add(t2);

//    for (Transaction transaction : transactionsList) {
//      if (Character.isDigit(transaction.getReferenceCode().charAt(0))) {
//        transactionsList.remove(transaction);
//      }
//    }

//    반복자의 상태와 컬렉션의 상태가 동기화 되지 않는다
//    Iterator의 객체를 명시적으로 사용하고 그 객체의 remove() 호출하여 해결할 수 있다.

    for (Iterator<Transaction> iterator = transactionsList.iterator(); iterator.hasNext(); ) {
      Transaction transaction = iterator.next();
      if (Character.isDigit(transaction.getReferenceCode().charAt(0))) {
        iterator.remove();
      }
    }

//    위의 코드를 removeIf로 바꿀 수 있다.
    transactionsList.removeIf(transaction -> Character.isDigit(transaction.getReferenceCode().charAt(0)));

    List<String> referenceCodes = new ArrayList<>();
    referenceCodes.add("a12");
    referenceCodes.add("B12");
    referenceCodes.add("C12");

//     아래의 코드는 새로운 리스트를 만든다.
//     새로운 리스트가 아닌 기존의 리스트를 수정해야한다.
    referenceCodes.stream()
        .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
        .collect(Collectors.toList())
        .forEach(System.out::println);

//    for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext(); ) {
//      String code = iterator.next();
//      iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
//    }
//    위의 코드는 아래의 코드처럼 replaceAll을 사용하여 구현할 수 있다.
    referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
    System.out.println(referenceCodes);

//    8.3 맵처리
//    Map 인터페이스에 새로 추가된 몇가지 디폴트 메서드
//    forEach BiConsumer(키와 값을 인수로 받음) 메서드를 이용할 수 있다.
    Map<String, Integer> ageOfFriends = Map.of("a", 10, "b", 20);
    ageOfFriends.forEach((friendName, age) -> System.out.println("name: " + friendName + ", age: " + age));

//    정렬 메서드
    Map<String, String> favoriteMovies = Map.ofEntries(Map.entry("Raphael", "StarWars"),
        Map.entry("Cristina", "Matrix"),
        Map.entry("Zcho", "Ata"),
        Map.entry("Lim", "Netflix"),
        Map.entry("Adam", "Zep"));
    favoriteMovies
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .forEachOrdered(System.out::println);

//    키가 존재하지 않을 때 사용할 수 있는 getOrDefault 메서드
    Map<String, String> movies = Map.ofEntries(Map.entry("Nam", "Hello"));
    System.out.println(movies.getOrDefault("Lim", "Netflix"));
    System.out.println(movies);

  }

  static class Transaction {
    String referenceCode;

    public Transaction(String referenceCode) {
      this.referenceCode = referenceCode;
    }

    public String getReferenceCode() {
      return referenceCode;
    }
  }
}

