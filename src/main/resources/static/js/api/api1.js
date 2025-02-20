console.log('api.js open')

// [5] 공공데이터의 자료를 요청하여 chart.js API 로 정보 시각화 하기
const api4 = () => {
    // 1. 부평구 인구 현황 api
    const url = 'https://api.odcloud.kr/api/3044322/v1/uddi:466eee86-a8be-447b-9c8e-802bdbe897d7?page=1&perPage=22&serviceKey=mZ02O4J90TR/FS3g3XA9sqhB0pZWDwmVdH31wcP8hcm1z9lgN8/X4ReqX4nJUKVoiJ/vXmFBKNZLxhcYJQsMVw=='
    fetch(url)
    .then(r => r.json())
    .then(d => { console.log(d)
        // 데이터준비
        let 동별리스트 = []
        let 남자인구수 = []
        let 여자인구수 = []

        d.data.forEach(obj => {
            동별리스트.push(obj['동별'])
            남자인구수.push(obj['인구수(남)'])
            여자인구수.push(obj['인구수(여)'])
        })

        
        
        // 1. 차트를 표현할 위치에 DOM 을 가져온다
        const myChart2 = document.getElementById('myChart2');

      new Chart(myChart2, { // 차트객체 , new Chart(DOM객체,)
        type: 'bar', // type : 차트모양, bar(막대) line(선)
        data: {
          labels: 동별리스트, // labels : 동별
          datasets: [{ // 여러개의 항목 자료 {} 당 하나
            label: '남자인구수',
            data: 남자인구수,
            borderWidth: 1
          },
          {
            label : '남자인구수',
            data : 여자인구수,
            borderWidth : 1
          }]
        }
      });
    })
    .catch(e => console.log(e))

}
api4();

// [4] chart.js 샘플 코드
    // (1) 차트가 표현될 DOM 객체
    // const ctx = document.querySelector('#myChart');
const ctx = document.getElementById('myChart');

      new Chart(ctx, { // 차트객체 , new Chart(DOM객체,)
        type: 'bar', // type : 차트모양, bar(막대) line(선)
        data: {
          labels: ['1월', '2월', '3월', '4월', '5월', '6월'], // labels : 가로축 제목
          datasets: [{ // 여러개의 항목 자료 {} 당 하나
            label: '사이다판매량',
            data: [12, 19, 3, 5, 2, 3],
            borderWidth: 1
          },
          {
            label : '콜라판매량',
            data : [4,11,7,10,5,2],
            borderWidth : 1
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });



// [1] 부평구 인구 현황 요청 함수
const api1 = () => {
    // 1. 요청할 URL
    const url = 'https://api.odcloud.kr/api/3044322/v1/uddi:466eee86-a8be-447b-9c8e-802bdbe897d7?page=1&perPage=23&serviceKey=';
    // 2. 요청할 API 인증 KEY, 개별 발급
    const serviceKey = 'mZ02O4J90TR/FS3g3XA9sqhB0pZWDwmVdH31wcP8hcm1z9lgN8/X4ReqX4nJUKVoiJ/vXmFBKNZLxhcYJQsMVw==';
    // 3. fetch 이용한 API 통신
    fetch(url + serviceKey)
    // 처음부터 합쳐서 붙혀넣어도 된다
    .then(r => r.json())
    .then(responseData => {console.log(responseData);
        // (1) 출력할 DOM(HTML 를 객체 표현) 가져온다.
        const boardTable1 = document.querySelector('#boardTable1');
        // (2) 출력할 내용을 저장할 변수 선언
        let html =``;
        // (3) 출력할 자료를 반목문 이용하여 여러개 자료를 html 문법 표현한다.
        responseData.data.forEach((obj) => {
            // 객체 내 속성값을 호출하는 방법 : 객체변수명.속성명 vs 객체변수명['속성명']
            // 주의할점 : 특수문자가 있을 경우에는 ['속성명'] 으로 사용한다
            html += `<tr>
                        <td>${obj['동별']}</td>
                        <td>${obj['세대수']}</td>
                        <td>${obj['인구수(계)']}</td>
                        <td>${obj['인구수(남)']}</td>
                        <td>${obj['인구수(여)']}</td>
                     </tr>`
        })
        // (4) 출력할 DOM 에 생성한 html 대입하기
        boardTable1.innerHTML = html;
    })
    .catch(e => console.log(e))
}

api1();

// [2] 사업자 상태 조회 요청 함수
const api2 = () => {
    // 1. 입력받은 데이터 가져오기
    const 사업자번호입력상자 = document.querySelector('#사업자번호입력상자');
    const 사업자번호 = 사업자번호입력상자.value;

    // 2. 요청 자료 만들기 // // 입력 받은 사업자번호를 api 요청 형식에 맞게 구성
    const data = {"b_no" : [사업자번호]}; // 사업자번호에 - 있을 경우 불가능하므로 replace 함수 이용 .replaceAll('-','')
    // 3. url
    const url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=";
    const serviceKey = 'mZ02O4J90TR/FS3g3XA9sqhB0pZWDwmVdH31wcP8hcm1z9lgN8/X4ReqX4nJUKVoiJ/vXmFBKNZLxhcYJQsMVw==';

    // 4. fetch 를 이용한 api 요청
    const option = {method : 'POST', headers : {'Content-Type' : 'application/json'}, body : JSON.stringify(data)}
    fetch(url + serviceKey, option)
    .then(r => r.json())
    .then(d => {console.log(d)
        // 6. 만약에 요청 성공시 응답 자료의 결과를 HTML 에 출력하기
        const 결과구역 = document.querySelector('#결과구역');
        let html = d.data[0].tax_type
        결과구역.innerHTML = html
    })
    .catch(e => console.log(e))
}

// [3] 부평구 맛집 api
const api3 = () => {
    const url = 'https://api.odcloud.kr/api/15103411/v1/uddi:0875260e-d807-49b7-85fe-adb00bfa76ce?page=1&perPage=23&serviceKey='
    const serviceKey = 'mZ02O4J90TR%2FFS3g3XA9sqhB0pZWDwmVdH31wcP8hcm1z9lgN8%2FX4ReqX4nJUKVoiJ%2FvXmFBKNZLxhcYJQsMVw%3D%3D'

    fetch(url+serviceKey)
    .then(r => r.json())
    .then(d => {console.log(d)
        let boardTable2 = document.querySelector('#boardTable2')
        let html = ``
        d.data.forEach(data => {
            html += `<tr>
                        <td>${data.소재지}</td>
                        <td>${data['업 소 명']}</td>
                        <td>${data.업태}</td>
                        <td>${data.전화번호}</td>
                        <td>${data.지정메뉴}</td>
                     </tr>`
        })
        boardTable2.innerHTML = html;
    })
    .catch(console.log(e))
}
api3();
