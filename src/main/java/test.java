import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] s){
        try {
            FileUtils.writeStringToFile(new File("a.txt"),"sadfsafd","utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
