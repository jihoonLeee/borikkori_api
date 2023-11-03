package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="friend_api", description = "FRIEND Apis")
@RequestMapping("friends")
@RestController
public class FriendController {
}
