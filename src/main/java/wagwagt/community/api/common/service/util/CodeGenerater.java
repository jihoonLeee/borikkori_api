package wagwagt.community.api.common.service.util;

import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerater {

    public static int generate(){
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

}
