package com.app.jlin.cafetraveler.Model;

import java.io.Serializable;

/**
 * Created by Stanley_NB on 2018/4/12.
 */

public class MrtInfoModel implements Serializable {
    private String type;
    private Features features;

    public class Features implements Serializable{
        private String type;
        private Geometry geometry;
        private Properties properties;
    }

    public class Geometry implements Serializable{
        private String type;
        private double[] coordinates;
    }

    public class Properties implements Serializable{

    }
}
