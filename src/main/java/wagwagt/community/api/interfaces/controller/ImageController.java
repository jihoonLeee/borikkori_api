package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.entities.domain.Post;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.dto.requests.PostLikeRequest;
import wagwagt.community.api.interfaces.controller.dto.requests.PostWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.PostListResponse;
import wagwagt.community.api.interfaces.controller.dto.responses.PostResponse;
import wagwagt.community.api.usecase.PostUsecase;
import wagwagt.community.api.usecase.UserUsecase;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Tag(name="image_api", description = "IMAGE Apis")
@RequestMapping("image")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final PostUsecase postUsecase;
    private static final double MAX_SIZE = 10*(1024*1024); // 총 10MB
    @Operation(summary = "이미지 업로드", description = "이미지 업로드")
    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file) throws IOException {
        // 파일 처리 로직
        // 파일 정보 추출
        // 유효성 검사 -> 파일 크기, 파일 확장자, MIME타입
        // 서버or파일시스템에 저장 -> 새로운 랜덤 문자열 생성 해서 파일이름 새로 생성
        // 파일의 기존 정보 새로운 정보 , 저장 경로, 업로드 시간 등 DB저장
        // 성공하면 URL return

    
        System.out.println(file.getOriginalFilename()+" 파일 : " + file.getSize() + " ?? "+ file.getContentType());
//        File destination = new File("upload/images/" + file.getOriginalFilename());
//        file.transferTo(destination);


        return ResponseEntity.created(URI.create("/path/to/file")).build();
    }

    // 우선 로컬에 저장 -> S3에 저장

    // 글쓰기 페이지에 들어가면 Post를 바로 생성하고 -> Upload하면 -> Post와 Images 테이블에 저장 (상태 = TEMP)
    // Post : Images = 1 : N 양방향
    // 글 쓰기 들어가면 그 유저 정보로 Post조회 (TEMP)상태가 있으면 불러오거나 삭제(Delete) 선택 , 하루에 한번 TEMP 면 삭제
    // 삭제면 -> Images도 삭제 
    // 등록하면 Post  (상태 = POSTED) 로 변경

//{"file_size":23485,
// "file_srl":6790664104,
// "sid":"44920adeec3744571a1f97c6717f13df",
// "source_filename":"borikkori_brown_logo.png",
// "upload_target_srl":6790663264,
// "uploaded_filename":"./files/attach/new3/20240306/486616/5878280555/6790663264/3d5068a389970e0f8a458ccd2bc13fc3.png","error":0,"message":"success"}
}
