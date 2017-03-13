package com.app;


import com.inter.Inter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Maksym on 09.03.2017.
 */
@Named
public class Main {

    private static Service service = new Service("com");

    @Inject
    @Named(value = "second")
    public Inter inter;

    public static void main(String[] args){
    }
}
