/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qlnhahang;

import com.mycompany.conf.Utils;
import com.mycompany.pojo.KhachHang;
import com.mycompany.services.DatTiecServices;
import com.mycompany.services.KhachHangServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANHMINH
 */
public class FGiaoDienKHController implements Initializable {
    @FXML ImageView imgDatTiec;
    @FXML ImageView imgThongTinDatTiec;
    @FXML Button a1;
    private KhachHang k;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        KhachHangServices ks = new KhachHangServices();
        imgDatTiec.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            DatTiecServices ds = new DatTiecServices();
            try {
                if(ds.checkDatTiec(k.getMaKH()) == 0){
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("FDatTiec.fxml"));
                    try {
                        Parent d = loader.load();
                        Scene scene = new Scene(d);
                        FDatTiecController controller = loader.getController();
                        controller.LoadTabDatTiec(k);
                        stage.setScene(scene);
                        stage.setOnCloseRequest((eh)->{
                            if(controller.flag == true){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("X??c nh???n");
                                alert.setContentText("B???n ch??a thanh to??n!\nVui l??ng thanh to??n n???u kh??ng ti???c s??? b??? h???y!");
                                Optional<ButtonType> result = alert.showAndWait();
                                if(result.get() == ButtonType.OK){
                                    try {
                                        controller.huyTiec();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                else
                                    eh.consume();
                            }
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    Utils.getBox("Qu?? kh??ch ??ang c?? 1 ti???c ch??a t??? ch???c!\n"
                            + "Qu?? kh??ch kh??ng th??? ?????t th??m ti???c!\nMong qu?? kh??ch th??ng c???m", Alert.AlertType.INFORMATION).show();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imgThongTinDatTiec.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FTiecDaDat.fxml"));
            try {
                Parent d = loader.load();
                Scene scene = new Scene(d);
                FTiecDaDatController controller = loader.getController();
                controller.LoadTabDatTiec(k);
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FGiaoDienKHController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

    public KhachHang getK() {
        return k;
    }

    /**
     * @param k the k to set
     */
    public void setK(KhachHang k) {
        this.k = k;
    }
    
}

