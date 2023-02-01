package model_crawler.lehoi;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadDataTest {
    public static void main(String[] args) {

        //Tạo đối tượng Gson
        Gson gson = new Gson();

        //Chuyển đổi từ JSON sang mảng
        LeHoi[] lehois = null;
        try {
            FileReader reader = new FileReader("src/main/java/jsondata/LeHoi.json");
            lehois = gson.fromJson(reader, LeHoi[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (LeHoi p : lehois){
            System.out.println(p.getTenLeHoi());
        }
    }
}
