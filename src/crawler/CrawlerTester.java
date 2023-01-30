package crawler;

import model_crawler.nhanvat.NhanVat;

import java.io.IOException;

public class CrawlerTester {
    public static void main(String[] args) throws IOException {
        NhanVatCrawler.getLinksFromNguoiKeSu();

        NhanVat.getInfoFromNguoiKeSu(NhanVatCrawler.detailedLinks);
    }
}
