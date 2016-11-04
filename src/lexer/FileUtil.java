package lexer;

import java.io.*;

/**
 * Created by phoebegl on 2016/10/21.
 * 处理输入输出
 */
public class FileUtil {

    private BufferedReader reader;
    private static BufferedWriter writer;
    private StringBuffer buffer;

    public FileUtil(String path) {
        try {
            reader = new BufferedReader(new FileReader(path));
            buffer = new StringBuffer();

            File file = new File("output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println("Can't find the file!");
        }
    }

    /**
     * 读取文件内容到缓存
     * @return
     */
    public StringBuffer read() {
        String temp = "";
        try {
            while((temp = reader.readLine()) != null) {
                buffer.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            System.out.println("Write failed!");
            e.printStackTrace();
        }
    }

    public static void finish() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
