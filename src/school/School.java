/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;


import dao.StudentDao;
import java.text.ParseException;
import menus.Home;

/**
 *
 * @author liana
 */
public class School {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
//        String dateBeforeString = "07 12 2019";
//        String dateAfterString = "10 01 2020";
////Liana change the format of the dateformat
//        try {
//            Date dateBefore = myFormat.parse(dateBeforeString);
//            Date dateAfter = myFormat.parse(dateAfterString);
//            long difference = dateAfter.getTime() - dateBefore.getTime();
//            float daysBetween = (difference / (1000 * 60 * 60 * 24));
//            /* You can also convert the milliseconds to days using this method
//                * float daysBetween = 
//                *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
//             */
//            System.out.println("Number of Days between dates: " + daysBetween);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



        Home.headmasterMenu();
                
    }
}
