package crawler;

import model_crawler.diadiem.DiaDiem;
import model_crawler.lehoi.LeHoi;
import model_crawler.nhanvat.NhanVat;
import model_crawler.sukien.SuKien;
import model_crawler.thoiky.ThoiKy;

import java.io.IOException;

public class CrawlerTester {
    public static void main(String[] args) throws IOException {
// Crawl dia diem
//        DiaDiemCrawler diadiemcrawler = new DiaDiemCrawler();
//        diadiemcrawler.crawlData();
//
// Crawl thoi ky
        ThoiKyCrawler thoikycrawler = new ThoiKyCrawler();
        thoikycrawler.crawlData();
//
//Crawl su kien
//        SuKienCrawler sukiencrawler = new SuKienCrawler();
//        sukiencrawler.crawlData();
//
//Crawl nhan vat
//        NhanVatCrawler nhanvatcrawler = new NhanVatCrawler();
//        nhanvatcrawler.crawlData();
//
//Crawl le hoi
//        LeHoiCrawler lehoicrawler = new LeHoiCrawler();
//        lehoicrawler.crawlData();
    }
}
