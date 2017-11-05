package wubingchen_utilty;

import java.io.FileWriter;
import java.io.IOException;
import  java.io.File;

public class Wbc_utility {
    //mode true表示追加方式 要求效率时候需要优化
    public static void wbc_write_file(String str,String filename,String appendstring,boolean mode) throws IOException
    {
        FileWriter fileWriter=new FileWriter(filename,mode);
        fileWriter.write(str+appendstring);
        fileWriter.close();
    }
    public static  void wbc_creat_directory(String str,String path)throws IOException
    {
        File dir=new File(path+"/"+str);
        if (!dir.exists())
        {
            if(!dir.mkdir())
            {
                System.out.println("creat failed wbc_creat_directory line:22");
            }
        }
    }
}
