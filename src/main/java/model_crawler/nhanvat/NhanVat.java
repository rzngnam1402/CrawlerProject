package model_crawler.nhanvat;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    protected String hocvan;
    protected String image;
    protected String moTa;
    protected ArrayList<String> vo;
    protected ArrayList<String> hauDue;
    protected ArrayList<String> nhanVatCungThoiKy;
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
        this.diaChi = "Không rõ";
        this.phoiNgau = "Không rõ";
        this.tonGiao = "Không rõ";
        this.nguyenNhanMat = "Không rõ";
        this.queQuan = "Không rõ";
        this.dangPhai = "Không rõ";
        this.danToc = "Không rõ";
        this.hocvan = "Không rõ";
        this.image = "Không rõ";
        this.moTa = "Không rõ";
        this.thoiKy = "Không rõ";

    }

    public static void getInfoFromNguoiKeSu(ArrayList<String> urls) throws IOException {
        final Document doc = Jsoup.connect(urls.get(1))
                .ignoreContentType(true)
                .timeout(0)
                .get();

        NhanVat nhanVat = new NhanVat();
        nhanVat.addLink(urls.get(1));

        Elements results;
        Element result;

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
                result = doc.selectFirst(".jch-lazyloaded.ls-is-cached");
                if (result != null) {
                    nhanVat.image = "https://nguoikesu.com" + result.attr("src");
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
                    nhanVat.hocvan = row.selectFirst("td").text();
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
                if (row.selectFirst("th:contains(Tôn giáo)") != null) {
                    nhanVat.tonGiao = row.selectFirst("td").text();
                }
                if ((row.selectFirst("th:contains(Phối ngẫu)") != null || row.selectFirst("th:contains(Phu quân)") != null) && row.selectFirst("td") != null) {
                    nhanVat.phoiNgau = row.selectFirst("td").text();
                }
            }
        }
        System.out.println(nhanVat.thoiKy);

        // chuyen doi Object sang json
        Gson gson = new Gson();
        String res = gson.toJson(nhanVat);

        System.out.println(res);

    }

    protected void addLink(String link) {
        if (links.contains(link)) {
            return;
        }
        links.add(link);
    }
}
