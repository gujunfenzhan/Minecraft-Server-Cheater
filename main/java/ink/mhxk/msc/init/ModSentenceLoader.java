package ink.mhxk.msc.init;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creative by GoldMain on 2019/11/29
 */
public class ModSentenceLoader {
    public static List<String> sentences = new ArrayList<String>();
    public ModSentenceLoader(){
        try {
            init();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
    public void init()throws IOException{
        saveDefaultConfig();
        File file = new File("config/sentence.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        while((str = br.readLine())!=null){
            sentences.add(str);
        }
        br.close();
    }
    public void saveDefaultConfig()throws IOException{
        File file = new File("config/sentence.txt");
        if(file.exists())return;
        file.createNewFile();
        OutputStream os = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        InputStream is = ModSentenceLoader.class.getResourceAsStream("/assets/msc/sentence/sentence.txt");
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] b = new byte[1024];
        int len;
        while((len = bis.read(b))!=-1){
            bos.write(b,0,len);
            bos.flush();
        }
        bis.close();
        is.close();
        bos.close();
        os.close();
    }
}
