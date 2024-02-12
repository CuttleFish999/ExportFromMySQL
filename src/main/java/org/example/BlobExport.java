package org.example;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BlobExport {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/photoalbum";
        String user = "root";
        String password = "Cuttle00";

        String query = "SELECT proid, proimage FROM product";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet result = pst.executeQuery()) {

            while (result.next()) {
                String id = result.getString("proid");
                InputStream is = result.getBinaryStream("proimage");
                FileOutputStream fos = new FileOutputStream("image_" + id + ".jpg");

                byte[] buffer = new byte[1024];
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }

                fos.close();
                is.close();

                System.out.println("导出图片 ID: " + id);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
