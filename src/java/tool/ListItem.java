package tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuyq on 2020/9/8.
 */

public class ListItem {

    final static String MAIN_PATH = "/Users/momo/FLOW/SRCB_POC";
    final static String OUT_PATH = MAIN_PATH + "/output";
    final static String OUT_FILE_NAME = "batcher.txt";

    final static String FSA_PREFIX = "fsaAnalyze ";
    final static String TFA_PREFIX = "tfaAnalyze ";

    final static String FSA_SUFFIX = " 2016-12,2019-12";
    final static String TFA_SUFFIX = " 2019-01-01 2019-12-31";

    static BufferedWriter out = null;

    public static void main(String[] args) {
        try {
            File mainDir = new File(MAIN_PATH);
            if (mainDir.exists()) {
                initOutWriter();
                System.out.println("找到目录：" + MAIN_PATH);
                int count = 0;
                for (File f : mainDir.listFiles()) {
                    if (f.isDirectory()) {
                        System.out.println("找到子目录" + ++count + "：" + f.getName());
                        appendToFile(f.getName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private static void initOutWriter() throws IOException {
        File outPath = new File(OUT_PATH);
        if (!outPath.exists())
            outPath.mkdir();
        out = new
                BufferedWriter(new FileWriter(OUT_PATH + File.separator + OUT_FILE_NAME));
        out.write("#" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(new Date()) + " - Output writer init.\n");
    }

    private static void appendToFile(String str) throws IOException {
        try {
            String fsaCommand = FSA_PREFIX + str + FSA_SUFFIX;
            String tfaCommand = TFA_PREFIX + str + TFA_SUFFIX;
            out.write(fsaCommand + "\n");
            out.write(tfaCommand + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
