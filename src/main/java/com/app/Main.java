package com.app;


import com.inter.Inter;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Maksym on 09.03.2017.
 */
@Named
public class Main {

    @Inject
    @Named(value = "second")
    public Inter inter;

    @Test
    public void main(){
        Service service = new Service("com");
        Assert.assertNotNull(inter);
    }
}
