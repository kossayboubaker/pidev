package tn.esprit.pidev_desktop.test;

import tn.esprit.pidev_desktop.utils.MyDatabase;

public class Main {
    public static void main(String[] args) {

        // instance de base de données
        MyDatabase db =  MyDatabase.getInstance();
        MyDatabase db1 =  MyDatabase.getInstance();
        System.out.println(db);

    }


}
