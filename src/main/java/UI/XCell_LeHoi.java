package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.lehoi.LeHoi;

import java.util.ArrayList;

public class XCell_LeHoi extends XCell {
    public XCell_LeHoi() {
        super();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<LeHoi> listLeHoi = ReadDataFromJson.readLeHoiData();
                LeHoi leHoiInfo = new LeHoi();
                for (LeHoi leHoi : listLeHoi) {
                    if (leHoi.getTenLeHoi().equals(lastItem)) {
                        leHoiInfo = leHoi;
                        break;
                    } else {
                        leHoiInfo.setTenLeHoi(lastItem);
                    }
                }

                //New window
                Text ngayBatDau = new Text(leHoiInfo.getNgayBatDau());
                Text viTri = new Text(leHoiInfo.getViTri());
                Text tenLeHoi = new Text(leHoiInfo.getTenLeHoi());
                Text lanDauToChucText = new Text("Không rõ");
                Button lanDauToChucBtn = new Button(leHoiInfo.getLanDauToChuc());
                lanDauToChucBtn.setOnAction(NewWindowThoiKyInfo.thoiKyInfoWindow(leHoiInfo.getLanDauToChuc()));
                ArrayList<String> nhanVatLienQuan = leHoiInfo.getNhanVatLienQuan();
                Text ghiChu = new Text(leHoiInfo.getGhiChu());
                //Create gridpand
                GridPane gridPane = new GridPane();
                //Setting the vertical and horizontal gaps between the columns
                gridPane.setVgap(5);
                gridPane.setHgap(5);
                //Setting the padding
                gridPane.setPadding(new Insets(10, 10, 10, 10));
                gridPane.add(new Text("Tên lễ hội: "),0,0);
                gridPane.add(tenLeHoi,1,0);
                gridPane.add(new Text("Ngày bắt đầu: "),0,1);
                gridPane.add(ngayBatDau,1,1);
                gridPane.add(new Text("Vị trí: "),0,2);
                gridPane.add(viTri,1,2);
                gridPane.add(new Text("Lần đầu tổ chức: "),0,3);
                if(leHoiInfo.getLanDauToChuc().equals("Không rõ")){
                    gridPane.add(lanDauToChucText, 1,3);
                }else {
                    gridPane.add(lanDauToChucBtn,1,3);
                }
                gridPane.add(new Text("Nhân vật liên quan: "),0,4);
                if(nhanVatLienQuan.size() == 0){
                    gridPane.add(new Text("Không rõ"),1,4);
                }else {
                    int i = 0;
                    GridPane gridPaneNhanVatLienQuan = new GridPane();
                    for (String nhanVat : nhanVatLienQuan) {
                        Button nhanVatButton = new Button(nhanVat);
                        nhanVatButton.setOnAction(NewWindowNhanVatInfo.nhanVatInfoWindow(nhanVat));
                        gridPaneNhanVatLienQuan.add(nhanVatButton,0,i);
                        i++;
                    }
                    gridPane.add(gridPaneNhanVatLienQuan, 1, 4);
                }
                gridPane.add(new Text("Ghi chú: "),0,5);
                gridPane.add(ghiChu,1,5);

                StackPane leHoiInfoWindow = new StackPane();
                leHoiInfoWindow.getChildren().add(gridPane);
                Scene leHoiInfoScene = new Scene(leHoiInfoWindow);
                Stage newWindow = new Stage();
                newWindow.setTitle("Thông tin lễ hội");
                newWindow.setScene(leHoiInfoScene);
                newWindow.show();
            }
        });
    }
}
