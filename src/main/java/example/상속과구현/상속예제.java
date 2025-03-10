package example.상속과구현;

class Car{
    Tire tire; // Tire 타입으로 멤버변수 생성
    public void run(){this.tire.roll();} // Tire 타입의 roll 메소드 사용
}

class Tire{
    public void roll(){
        System.out.println("일반 타이어가 회전합니다.");
    };
}

class KumhoTire extends Tire { //  금호타이어는 일반타이어에게 상속 받는다
    @Override // extends 키워드 Tire 타입에게 물려받은 메소드를 재정의 : 오버라이딩
    public void roll(){
        System.out.println("금호타이어가 회전합니다.");
    }
}

class HankookTire extends Tire{
    @Override
    public void roll(){
        System.out.println("한국타이어가 회전합니다.");
    }
}

public class 상속예제 {
    public static void main(String[] args) {
        // [1]
        Car myCar = new Car();
        myCar.run();
        // 객체가 없어서 오류뜸
        // Car 객체는 존재하지만 Car 객체 안에 Tire 객체가 존재하지않으므로 tire.roll()은 실행되지 않는다.

        // [2]
        Car yourCar = new Car();
        yourCar.tire = new Tire();
        yourCar.run();
        // 일반 타이어가 회전합니다.
        // Car 객체안에 Tire 객체를 생성해서

        // [3]
        myCar.tire = new Tire();
        myCar.run();
        // 일반 타이어가 회전합니다.
        // 2번과 동일

        // [4]
        myCar.tire = new KumhoTire();
        myCar.run();
        // 금호타이어가 회전합니다.
        // myCar 객체 안에 KumhoTire 객체를 대입해서

        // [5]
        myCar.tire = new HankookTire();
        myCar.run();
        // 한국타이어가 회전합니다.
        // myCar 객체안의 Tire 변수에 HankookTire 로 새로 대입해서

        // [6]
        yourCar.run();
        // 일반 타이어가 회전합니다.
        // 2번에 yourCar 에 Tire 객체를 생성해둔 그대로 이므로


        // [즉]
        // 1. 메소드를 호출하기 위해서는 인스턴스(new)가 필요하다.
        // 2. Tire 타입에는 KumhoTire 와 HankookTire 가 대입된다. 용어 : 다형성 특징
    }
}
