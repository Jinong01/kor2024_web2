package korweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {

    // 프로젝트폴더명 -> build -> resources -> main -> static -> img
    // 경로 마지막에 \\ 넣어준다
    String uploadPath="C:\\Users\\User\\IdeaProjects\\kor2024_web2\\build\\resources\\main\\static\\img\\";

    // 1. 업로드 함수
    public String fileUpload(MultipartFile multipartFile) {
        // (1) 매개변수로 MultipartFile 타입 객체를 받는다. 클라이언트가 보낸 첨부파일이 들어있는 객체
        System.out.println(multipartFile.getOriginalFilename()); // 첨부파일의 파일명을 반환하는 함수
        System.out.println(multipartFile.getName()); // 첨부파일이 들어있는 속성명 반환하는 함수
        System.out.println(multipartFile.getSize()); // 첨부파일의 용량 반환하는 함수/바이트단위
        System.out.println(multipartFile.isEmpty()); // 첨부파일이 존재하는 여부 반환 함수

        // (*) 만약에 서로 다른 파일을 동일한 이름으로 업로드 했을때 파일명 식별 불가능
        // 방안 : 파일명 앞에 UUID 난수 텍스트 조합
        // UUID.randomUUID().toString(); : 난수로 UUID 규약의 텍스트 생성
            // 1. UUID 생성
        String uuid = UUID.randomUUID().toString();
        
        // (2) 업로드 경록와 파일명 조합하기, 업로드경로 + uuid + 파일명
            // 2. UUID 의 구분자는 '-' 하이픈 사용하므로 파일명에 하이픈이 존재하면 안된다.
            // -> 파일명에 '-'하이픈은 모두 '_'언더바로 변경
            // uuid-파일명 => uuid_파일명
        String fileName = uuid + "-" + multipartFile.getOriginalFilename().replaceAll("-","_");
        String uploadFile = uploadPath + fileName;
        // (3) 조합된 경로로 file 클래스 객체 만들기
        File file = new File(uploadFile);
        // (4) 업로드 하기 , transferTO()지정된경로, 예외발생
        try { multipartFile.transferTo(file);
        } catch (Exception e) {System.out.println("파일 업로드 실패 : " + e); return null;}
        return fileName; // 업로드 성공하면 파일명 반환
    }

    // 2. 다운로드 함수
}
