package com.jibi.backendjibi.shared;

import java.util.Random;

public class GenerateRib {
    private static final String rib="0123456789098765432";
    public static StringBuilder generate(){
        Random random=new Random();
        StringBuilder ribBuilder=new StringBuilder();
        for(int i=0;i<rib.length();i++){
            ribBuilder.append(rib.charAt(random.nextInt(rib.length())));
        }
        return ribBuilder;
    }
}

