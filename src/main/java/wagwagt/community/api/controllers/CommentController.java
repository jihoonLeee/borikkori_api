package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="comment_api", description = "COMMENT Apis")
@RequestMapping("comments")
@RestController
public class CommentController {
}
