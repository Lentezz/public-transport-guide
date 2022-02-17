package com.univer.public_transport_guide.model;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Route extends AppCompatActivity {
    ArrayList<Stop> routeStop = new ArrayList();

    public Route() {

    }

    public ArrayList<Stop> getRouteStop() {
        return routeStop;
    }

    public void setRouteStop(ArrayList<Stop> routeStop) {
        this.routeStop = routeStop;
    }

    public void addStop(Stop stop){
        this.routeStop.add(stop);
    }

}

