package com.jibi.backendjibi.shared;

import java.util.Random;

public class GeneratePassword {
    private static final String password="ABCJKLab12345";
    public static StringBuilder generate(){
        Random random = new Random();
        StringBuilder pas=new StringBuilder();
        for(int i=0;i<password.length();i++){
            pas.append(password.charAt(random.nextInt(password.length())));
        }
        return pas;
    }

}
