# borikkori.server
borikkori 프로젝트 Backend 


## 요구사항 분석 
**기능 목록**
- 회원 기능 (연동로그인 시도해보기)
    - 회원 가입
        - 이메일 인증 (소셜로그인 추가 고려)
    - 회원 탈퇴
    - 로그인
    - 로그아웃
    - 비밀번호찾기
    - 비밀번호변경
- 게시판 기능(강아지 정보 공유, 자유게시판, 꿀팁 …)
    - 게시글 등록
    - 게시글 수정
    - 게시글 삭제
    - 게시글 조회
        - 필터(최신,인기,내가쓴,저장한…)
    - 게시글 좋아요
    - 게시글 댓글
- SNS 기능 (like instagram)
    - 피드 등록
        - 사진,동영상
    - 피드 삭제
    - 피드 수정
    - 피드 목록 조회
        - 필터(내 피드)
    - 피드 좋아요
    - 피드 댓글
- 친구 기능
    - 친구 신청
    - 친구 신청 수락
    - 친구 신청 거절
    - 친구 목록 조회
- 주변 동물 병원 조회(map api사용)
    - 위치 기반 5km내외 동물병원 조회
    - 동물병원 별점 및 댓글(리뷰)
- 기타 요구사항
    - 인기 게시글은 좋아요 10개 이상이다.
    - 최신 게시글은 48시간이 지나지 않은 게시글이다.



## Class Diagram, swagger 문서, DB설계 문서 넣기

README 를 잘 작성하자