package model_crawler.nhanvat;

import com.google.gson.Gson;
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

public class NhanVat {
    protected String ten;
    protected String tenKhac;
    protected String thoiKy;
    protected String sinh;
    protected String thanPhu;
    protected String thanMau;
    protected String mat;
    protected String anTang;
    protected String chucVu;
    protected String phoiNgau;
    protected String tonGiao;
    protected String diaChi;
    protected String nguyenNhanMat;
    protected String queQuan;
    protected String dangPhai;
    protected String danToc;
    protected String hocVan;
    protected String image;
    protected ArrayList<String> vo;
    protected ArrayList<String> hauDue;
    protected ArrayList<String> links = new ArrayList<>();

    public NhanVat() {
        this.ten = "Không rõ";
        this.tenKhac = "Không rõ";
        this.sinh = "Không rõ";
        this.thanPhu = "Không rõ";
        this.thanMau = "Không rõ";
        this.mat = "Không rõ";
        this.anTang = "Không rõ";
        this.chucVu = "Không rõ";
        this.nguyenNhanMat = "Không rõ";
        this.queQuan = "Không rõ";
        this.dangPhai = "Không rõ";
        this.danToc = "Không rõ";
        this.hocVan = "Không rõ";
        this.image = "Không rõ";
        this.diaChi = "Không rõ";
        this.phoiNgau = "Không rõ";
        this.tonGiao = "Không rõ";
        this.thoiKy = "Không rõ";
    }

    public static void getOneLink(String url) throws IOException {
        System.out.println(url);
        final Document doc = Jsoup.connect(url)
                .ignoreContentType(true)
                .timeout(0)
                .get();
        NhanVat nhanVat = new NhanVat();

        Element info = doc.selectFirst(".infobox");
        if (info != null) {
            for (Element row : info.select("tr")) {
                if (row.selectFirst("th:contains(Triều Đại)") != null || row.selectFirst("th:contains(Thời kỳ)") != null || row.selectFirst("th:contains(Hoàng tộc)") != null || row.selectFirst("th:contains(Kỷ nguyên)") != null) {
                    if (row.selectFirst("td") == null) {
                        nhanVat.thoiKy = row.nextElementSibling().text();
                    } else {
                        nhanVat.thoiKy = row.selectFirst("td").text();
                    }
                }
            }
        }

        Element result = doc.selectFirst(".jch-lazyload");
        if (result != null) {
            nhanVat.image = "https://nguoikesu.com" + result.attr("data-src");
        }
        System.out.println(nhanVat.image);
        Gson gson = new Gson();
        String res = gson.toJson(nhanVat);

//        System.out.println(res);
    }

