package tn.esprit.test;

import tn.esprit.models.User;
import tn.esprit.services.UserService;

public class Main {

    public static void main(String[] args) {

        UserService us = new UserService();
        us.AjouterUser(new User("saleh","mhamdi","patati","patata","donc","six","sept",1));
    }
}
