package model_crawler.sukien;


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuKien {
    protected String tenSuKien;
    protected String thoiGian;
    protected String diaDiem;
    protected String nguyenNhan;
    protected String moTa;
    protected String ketQua;
    ArrayList<String> nhanVatLienQuan;

    public SuKien() {
        this.tenSuKien = "Chưa rõ";
        this.thoiGian = "Chưa rõ";
        this.diaDiem = "Chưa rõ";
        this.nguyenNhan = "Chưa rõ";
        this.moTa = "Chưa rõ";
        this.ketQua = "Chưa rõ";
        nhanVatLienQuan = new ArrayList<>();
    }

    public static void getInfoFromNguoiKeSu(ArrayList<String> urls) throws IOException {

        Gson gson = new Gson();

        Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/jsondata/NhanVat.json"));

        ArrayList<SuKien> danhsachsukien = new ArrayList<>();
        for (String url : urls) {
            try {
                SuKien motSuKien = new SuKien();
                Document doc = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .timeout(0)
                        .get();

                System.out.println("Crawling from: " + url);

                // Lấy tên của sự kiện
                Element firstPageHeader = doc.selectFirst("div[class=page-header]");
                if (firstPageHeader != null) {
                    firstPageHeader.select("sup").remove();
                    if (motSuKien.tenSuKien.equals("Chưa rõ")) motSuKien.tenSuKien = firstPageHeader.text();
                }

                // Tên từ thẻ đầu tiên trong infobox
                Element eventNameTag = doc
                        .selectFirst("#content > div.com-content-article.item-page > div.com-content-article__body > div.infobox > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td > span");
                if (eventNameTag != null) {
                    eventNameTag.select("sup").remove();
                    if (motSuKien.tenSuKien.equals("Chưa rõ"))
                        motSuKien.tenSuKien = eventNameTag.text();
                }

                Element infoBoxDiv = doc.selectFirst("div[class^=infobox]");
                if (infoBoxDiv != null) {
                    Elements aTagsInInfoBox = infoBoxDiv.select("a");
                    for (Element a : aTagsInInfoBox) {
                        String hrefValue = a.attr("href");
                        if (hrefValue.contains("/nhan-vat") && !hrefValue.contains("nha-")) {
                            String aTagValue = a.text();
                            if (!motSuKien.nhanVatLienQuan.contains(aTagValue)) {
                                motSuKien.nhanVatLienQuan.add(aTagValue);
                            }
                        }
                    }
                }

                Element infoTable = doc.selectFirst("table[cellpadding=0]");

                if (infoTable != null) {
                    Elements infoTableRows = infoTable.select("tr");
                    for (Element tr : infoTableRows) {
                        Elements tableDatas = tr.select("td");
                        if (tableDatas.size() > 1) {
                            tableDatas.get(0).select("sup").remove();
                            tableDatas.get(1).select("sup").remove();
                            String title = tableDatas.get(0).text();
                            if (title.equals("Thời gian") && motSuKien.thoiGian.equals("Chưa rõ")) {
                                motSuKien.thoiGian = tableDatas.get(1).text();
                            } else if (title.equals("Địa điểm") && motSuKien.diaDiem.equals("Chưa rõ")) {
                                motSuKien.diaDiem = tableDatas.get(1).text();
                            } else if (title.equals("Kết quả") && motSuKien.ketQua.equals("Chưa rõ")) {
                                motSuKien.ketQua = tableDatas.get(1).text();
                            } else if (title.contains("Nguyên nhân") && motSuKien.nguyenNhan.equals("Chưa rõ")) {
                                motSuKien.nguyenNhan = tableDatas.get(1).text();
                            }
                        }
                    }
                }

                Element contentBody = doc.selectFirst("div[class=com-content-article__body]");
                assert contentBody != null;
                Elements contentBodyELements = contentBody.children();
                boolean firstPFound = false;

                for (Element item : contentBodyELements) {
                    if (item.tagName().equals("p")) {
                        if (!firstPFound) {
                            firstPFound = true;
                            item.select("sup").remove(); // [class~=(annotation).*]
                            String firstPContent = item.text();

                            Element firstBTag = item.selectFirst("b");
                            if (firstBTag != null) {
                                String firstBTagContent = firstBTag.text();
                                String[] splitArray = firstBTagContent.split(",");
                                if (splitArray.length == 1) {
                                    if (motSuKien.tenSuKien.equals("Chưa rõ"))
                                        motSuKien.tenSuKien = splitArray[0].trim();
                                } else if (splitArray.length > 1) {
                                    if (motSuKien.tenSuKien.equals("Chưa rõ"))
                                        motSuKien.tenSuKien = splitArray[0].trim();
                                    if (motSuKien.thoiGian.equals("Chưa rõ")) motSuKien.thoiGian = splitArray[1].trim();
                                }
                            }

                            // Check mô tả của sự kiện
                            Pattern p = Pattern.compile("là[^.]*[.]");
                            Matcher m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (motSuKien.moTa.equals("Chưa rõ")) {
                                    motSuKien.moTa = findResult.substring(0, findResult.length() - 1);
                                }
                            }

                            // Check kết quả của sự kiện
                            p = Pattern.compile("(Kết quả|cuối cùng)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (motSuKien.ketQua.equals("Chưa rõ")) {
                                    motSuKien.ketQua = findResult.substring(0, findResult.length() - 1);
                                }
                            }

                            // Check thời gian diễ ra sự kiện
                            p = Pattern.compile("(xảy ra|diễn ra) (từ|vào)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (motSuKien.thoiGian.equals("Chưa rõ")) {
                                    motSuKien.thoiGian = findResult.substring(0, findResult.length() - 1);
                                }
                            }

                            // Check nguyên nhân của sự kiện
                            p = Pattern.compile("(nhằm|bắt nguồn|do)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (motSuKien.nguyenNhan.equals("Chưa rõ")) {
                                    motSuKien.nguyenNhan = findResult.substring(0, findResult.length() - 1);
                                }
                            }
                        }

                        Elements pATags = item.select("a");
                        for (Element a : pATags) {
                            String hrefValue = a.attr("href");
                            if (hrefValue.contains("/nhan-vat") && !hrefValue.contains("nha-")) {
                                String aTagValue = a.text();
                                if (!motSuKien.nhanVatLienQuan.contains(aTagValue)) {
                                    motSuKien.nhanVatLienQuan.add(aTagValue);
                                }
                            }
                        }
                    }
                }

                System.out.println("Name: " + motSuKien.tenSuKien);
                System.out.println("Date: " + motSuKien.thoiGian);
                System.out.println("Location: " + motSuKien.diaDiem);
                System.out.println("Result: " + motSuKien.ketQua);
                System.out.println("Reason: " + motSuKien.nguyenNhan);
                System.out.println("Description: " + motSuKien.moTa);
                System.out.print("Related characters: [");
                if (motSuKien.nhanVatLienQuan.size() > 0) {
                    for (int i = 0; i < motSuKien.nhanVatLienQuan.size(); ++i) {
                        if (i < motSuKien.nhanVatLienQuan.size() - 1) {
                            System.out.print(motSuKien.nhanVatLienQuan.get(i) + ", ");
                        } else System.out.print(motSuKien.nhanVatLienQuan.get(i) + "]\n");
                    }
                } else {
                    System.out.print("]\n");
                }
                danhsachsukien.add(motSuKien);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
