package com.impl;

import com.inter.Inter;

import javax.inject.Named;

/**
 * Created by Maksym on 09.03.2017.
 */
@Named(value = "second")
public class InterImplSecond implements Inter {

    @Override
    public String getValue() {
        return "Second implementation";
    }
}