    public static void getInfoFromNguoiKeSu(ArrayList<String> urls) throws IOException {
        // create Gson instance
        Gson gson = new Gson();

        // create a writer
        Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/jsondata/NhanVat.json"));
        ArrayList<NhanVat> danhsachnhanvat = new ArrayList<>();
        Element result;
        Elements results;
        for (String url : urls) {
            final Document doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(0)
                    .get();
            NhanVat nhanVat = new NhanVat();
            nhanVat.addLink(url);


            nhanVat.ten = Objects.requireNonNull(doc.selectFirst("div.page-header h2")).text();
            Element info = doc.selectFirst(".infobox");
            if (info != null) {
                for (Element row : info.select("tr")) {
                    if (row.selectFirst("th:contains(Triều Đại)") != null || row.selectFirst("th:contains(Thời kỳ)") != null || row.selectFirst("th:contains(Hoàng tộc)") != null || row.selectFirst("th:contains(Kỷ nguyên)") != null) {
                        if (row.selectFirst("td") == null) {
                            nhanVat.thoiKy = row.nextElementSibling().text();
                        } else {
                            nhanVat.thoiKy = row.selectFirst("td").text();
                        }
                    }
                    result = doc.selectFirst(".jch-lazyload");
                    if (result != null) {
                        nhanVat.image = "https://nguoikesu.com" + result.attr("data-src");
                    }
                    if (row.selectFirst("th:contains(Chức vụ)") != null || row.selectFirst("th:contains(Nghề nghiệp)") != null || row.selectFirst("th:contains(Công việc)") != null) {
                        if (row.selectFirst("td") == null) {
                            nhanVat.chucVu = row.nextElementSibling().text();
                        } else {
                            nhanVat.chucVu = row.selectFirst("td").text();
                        }
                    }
                    if (row.selectFirst("th:contains(Tên đầy đủ)") != null || row.selectFirst("th:contains(Tên khác)") != null || row.selectFirst("th:contains(Tự)") != null || row.selectFirst("th:contains(Tên khai sinh)") != null || row.selectFirst("th:contains(Biệt danh)") != null || row.selectFirst("th:contains(Tên thật)") != null) {
                        if (row.selectFirst("td") == null) {
                            nhanVat.tenKhac = row.nextElementSibling().text();
                        } else {
                            nhanVat.tenKhac = row.selectFirst("td").text();
                        }
                    }
                    if (row.selectFirst("th:contains(Sinh)") != null) {
                        nhanVat.sinh = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Nguyên nhân mất)") != null) {
                        nhanVat.nguyenNhanMat = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Quê quán)") != null) {
                        nhanVat.queQuan = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Đảng phái)") != null) {
                        nhanVat.dangPhai = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Nơi ở)") != null) {
                        nhanVat.diaChi = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Dân tộc)") != null) {
                        nhanVat.danToc = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Học vấn)") != null) {
                        nhanVat.hocVan = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Sinh)") != null) {
                        nhanVat.sinh = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Mất)") != null) {
                        nhanVat.mat = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(An táng)") != null || row.selectFirst("th:contains(Nơi an nghỉ)") != null) {
                        nhanVat.anTang = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Thân phụ)") != null || row.selectFirst("th:contains(Cha mẹ)") != null) {
                        nhanVat.thanPhu = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Thân mẫu)") != null) {
                        nhanVat.thanMau = row.selectFirst("td").text();
                    }
                    if ((row.selectFirst("th:contains(Phối ngẫu)") != null || row.selectFirst("th:contains(Phu quân)") != null) && row.selectFirst("td") != null) {
                        nhanVat.phoiNgau = row.selectFirst("td").text();
                    }
                    if (row.selectFirst("th:contains(Hậu phi)") != null || row.selectFirst("th:contains(Thê thiếp)") != null || row.selectFirst("th:contains(Vợ)") != null || row.selectFirst("th:contains(Hoàng hậu)") != null || row.selectFirst("th:contains(Phi tần)") != null) {
                        results = row.select("li");
                        if (results.size() == 0 && row.selectFirst("td") != null) {
                            nhanVat.addVo(row.selectFirst("td").text());
                        } else {
                            for (Element vo : results) {
                                nhanVat.addVo(vo.text());
                            }
                        }
                    }
                    if (row.selectFirst("th:contains(Hậu duệ)") != null || row.selectFirst("th:contains(Con cái)") != null) {
                        results = row.select("li");
                        if (results.size() == 0 && row.selectFirst("td") != null) {
                            nhanVat.addHauDue(row.selectFirst("td").text());
                        } else {
                            for (Element hauDue : results) {
                                nhanVat.addHauDue(hauDue.text());
                            }
                        }
                    }
                }
            }
            danhsachnhanvat.add(nhanVat);
        }
        gson.toJson(danhsachnhanvat, writer);
        writer.close();
    }

    protected void addLink(String link) {
        if (links.contains(link)) {
            return;
        }
        links.add(link);
    }

    protected void addVo(String item) {
        if (vo == null) {
            vo = new ArrayList<>();
        }
        if (vo.contains(item)) {
            return;
        }
        vo.add(item);
    }

    protected void addHauDue(String item) {
        if (hauDue == null) {
            hauDue = new ArrayList<>();
        }
        if (hauDue.contains(item)) {
            return;
        }
        hauDue.add(item);
    }
}
