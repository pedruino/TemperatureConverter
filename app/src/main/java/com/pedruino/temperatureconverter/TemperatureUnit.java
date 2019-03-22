package com.pedruino.temperatureconverter;

import java.util.HashMap;
import java.util.Map;

enum TemperatureUnit {
    Celsius(0),
    Fahrenheit(1),
    Kelvin(2);

    private int value;
    private static Map map = new HashMap<>();

    TemperatureUnit(int value) {
        this.value = value;
    }

    static {
        for (TemperatureUnit pageType : TemperatureUnit.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static TemperatureUnit valueOf(int unit) {
        return (TemperatureUnit) map.get(unit);
    }
}