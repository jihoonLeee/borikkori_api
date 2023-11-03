package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="post_api", description = "POST Apis")
@RequestMapping("posts")
@RestController
public class PostController {
}
