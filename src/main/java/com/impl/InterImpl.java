package com.impl;

import com.inter.Inter;

import javax.inject.Named;

/**
 * Created by Maksym on 09.03.2017.
 */
@Named(value = "first")
public class InterImpl implements Inter {

    @Override
    public String getValue() {
        return "First implemetation";
    }
}
