package model_crawler.diadiem;

import com.google.gson.Gson;
import model_crawler.nhanvat.NhanVat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class DiaDiem {
    protected String ten;
    protected String image;
    protected String viTri;
    protected  String quocGia;
    protected String diaChi;
    protected String thongTin;
    protected String khoiLap;
    protected String toaDo;
    protected String nguoiSangLap;
    protected String leHoi;
    protected ArrayList<String> links = new ArrayList<>();

    public DiaDiem() {
        this.viTri = "Khong ro";
        this.quocGia = "Khong ro";
        this.diaChi = "Khong ro";
        this.thongTin = "Khong ro";
        this.khoiLap = "Khong ro";
        this.toaDo = "Khong ro";
        this.nguoiSangLap = "Khong ro";
        this.leHoi = "Khong ro";
    }

    public static void getInfoFromNguoiKeSu(ArrayList<String> urls) throws IOException {
        // create Gson instance
        Gson gson = new Gson();

        // create a writer
        Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/jsondata/DiaDiem.json"));
        Element result;
        Elements results;
        for (String url : urls) {
            final Document doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(0)
                    .get();
            DiaDiem diaDiem = new DiaDiem();
            diaDiem.addLink(url);


            diaDiem.ten = Objects.requireNonNull(doc.selectFirst("div.page-header h2")).text();
            Element info = doc.selectFirst(".infobox");
            if (info != null) {
                for (Element row : info.select("tr")) {
                    result = doc.selectFirst(".jch-lazyload");
                    if (result != null) {
                        diaDiem.image = "https://nguoikesu.com" + result.attr("data-src");
                    }
                    if (row.selectFirst("th:contains(Vị trí)") != null) {
                        diaDiem.viTri = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Quốc gia)") != null) {
                        diaDiem.quocGia = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Địa chỉ)") != null) {
                        diaDiem.diaChi = row.selectFirst("td").text();
                    }
//                    if (row.selectFirst("th:contains(Thông tin)") != null) {
//                        diaDiem.thongTin = row.selectFirst("td").text();
//                    }
                    if (row.selectFirst("th:contains(Khởi lập)") != null) {
                        diaDiem.khoiLap = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Tọa độ)") != null) {
                        diaDiem.toaDo = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Người sáng lập)") != null) {
                        diaDiem.nguoiSangLap = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Lễ hội)") != null) {
                        diaDiem.leHoi = row.selectFirst("td").text();
                    }
                }
            }
            // convert user object to JSON file
            gson.toJson(diaDiem, writer);
        }
        // close writer
        writer.close();
    }
    protected void addLink(String link) {
        if (links.contains(link)) {
            return;
        }
        links.add(link);
    }
}
