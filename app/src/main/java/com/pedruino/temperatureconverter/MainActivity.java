package com.pedruino.temperatureconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final double MIN_VALUE = 0;
    private EditText temperatureLeftEditText;
    private EditText temperatureRightEditText;
    private Spinner temperatureSelectOptionsLeft;
    private Spinner temperatureSelectOptionsRight;

    private int oldIndexTemperatureSelectedLeft;
    private int oldIndexTemperatureSelectedRight;
    private TextWatcher leftTextWatcher;
    private TextWatcher rightTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.temperatureLeftEditText = this.findViewById(R.id.activity_main_temperature_left_edit_text);
        this.temperatureRightEditText = this.findViewById(R.id.activity_main_temperature_right_edit_text);

        this.temperatureSelectOptionsLeft = this.findViewById(R.id.activity_main_temperature_select_options_left_spinner);
        this.temperatureSelectOptionsRight = this.findViewById(R.id.activity_main_temperature_select_options_right_spinner);


        ArrayAdapter<CharSequence> options = ArrayAdapter.createFromResource(this,
                R.array.converter_options, android.R.layout.simple_spinner_item);

        options.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        temperatureSelectOptionsLeft.setAdapter(options);
        temperatureSelectOptionsRight.setAdapter(options);

        oldIndexTemperatureSelectedLeft = 0;
        oldIndexTemperatureSelectedRight = 1;

        temperatureSelectOptionsLeft.setSelection(oldIndexTemperatureSelectedLeft, false);
        temperatureSelectOptionsRight.setSelection(oldIndexTemperatureSelectedRight, false);

        temperatureSelectOptionsLeft.setOnItemSelectedListener(this);
        temperatureSelectOptionsRight.setOnItemSelectedListener(this);

        this.leftTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !(s.toString().equals("-") || s.toString().equals("+"))) {
                    convertTemperature(s, temperatureSelectOptionsLeft, temperatureSelectOptionsRight, temperatureRightEditText, rightTextWatcher);
                } else if (s.length() == 0) {
                    temperatureLeftEditText.setText(String.valueOf(MIN_VALUE));
                }
            }
        };

        this.rightTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !(s.toString().equals("-") || s.toString().equals("+"))) {
                    convertTemperature(s, temperatureSelectOptionsRight, temperatureSelectOptionsLeft, temperatureLeftEditText, leftTextWatcher);
                } else if (s.length() == 0) {
                    temperatureRightEditText.setText(String.valueOf(MIN_VALUE));
                }
            }
        };


        temperatureRightEditText.addTextChangedListener(rightTextWatcher);
        temperatureLeftEditText.addTextChangedListener(leftTextWatcher);

        temperatureLeftEditText.setText("0");

    }

    private void convertTemperature(CharSequence text, Spinner sourceTemperatureSelectOptions, Spinner targetTemperatureSelectOptions, EditText targetTemperatureEditText, TextWatcher targetTextWatcher) {
        TemperatureUnit from = TemperatureUnit.valueOf(sourceTemperatureSelectOptions.getSelectedItemPosition());
        TemperatureUnit to = TemperatureUnit.valueOf(targetTemperatureSelectOptions.getSelectedItemPosition());

        double originalValue = Double.parseDouble(text.toString());
        double convertedValue = new TemperatureConverter(from, to).Convert(originalValue);
        BigDecimal scaledValue = BigDecimal.valueOf(convertedValue).setScale(2, RoundingMode.HALF_UP);

        targetTemperatureEditText.removeTextChangedListener(targetTextWatcher);
        targetTemperatureEditText.setText(scaledValue.toString());
        targetTemperatureEditText.addTextChangedListener(targetTextWatcher);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == temperatureSelectOptionsLeft) {
            if (temperatureSelectOptionsRight.getSelectedItemPosition() == position) {
                temperatureSelectOptionsRight.setOnItemSelectedListener(null);
                temperatureSelectOptionsRight.setSelection(oldIndexTemperatureSelectedLeft, true);
                oldIndexTemperatureSelectedRight = oldIndexTemperatureSelectedLeft;
                temperatureSelectOptionsRight.setOnItemSelectedListener(this);
            }

            oldIndexTemperatureSelectedLeft = position;
            convertTemperature(temperatureLeftEditText.getText(), temperatureSelectOptionsLeft, temperatureSelectOptionsRight, temperatureRightEditText, rightTextWatcher);

        } else if (parent == temperatureSelectOptionsRight) {

            if (temperatureSelectOptionsLeft.getSelectedItemPosition() == position) {
                temperatureSelectOptionsLeft.setOnItemSelectedListener(null);
                temperatureSelectOptionsLeft.setSelection(oldIndexTemperatureSelectedRight, true);
                oldIndexTemperatureSelectedLeft = oldIndexTemperatureSelectedRight;
                temperatureSelectOptionsLeft.setOnItemSelectedListener(this);
            }

            oldIndexTemperatureSelectedRight = position;
            convertTemperature(temperatureLeftEditText.getText(), temperatureSelectOptionsLeft, temperatureSelectOptionsRight, temperatureRightEditText, rightTextWatcher);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
