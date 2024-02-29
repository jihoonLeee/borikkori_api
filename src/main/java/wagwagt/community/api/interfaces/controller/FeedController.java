package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="feed_api", description = "FEED Apis")
@RequestMapping("feeds")
@RestController
public class FeedController {
}
