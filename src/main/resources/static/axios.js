// [1] 비동기 함수란?
const test1 = () => {
    console.log('test1'); // (1) 첫번째 콘솔
    const result = axios.get('/students')
    console.log(result) // (2) 두번째 콘솔
    console.log('test2'); // (3) 세번째 콘솔
    // 실행순서 : 첫번째 콘솔 --> 두번째 콘솔(undefined) --> 세번째 콘솔
    // 즉] JS 흐름이 AXIOS 를 이용하여 요청을 보내고 응답을 기다리지 않고 두번째 콘솔을 출력했으므로 undefined
}

test1()

// [2] 동기 함수란? 1. 함수 내 매개변수() 앞에 'async' 키워드 붙힌다. 2. axios/fetch 앞에 'await' 키워드 붙힌다
const test2 = () =>{
    console.log('(2)test1') // 첫번째 콘솔
    const response = axios.get('/students')
    console.log(response.data) // 두번째 콘솔
    console.log('(2)test2') // 세번째 콘솔
    // 실행순서 : 첫번째 --> 두번째 --> 세번째
    // 즉] JS 흐름이 AXIOS 를 이용하여 요청을 보내고 응답이 올때까지 기다린다. 응답이 오면 다음코드를 실행한다.
}

test2()

// [4] 학생점수 전체 조회 (동기화)
const onFindAll = async () => {
    // fetch 대신 axios 사용
    // (1) AXIOS 이용한 SPRING controller 매핑
    const response = await axios.get('/students')
    // (2) 응답 본문 꺼내기
    const data = response.data
    // (3) 반복문 이용하여 HTML 출력한다
        // 어디에
        const tbody = document.querySelector('tbody')
        // 무엇을
        let html = ``
        data.forEach((student)=>{
            html += `<tr>
                        <td>${student.sno}</td>
                        <td>${student.name}</td>
                        <td>${student.kor}</td>
                        <td>${student.math}</td>
                    </tr>`
        })
        // 출력
        tbody.innerHTML = html
}
onFindAll()
// [3] 학생 점수 등록 버튼을 클릭 했을때
const onSave = async () => {
    // 1. 입력받은 값 가져오기
    const name = document.querySelector('.name').value
    const kor = document.querySelector('.kor').value
    const math = document.querySelector('.math').value

    // 2. 객체화
        // const obj = {name:name,kor:kor,math:math}
    // JS 에서는 객체 속성명과 대입변수 명이 같다면 생략가능
    const obj = {name, kor, math}
    // [선택1] fetch
//    const option = {
//        method : 'POST',
//        headers : {'Content-Type' : 'application/json'},
//        body : JSON.stringify(obj)
//        }
//        fetch('/students', option)
//        .then(r => r.json())
//        .then(d => console.log(d))
//        .catch(e => console.log(e))

    // [선택2] axios + 동기
    const response = await axios.post('/students', obj) // then() 함수 대신에 변수에 리턴받은 형식으로 무조건 동기화
    console.log(response.data) // response 응답(정보)객체, response.data 응답 본문내용
}