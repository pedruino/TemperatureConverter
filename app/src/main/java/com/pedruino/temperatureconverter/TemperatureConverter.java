package com.pedruino.temperatureconverter;

class TemperatureConverter {
    private final TemperatureUnit from;
    private final TemperatureUnit to;

    public TemperatureConverter(TemperatureUnit from, TemperatureUnit to) {
        this.from = from;
        this.to = to;
    }

    public double Convert(double value) {
        double convertedValue = 0;

        switch (from) {
            case Celsius:
                if (to == TemperatureUnit.Fahrenheit) {
                    convertedValue = convertCelsiusToFahrenheit(value);
                } else if (to == TemperatureUnit.Kelvin) {
                    convertedValue = convertCelsiusToKelvin(value);
                }
                break;
            case Fahrenheit:
                if (to == TemperatureUnit.Celsius) {
                    convertedValue = convertFahrenheitToCelsius(value);
                } else if (to == TemperatureUnit.Fahrenheit) {
                    convertedValue = convertFahrenheitToKelvin(value);
                }
                break;

            case Kelvin:
                if (to == TemperatureUnit.Celsius) {
                    convertedValue = convertKelvinToCelsius(value);
                } else if (to == TemperatureUnit.Fahrenheit) {
                    convertedValue = convertKelvinToFahrenheit(value);
                }
                break;
        }
        return convertedValue;
    }

    private double convertCelsiusToKelvin(double value) {
        return (value + 273.15);
    }

    private double convertCelsiusToFahrenheit(double value) {
        return ((value * 1.8) + 32);
    }

    private double convertFahrenheitToCelsius(double value) {
        return ((value - 32) / 1.8);
    }

    private double convertFahrenheitToKelvin(double value) {
        return ((value + 459.67) / 1.8);
    }

    private double convertKelvinToCelsius(double value) {
        return (value - 273.15);
    }

    private double convertKelvinToFahrenheit(double value) {
        return ((value * 1.8) - 459.67);
    }
}
