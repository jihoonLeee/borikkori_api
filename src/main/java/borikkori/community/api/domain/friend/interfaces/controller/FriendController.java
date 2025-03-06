package borikkori.community.api.domain.friend.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="friend_api", description = "FRIEND Apis")
@RequestMapping("friends")
@RestController
public class FriendController {

    // 친구 요청
    @PostMapping
    public ResponseEntity<Void> requestFriends(@RequestBody String req){

        return ResponseEntity.ok().build();
    }
    // 친구 요청 수락
    @PostMapping("/{requestId}/accept") // or PUT
    public ResponseEntity<Void> acceptFriendRequest(@RequestBody String req){

        return ResponseEntity.ok().build();
    }
    // 친구 요청 거절
    @PostMapping("/{requestId}/reject") // or PUT
    public ResponseEntity<Void> rejectFriendRequest(@RequestBody String req){

        return ResponseEntity.ok().build();
    }

    // 친구 신청 목록 (받은 사람)
    @GetMapping("/requests")
    public ResponseEntity<Void> getRequestList(@RequestBody String req){

        return ResponseEntity.ok().build();
    }
    
    // 친구 목록
    @GetMapping
    public ResponseEntity<Void> getMyFriendList(@RequestBody String req){

        return ResponseEntity.ok().build();
    }
    
    //친구 삭제
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@RequestBody String req){

        return ResponseEntity.ok().build();
    }
}
