package model_crawler.thoiky;

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

public class ThoiKy {
    protected String tenTrieuDai;
    protected String thoiKy;
    protected String queHuong;
    protected String kinhDo;
    protected String thoiGian;
    protected ArrayList<String> cacVua;

    public ThoiKy() {
        this.tenTrieuDai = "Chưa rõ";
        this.thoiKy = "Chưa rõ";
        this.queHuong = "Chưa rõ";
        this.kinhDo = "Chưa rõ";
        this.thoiGian = "Chưa rõ";
        this.cacVua = new ArrayList<>();
    }

    public static ThoiKy getInfoFromWiki(String url) {
        try {
            Gson gson = new Gson();

            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/jsondata/ThoiKy.json"));
            ThoiKy motThoiKy = new ThoiKy();

            final Document doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(0)
                    .get();


            Element contentDiv = doc.selectFirst("div[class=mw-parser-output]");
            assert contentDiv != null;
            Elements contentDivChild = contentDiv.children();

            Element table = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(91)");
            assert table != null;
            Elements rows = table.select("tr");

            for (int i = 0; i < contentDivChild.size(); ++i) {
                Element e = contentDivChild.get(i);
                if (e.tagName().equals("h2")) {
                    Element titleSpan = e.selectFirst("span[class=mw-headline]");

                    if (titleSpan != null) {
                        String titleSpanContent = titleSpan.text();

                        Pattern p = Pattern.compile("(Thời|Bắc).*", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(titleSpanContent);

                        if (m.find()) {
                            String mocThoiKy = "";
                            String nguoiSangLap = "Chưa rõ";
                            String kinhDo = "Chưa rõ";
                            String queHuong = "Chưa rõ";
                            String mocThoiGian = "Chưa rõ";

                            while (!contentDivChild.get(i + 1).tagName().equals("h2")) {
                                i++;
                                Element currentElement = contentDivChild.get(i);
                                if (currentElement.tagName().equals("h3")) {
                                    Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
                                    if (eraNameElement != null) {
                                        String eraNameEleContent = eraNameElement.text();
                                        int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();
                                        if (countParen == 0) {
                                            mocThoiKy = eraNameEleContent;
                                        } else if (countParen == 1) {
                                            int startParen = eraNameEleContent.indexOf("(");
                                            int endParen = eraNameEleContent.indexOf(")");
                                            mocThoiKy = eraNameEleContent.substring(0, startParen - 1).trim();
                                            mocThoiGian = eraNameEleContent.substring(startParen + 1, endParen).trim();
                                        } else if (countParen == 2) {
                                            int startParenFirst = eraNameEleContent.indexOf("(");
                                            int endParenFirst = eraNameEleContent.indexOf(")");
                                            int startParenSec = eraNameEleContent.lastIndexOf("(");
                                            int endParenSec = eraNameEleContent.lastIndexOf(")");
                                            mocThoiKy = eraNameEleContent.substring(0, startParenFirst - 1);
                                            String firstParenContent = eraNameEleContent.substring(startParenFirst + 1, endParenFirst);
                                            String secondParenContent = eraNameEleContent.substring(startParenSec + 1, endParenSec);
                                            String[] firstParenSplit = firstParenContent.split("–");
                                            String[] secParenSplit = secondParenContent.split("–");
                                            if (firstParenSplit.length < 2) {
                                                firstParenSplit = firstParenContent.split("-");
                                            }
                                            if (secParenSplit.length < 2) {
                                                secParenSplit = secondParenContent.split("-");
                                            }
                                            mocThoiGian = firstParenSplit[0].trim() + " - " + secParenSplit[1].trim();
                                        }
                                    }
                                } else if (currentElement.tagName().equals("table")) {
                                    if (
                                            currentElement.attr("cellpadding").equals("2") ||
                                                    (currentElement.hasClass("wikitable") &&
                                                            !currentElement.hasAttr("cellpadding"))
                                    ) {
                                        Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
                                        if (eraNameElement != null) {
                                            String eraNameEleContent = eraNameElement.text();
                                            int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();
                                            if (countParen == 0) {
                                                mocThoiKy = eraNameEleContent;
                                            } else if (countParen == 1) {
                                                int startParen = eraNameEleContent.indexOf("(");
                                                int endParen = eraNameEleContent.indexOf(")");
                                                mocThoiKy = eraNameEleContent.substring(0, startParen - 1).trim();
                                                mocThoiGian = eraNameEleContent.substring(startParen + 1, endParen).trim();
                                            } else if (countParen == 2) {
                                                int startParenFirst = eraNameEleContent.indexOf("(");
                                                int endParenFirst = eraNameEleContent.indexOf(")");
                                                int startParenSec = eraNameEleContent.lastIndexOf("(");
                                                int endParenSec = eraNameEleContent.lastIndexOf(")");
                                                mocThoiKy = eraNameEleContent.substring(0, startParenFirst - 1);
                                                String firstParenContent = eraNameEleContent.substring(startParenFirst + 1, endParenFirst);
                                                String secondParenContent = eraNameEleContent.substring(startParenSec + 1, endParenSec);
                                                String[] firstParenSplit = firstParenContent.split("–");
                                                String[] secParenSplit = secondParenContent.split("–");
                                                if (firstParenSplit.length < 2) {
                                                    firstParenSplit = firstParenContent.split("-");
                                                }
                                                if (secParenSplit.length < 2) {
                                                    secParenSplit = secondParenContent.split("-");
                                                }
                                                mocThoiGian = firstParenSplit[0].trim() + " - " + secParenSplit[1].trim();
                                            }
                                        }
                                    } else {
                                        Elements tableDatas = currentElement.select("tbody > tr > td:nth-child(2)");
                                        for (Element td : tableDatas) {
                                            td.select("sup").remove();
                                            Element tdATag = td.selectFirst("a");
                                            if (tdATag != null) {
                                                String tenVua = tdATag.text();
                                                motThoiKy.addVua(tenVua);
                                            }
                                        }

                                        for (int j = 1; j < rows.size(); ++j) {
                                            Element firstTd = rows.get(j).selectFirst("td");
                                            if (firstTd != null) {
                                                String eraValue = firstTd.text();
                                                if (!mocThoiKy.equals("")) {
                                                    if (eraValue.contains(mocThoiKy)) {
                                                        Elements currentRowTds = rows.get(j).select("td");
                                                        currentRowTds.get(1).select("sup").remove();
                                                        currentRowTds.get(2).select("sup").remove();
                                                        currentRowTds.get(3).select("sup").remove();
                                                        nguoiSangLap = currentRowTds.get(1).text();
                                                        queHuong = currentRowTds.get(2).text();
                                                        kinhDo = currentRowTds.get(3).text();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println("Belongs to timestamp: " + mocThoiKy);
                                        System.out.println("Hometown: " + queHuong);
                                        System.out.println("Founder: " + nguoiSangLap);
                                        System.out.println("Location: " + kinhDo);
                                        System.out.println("Time: " + mocThoiGian);


                                        motThoiKy.thoiKy = mocThoiKy;
                                        motThoiKy.queHuong = queHuong;
                                        motThoiKy.kinhDo = kinhDo;
                                        motThoiKy.thoiGian = mocThoiGian;

                                        return motThoiKy;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ThoiKy motThoiKy = getInfoFromWiki("https://vi.wikipedia.org/wiki/Vua_Việt_Nam");
        System.out.println(motThoiKy.tenTrieuDai);
    }

    protected void addVua(String vua) {
        if (cacVua.contains(vua)) {
            return;
        }
        cacVua.add(vua);
    }
}
