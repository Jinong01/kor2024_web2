package example.상속과구현;

interface Tire2{ // 일반타이어 인터페이스 선언
    void roll(); // 추상 메소드
}

class Car2 { // 자동차 클래스 선언
    Tire2 tire2; // 일반타이어 타입 멤버변수 선언
    public void roll(){tire2.roll();} // 일반타이어 타입으로 roll 메소드 호출
}

// extends 는 재정의 override , implements 는 구현 override
class HanKookTire2 implements Tire2{
    @Override // 구현에서의 오버라이드는 재정의가 아닌 구현
    public void roll(){
        System.out.println("한국타이어가 회전합니다.");
    }
}

class KumhoTire2 implements Tire2{
    @Override
    public void roll(){
        System.out.println("금호타이어가 회전합니다.");
    }
}

public class 구현예제 {
    public static void main(String[] args) {

        // [1]
        Car2 myCar = new Car2();
        myCar.roll(); // tire2 에 인스턴스가 존재하지 않으므로

        // [2]
        Car2 yourCar = new Car2();
        yourCar.tire2 = new HanKookTire2();
        // 추상메소드를 구현했으므로 HanKookTire2는 구현한 객체 = 구현체
        yourCar.roll();

        // [3]
        myCar.tire2 = new HanKookTire2();
        myCar.roll();

        // [4]
        myCar.tire2 = new KumhoTire2();
        myCar.roll();

        // [5]
        yourCar.roll();
    }
}
