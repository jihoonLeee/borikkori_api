package wagwagt.community.api.common.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomCodeGenerator {

    public static int generate(){
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

}
