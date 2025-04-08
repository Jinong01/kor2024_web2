package example.컬렉션프레임워크;

import lombok.val;

import java.util.*;

public class Example {
    public static void main(String[] args) {

        // 컬렉션프레임워크 : 자료들을 저장하는 구조(자료구조), 자료구조 기반으로 여러가지 인터페이스/클래스
        // 1. List interface : 요소를 순서대로 저장하며, 중복 허용
        // 2. Set interface : 요소의 저장순서를 보장 하지 않고, 중복 허용하지 않는다
        // 3. Map interface : 요소를 key(키) - value(값) 쌍으로 저장, 키는 중복 불가능, 값은 중복 가능하다
            // * 인터페이스란? 추상 메소드를 선언하고 각 클래스들이 구현하여 사용한다.

        // [1] List 구현클래스 : ArrayList, Vector, LinkedList 등등
        List<String > list1 = new ArrayList<>(); // (1)
        ArrayList<String > list2 = new ArrayList<>(); // (2)
        // 차이점 : List 타입으로 선언된 변수는 ArrayList, Vector, LinkedList 객체를 모두 저장할 수 있다.
        //         ArrayList 타입으로 선언된 변수는 ArrayList 객체만 저장할 수 있다.
            // 즉] 인터페이스 타입의 변수는 해당 인터페이스를 구현한 모든 클래스의 객체들을 저장할 수 있다. (다형성)
       // List<String> list = new List<>(); -> 오류 : 인터페이스는 객체를 생성할 수 없다.
            // 인터페이스는 생성자가 없으므로 객체 생성할 수 없다.
            // 생성자란? 인스턴스(객체)를 메모리에 만들기 위한 초기화 메소드 역할
        List<String> list3 = new ArrayList<>();     // 특징 : 배열 기반, index 사용, [a, b, c], 비동기화, 뒤에서부터 추가/삭제가 빠르다 -> 검색/읽기 많을때 사용
        List<String> list4 = new Vector<>();        // 특징 : 배열 기반, index 사용, [a, b, c] -> 동기화(대기상태) 필요할때 사용
        List<String> list5 = new LinkedList<>();    // 특징 : 연결 리스트 기반, 두 객체를 참조, a(b참조) --> b(c참조) --> c, 중간 추가/삭제가 빠르다 -> 중간 추가/삭제 많을때 사용

        // [2] Set 구현클래스 : HashSet, LinkedHashSet, TreeSet 등등
        Set<String > set1 = new HashSet<>();       // 특징 : 중복이 없는 구조를 사용할때
        Set<String > set2 = new LinkedHashSet<>(); // 특징 : 저장된 순서를 유지하고, 중복이 없는 구조
        Set<String > set3 = new TreeSet<>();       // 특징 : 자동정렬, 중복이 없는 구조

        // [3] Map 구현클래스 : HashMap, LinkedHashMap, TreeMap 등등
        Map<String , Integer> map1 = new HashMap<>();       // 특징 : key 의 순서를 유지하지 않는다. 빠른 검색 가능
        Map<String , Integer> map2 = new LinkedHashMap<>(); // 특징 : 입력된 key 순서를 유지를 한다.
        Map<String , Integer> map3 = new TreeMap<>();       // 특징 : key 순으로 자동 정렬, key 의 순서를 유지하지 않는다.

        // [4] < > : 제네릭 타입, 컬렉션프레임워크에서 주로 사용된다. JPA 와 같이 여러 라이브러리에서도 사용된다.
            // 제네릭 타입 이란? 클래스를 만들때 멤버변수/메소드 타입을 정의하지 않고 인스턴스를 생성할때 타입 정의한다.

        // (1) 제네릭 타입이 없을때, 고려할 사항 : 1.형변환이 필요하다 2.타입 안정성 부족
        Box box1 = new Box(); box1.value = "유재석";

        Box box2 = new Box(); box2.value = 40;

        String str1 = (String) box1.value;
        int num1 = (Integer) box2.value;
        // (2) 제네릭 타입이 있을때
        Contain<String> cont1 = new Contain<>(); cont1.value = "유재석";
        String str2 = cont1.value;

        Contain<Integer> cont2 = new Contain<>(); cont2.value = 40;
        int num2 = cont2.value;
    }
}

class Contain<T>{
    T value;
}

class Box{
    Object value;
}