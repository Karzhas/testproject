package kz.karzhas.androidcurrency;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText ed_value;
    Spinner spinner;
    Button convert;
    TextView converted_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_value = findViewById(R.id.ed_val);
        spinner = findViewById(R.id.spinner);
        convert = findViewById(R.id.btn_convert);
        converted_value = findViewById(R.id.txt_converted_value);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                final double[] new_value = {0.0};
                NetworkService.getInstance()
                        .getJSONApi()
                        .getCurrencyRate(spinner.getSelectedItem().toString())
                        .enqueue(new Callback<Currency>() {
                            @Override
                            public void onResponse(@NonNull Call<Currency> call, @NonNull Response<Currency> response) {
                                Currency post = response.body();
                                double value_to_convert = Double.parseDouble(ed_value.getText().toString());
                                new_value[0] = Double.parseDouble(post.rates.KZT);
                                converted_value.setText((new_value[0] * value_to_convert) + "");

                                Log.d("test", new_value[0]+"");
                            }

                            @Override
                            public void onFailure(@NonNull Call<Currency> call, @NonNull Throwable t) {
                                Log.d("test", "fail");
                            }
                        });



            }
        });
    }
}
