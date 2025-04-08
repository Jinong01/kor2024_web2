// js 가 열렸는지 확인
console.log('board.js open');

// 현재 URL 의 쿼리스트링 매개변수 가져오기

    // 현재 페이지의 URL 정보가 담긴 객체 생성
console.log(new URL(location.href));
    // 현재 페이지의 URL 쿼리스트링 정보 속성 반환
console.log(new URL(location.href).searchParams);
    // 현재 페이지의 URL 쿼리스트링 속성 중 cno 반환
console.log(new URL(location.href).searchParams.get('cno'));


// [1] 게시물 전체 조회 요청 함수
const findAll = () =>{
    // 1. 현재 페이지 URL 에서 매개변수 cno 값 구하기
    const cno = new URL(location.href).searchParams.get('cno')
    // 현재 페이지의 URL 에서 매개변수 page 값 구하기
    let page = new URL(location.href).searchParams.get('page');
    if(page == null) page = 1;
    // 1. 검색필드와 검색어
    let key = new URL(location.href).searchParams.get('.key')
    if (key == null) key=''
    let keyword = new URL(location.href).searchParams.get('.keyword')
    if (keyword == null) keyword=''
    // 2. fetch option
    const option = {method : 'GET'}
    // 3. fetch
    fetch(`/board/findall.do?cno=${cno}&page=${page}&key=${key}&keyWord=${keyword}`,option)
    .then(r => r.json())
    .then(d => {
        // 4. 요청 결과 응답 자료 확인
        console.log(d)
        // 5. html 를 출력할 구역 dom 가져오기
        const tbody = document.querySelector('tbody')
        // 6. 출력할 html 를 저장하는 변수 선언
        let html = ``
        // 7. 응답 자료를 반복문 이용하여 하나씩 순회해서 html 누적으로 더해주기
            // + 응답 자료에서 게시물 리스트 추출
            // data = {data : []}
        let boardList = d.data;
        boardList.forEach(board => {
            html += `<tr>
                        <td> ${board.bno}</td>
                        <td> <a href="/board/view?bno=${board.bno}"> ${board.btitle} </a> </td>
                        <td> ${board.mid}</td>
                        <td> ${board.bview}</td>
                        <td> ${board.cdate}</td>
                    </tr>`
                        
        });
        // 8. 반복문 종료후 html 변수에 누적된 <tr> 출력하기
        tbody.innerHTML = html;
        // 9. 게시물 출력 후 페이징 버튼 생성 함수 호출
        printPageNation(d, cno, key, keyword)
    })
    .catch(e => console.log(e))
}

findAll();

    // [2] 페이징버튼 생성하는 함수
    const printPageNation = (d,cno, key, keyword) => {
        let page = d.page; // 현재페이지
        let totalpage = d.totalpage;
        let startbtn = d.startbtn;
        let endbtn = d.endbtn;
        // (1) 어디에
        const pagebox = document.querySelector('.pagebox')
        // (2) 무엇을
        let html = ``
        // 이전 버튼, 현재 페이지에서 -1 차감한 페이지 이동, 만약에 0이면 1페이지로 고정
        html += `<li class="page-item"><a class="page-link" href="/board?cno=${cno}&page=${page <= 1? 1 : page-1}&key=${key}&keyWord=${keyword}">Previous</a></li>`
        // 페이징 버튼 , 반복문 이용하여 statbtn 부터 endbtn 까지 페이징버튼 만들기
        for( let index = startbtn ; index <= endbtn ; index++ ){
            // 만약에 현재 페이지와 버튼번호가 같다면 .active 부트스트랩 클래스 부여
            html += `<li class="page-item">
                        <a class="page-link ${ page == index ? 'active' : '' }" href="/board?cno=${ cno }&page=${ index }&key=${ key }&keyword=${ keyword }">
                            ${ index }
                        </a>
                     </li>`
            } // f end
        // 다음 버튼, 현재 페이지에서 +1 증가한 페이지 이동, 만약에 전체페이지수보다 크면 전체페이지 수로 고정
        html += `<li class="page-item"><a class="page-link" href="/board?cno=${cno}&page=${page >= totalpage? totalpage : page+1}&key=${key}&keyWord=${keyword}">Next</a></li>`
        // (3) 출력
        pagebox.innerHTML = html
    }

    // [3] 검색 버튼을 클릭 했을때 함수
    const onSearch = ( ) => {
        // 1. 선택한 검색필드 와 입력받은 검색어 가져오기
        const key = document.querySelector('.key').value
        const keyword = document.querySelector('.keyword').value
        // 2. 현재 카테고리 번호 를 URL 에서 가져오기  // 검색 기능 실행시 현재 카테고리를 유지 하기 위해
        const cno = new URL( location.href ).searchParams.get('cno');
        // 3. 입력받은 검색필드 와 입력받은 검색어로 이동한다. cno는 유지 , 검색후 결과는 1페이지 설정
        location.href = `board?cno=${ cno }&page=1&key=${ key }&keyWord=${ keyword }`
    }