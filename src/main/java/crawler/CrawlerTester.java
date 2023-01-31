package crawler;

import model_crawler.lehoi.LeHoi;
import model_crawler.nhanvat.NhanVat;

import java.io.IOException;

public class  CrawlerTester {
    public static void main(String[] args) throws IOException {
        NhanVatCrawler.getLinksFromNguoiKeSu();

        NhanVat.getInfoFromNguoiKeSu(NhanVatCrawler.detailedLinks);
        LeHoi.getInfoLeHoiFromWikipedia("https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i");
    }
}
