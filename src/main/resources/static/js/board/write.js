$(document).ready(function(){
    $('#summernote').summernote({
        height : 500, // 썸머노트 게시판의 높이 조절 속성
        lang : 'ko-KR', // 메뉴 한국어 설정, 기본 영어
        placeholder : '게시물 내용 입력해주세요' // 입력 전 가이드라인 제공 속성
    });
});

// [1] 게시물 등록 요청 함수
const onWrite = () => {
    // [1] 현재 html 의 DOM 객체 가져오기
    const cno = document.querySelector('.cno').value
    const btitle = document.querySelector('.btitle').value
    const bcontent = document.querySelector('.bcontent').value

    // [2] 객체화
    const obj = {cno : cno, btitle : btitle, bcontent : bcontent}

    fetch('/board/write.do',
        {method : 'POST',headers : {'Content-Type' : 'application/json'},body : JSON.stringify(obj)})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if(d==true){alert('글쓰기 성공');
        location.href = `/board?cno=${cno}`}
        else{alert('로그인 후 가능합니다')}
    })
    .catch(e => console.log(e))
}