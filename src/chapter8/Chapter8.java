package chapter8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

//    계산패턴
//    맵에 키가 존재하는지 여부에 따라 어떤 동작을 실행하고 결과를 저장해야 하는 상황에서 사용

    List<String> line = List.of("A", "b", "c", "D");
    Map<String, String> dataToHash = new HashMap<>();

    line.forEach(line2 -> dataToHash.computeIfAbsent(line2, Chapter8::addMap));

    System.out.println(dataToHash);

//    삭제 패턴
//    키가 특정한 값과 연관되었을 대만 항목을 제거 하는 오버로드 버전 메서드 제공
    Map<String, String> favoriteMovie = new HashMap<>();
    favoriteMovie.put("keykey", "valuevalue");
    favoriteMovie.put("keyke", "valuevalue");

    favoriteMovie.remove("keykey", "valuevalue");

    System.out.println(favoriteMovie);

    favoriteMovie.replaceAll((k, v) -> v.toUpperCase());
    System.out.println(favoriteMovie);


//    합침
    Map<String, String> family = Map.ofEntries(
        Map.entry("Lim", "Netflix"),
        Map.entry("Adam", "Zep"));

    Map<String, String> friend = Map.ofEntries(
        Map.entry("Raphael", "StarWars"),
        Map.entry("Cristina", "Matrix"),
        Map.entry("Zcho", "Ata"));

    Map<String, String> everyone = new HashMap<>(family);
    everyone.putAll(friend);
    System.out.println(everyone);

    Map<String, String> family2 = Map.ofEntries(
        Map.entry("Lim", "Netflix"),
        Map.entry("Adam", "Zep"));

    Map<String, String> friend2 = Map.ofEntries(
        Map.entry("Raphael", "StarWars"),
        Map.entry("Cristina", "Matrix"),
        Map.entry("Adam", "Zep"));

    Map<String, String> everyone2 = new HashMap<>(family2);
    friend2.forEach((k, v) ->
        everyone2.merge(k, v, (v1, v2) -> v1 + " & " + v2));
    System.out.println(everyone2);
  }
  public static String addMap(String key){
    return "CC";
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

