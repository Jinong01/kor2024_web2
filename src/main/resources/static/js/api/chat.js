console.log('chat.js open')

// [1] 클라이언트 웹소켓
const clientSocket  = new WebSocket('ws://localhost:8080/socket/server')
console.log(clientSocket)

// [2] 클라이언트 웹소켓 속성
// 1. 만약에 클라이언트 웹소켓이 서버소켓과 연결을 성공 했을 때 실행되는 함수 구현
clientSocket.onopen = (event) => {
    console.log('서버소켓에 연동 성공')
}
// 2. 만약에 클라이언트 웹소켓이 서버소켓과 연결이 닫혔을때 실행되는 함수 구현
clientSocket.onclose = (event) => {
    console.log('서버소켓과 연동이 닫혔다')
}
// 3. 만약에 클라이언트 웹소켓이 서버소켓과 에러가 발생했을 때 실행되는 함수 구현
clientSocket.onerror = (event) => {
    console.log('서버소켓과 에러가 발생했다')
}
// 4. 만약에 클라이언트 웹소켓으로 서버소켓이 메세지를 보냈을 때
clientSocket.onmessage = (event) => {
    console.log('서버소켓으로 부터 메세지를 받았다')
    // [4] 서버로 부터 클라이언트가 메시지를 받았을때
    console.log(event); // 받은 메시지 통신 정보 객체
    console.log(event.data); // 받은 메시지 본문
    // (1) 받은 메시지 꺼내기
    const message = event.data;
    // (2) 특정한 위치에 받은 메시지 출력하기
    const 채팅내용구역 = document.querySelector('.채팅내용구역')
    채팅내용구역.innerHTML += `<div>${message}</div>`
}

// [3] 메시지 서버에게 보내기
const 메시지전송 = () => {
    // 1. 입력받은 값을 가져온다.
    const 메시지작성구역 = document.querySelector('.메시지작성구역');
    const 메시지 = 메시지작성구역.value;

    // 2. 클라이언트 웹소켓 객체의 .send() 함수 이용한 서버에게 메시시 전송
    clientSocket.send(메시지);

    // 3. 전송 후 입력상자 초기화
    메시지작성구역.value = '';
}