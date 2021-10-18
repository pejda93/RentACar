import com.example.demo.dao.CarDaoSQL;
import com.example.demo.db.DatabaseConnection;
import com.example.demo.model.CarModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.request.CarSearchModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Main {
    private static final Connection conn = DatabaseConnection.getConnection();

    public static void main(String[] args) {

//        try {
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user_id = '" + "3966b84d-015f-406d-9279-a12f5c9a30f4" +"' ");
//
//
//
//            if (rs.next()) {
//                String s = rs.getString(9);
//                System.out.println(s.equals("null"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        UserModel um = new UserModel();
//
//        System.out.println(um.isAdmin());

//        int n = Integer.parseInt(null);
//        System.out.println(n);


        CarDaoSQL car2 = new CarDaoSQL();
        CarSearchModel car = new CarSearchModel("audi");
        LocalDate date1 = LocalDate.parse("2021-10-01");
        LocalDate date2 = LocalDate.parse("2021-10-02");

        List<CarModel> list = car2.getAllAvailableCars(date1,date2);

        List<CarModel> containingList = car2.getAllAvailableCars(date1,date2,car);

        for (CarModel cm :
                list) {
            if(cm.getYear() >= car.getYear() && car.getYear() != 0){
                containingList.add(cm);
                continue;
            }

            if (car.getMake() != null && cm.getMake().toLowerCase().contains(car.getMake().toLowerCase())) {
                containingList.add(cm);
                continue;
            }

            if (car.getModel() != null && cm.getModel().toLowerCase().contains(car.getModel().toLowerCase())) {
                containingList.add(cm);
                continue;
            }

            if (cm.getPrice() >= car.getPrice() && car.getPrice() != 0.0) {
                containingList.add(cm);
                continue;
            }

            if (cm.getPower() >= car.getPower() && car.getPower() != 0) {
                containingList.add(cm);
                continue;
            }

            if (cm.getDoors() >= car.getDoors() && car.getDoors() != 0) {
                containingList.add(cm);
                continue;
            }

            if (Boolean.parseBoolean(car.getAutomatic()) && cm.isAutomatic()) {
                containingList.add(cm);
            }

        }
        System.out.println(car.getModel());

        for (CarModel cm :
                containingList) {
            System.out.println(cm);
        }





    }
}
