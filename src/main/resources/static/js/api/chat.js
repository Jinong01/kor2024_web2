console.log('chat.js open')

// - 비회원제 익명 채팅-
// (1) 익명 아이디 만들어주기
    // Math.random() : 0~1 사이의 난수 생성 함수.
    // (Math.random() * 끝값) + 시작값 : 1부터 끝값 전까지 사이의 난수
let randomId = Math.floor(Math.random() * 1001) +1
let nickName = `익명${randomId}`;

// [1] 클라이언트 웹소켓
const clientSocket  = new WebSocket('ws://localhost:8080/socket/server')
console.log(clientSocket)

// [2] 클라이언트 웹소켓 속성
// 1. 만약에 클라이언트 웹소켓이 서버소켓과 연결을 성공 했을 때 실행되는 함수 구현
clientSocket.onopen = (event) => {
    console.log('서버소켓에 연동 성공')
    // (2) 클라이언트소켓이 서버소켓에 접속했을 때
    let msg = {'type' : 'alarm', 'message' : `${nickName}님이 입장 했습니다.`}
    // 소켓은 문자열만 전송이 가능하므로 JSON 이용한 문자열 타입
    clientSocket.send(JSON.stringify(msg))
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
    // (1) 받은 메시지 꺼내서 JSON 으로 타입 변환, JSON.parse("문자열") : 문자열 -> JSON
    const message = JSON.parse(event.data);
    // (2) 특정한 위치에 받은 메시지 출력하기
    const 채팅내용구역 = document.querySelector('.채팅내용구역')

    if (message.type == 'alarm'){ // 만약 메시지의 타입이 알람이면
        채팅내용구역.innerHTML += `<div class="alarm">
                                    <span>${message.message}</span>
                                 </div>`
    } else if(message.type == 'msg'){ // 만약 메시지의 타입이 메시지 이면
        if(message.from == nickName){ // 메시지의 보낸사람이 현재 nickName과 같다면 (내가 보낸 메시지)
            채팅내용구역.innerHTML += `<div class="secontent">
                                        <span class="date">${message.date}</span>
                                        <span class="content">${message.message}</span>
                                     </div>`
        } else{ // 남이 보낸 메시지
            채팅내용구역.innerHTML += `<div class="receiveBox">
                                        <div class="profileImg">
                                            <img src="/img/default.jpg"/>
                                        </div>
                                        <div>
                                            <div class="recontent">
                                                <div class="memberNic">${message.from}</div>
                                                    <span class="content">${message.message}</span>
                                                    <span>${message.date}</span>
                                                </div>
                                            </div>
                                     </div>`
        }
    }

    // + 메시지를 표시하고 만약에 스크롤이 메시지보다 위에 존재하면 스크롤은 최하단으로 내리기
        // scrollTop : 스크롤바의 상단 위치
        // scrollHeight : 스크롤의 전체 길이
        // scrollTop = scrollHeight : 상단 위치를 가장 하단으로 대입 한다는 뜻
    채팅내용구역.scrollTop = 채팅내용구역.scrollHeight;
}

// [3-2] 만약에 입력상자에서 엔터 키를 눌렀을때 메시지 전송
// onkeyup : 키보드 키를 누르고 떼었을때 이벤트
const enterKey = () => {
    // 만약에 엔터키를 눌렀을때
    if(window.event.keyCode==13){
        메시지전송();
    }
}

// [3] 메시지 서버에게 보내기
const 메시지전송 = () => {
    // 1. 입력받은 값을 가져온다.
    const 메시지작성구역 = document.querySelector('.메시지작성구역');
    const 메시지 = 메시지작성구역.value;
        // 만약에 메시지가 비어있으면 함수 강제 종료
        if(메시지 == ''){return;}

    // (3) 메시지를 구성한다.
    let msg = {type : 'msg' , message : 메시지, from : nickName, date : new Date().toLocaleString()}

    // 2. 클라이언트 웹소켓 객체의 .send() 함수 이용한 서버에게 메시시 전송
    clientSocket.send(JSON.stringify(msg));

    // 3. 전송 후 입력상자 초기화
    메시지작성구역.value = '';
}