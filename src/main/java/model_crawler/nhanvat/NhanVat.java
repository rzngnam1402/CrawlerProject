package model_crawler.nhanvat;

import crawler.NhanVatCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        this.thoiKy = "Không rõ";
    }

    public static void getInfoFromNguoiKeSu(ArrayList<String> urls) throws IOException {
        final Document doc = Jsoup.connect(urls.get(1))
                .ignoreContentType(true)
                .timeout(0)
                .get();

        NhanVat nhanVat = new NhanVat();
        nhanVat.addLink(urls.get(1));

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
            }
        }
        System.out.println(nhanVat.thoiKy);
    }

    protected void addLink(String link) {
        if (links.contains(link)) {
            return;
        }
        links.add(link);
    }
}
