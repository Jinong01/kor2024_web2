package example;

import java.util.ArrayList;
import java.util.List;
/*
    자바의 상속
        - 기존 클래스(부모)의 멤버변수와 메소드를 새로운 클래스(자식)에게 물려주는 기능
        - 핵심 : 1. 클래스 코드의 재사용, 2. 유지보수 용이, 3. *다형성**
        - 자식클래스명 extends 부모클래스명{}

    자바는 100% 객체지향 이면서 다형성 특징 갖는다
        - 근거 : 자바의 최상위 클래스는 Object 클래스가 존재한다.
        즉] 개발자가 만든 모든 클래스는 Object 로 부터 상속을 자동으로 받는다. 100% 상속 개념 내포

    다형성 : 다양한 형태(모양/타입) 성질
        - 기본타입 : 기본타입변환
        - 참조타입 :
                1. 강제타입변환(캐스팅) : 부모타입이 자식타입 변환 (객체의 생성시 자식타입으로 생성되었다면 가능)
                2. 자동타입변환 : 자식타입이 부모타입 변환 (자식이 부모로부터 extends 했을 경우)
 */
class Animal{

}

class Dog extends Animal{

}

class Cat extends Animal{}

public class Task1 {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Dog dog = new Dog();
        Cat cat = new Cat();

        List<Animal> list = new ArrayList<>();
        list.add(animal);
        list.add(dog);
        list.add(cat);

        List<Object> list2 = new ArrayList<>();
        list2.add(animal);
        list2.add(dog);
        list2.add(cat);

        method1(animal);
        method1(dog);
        method1(cat);

        method2(animal);
        method2(dog);
        method2(cat);
    }

    public static void method1(Object object){}
    public static void method2(Animal animal){}

    /*
     인자/인수 : 함수로 들어가는 값 그 자체
     매개변수 : 함수로 들어오는 인자/값을 저장하는 메모리 공간
     리터럴 : 값 그자체
     변수 : 데이터를 저장할 수 있는 메모리 공간
     타입/자료형 : 값을 표현할 수 있는 형태/모양/형식
     다형성 : 타입변환
    */
}
