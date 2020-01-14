/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import dao.UserDao;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author liana
 */
public class AdminMenu {
     public static void adminMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("MAIN MENU:" + "\n" + "1.See all users" + "\n" + "2.Exit" + "\n");
        int choiceMenu = 0;
        do {
            try {
                choiceMenu = sc.nextInt();
                if ((choiceMenu < 1) || (choiceMenu > 2)) {
                    System.err.print("You must enter a number from 1 to 2");

                } else {
                    switch (choiceMenu) {
                        case 1:
                            UserDao.getAllUsers();
                            adminMenu();
                            break;
                        case 2:
                            Home.userLogin();
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again.");
                sc.next();
            }
        } while (sc.hasNext());
    }
    
}
