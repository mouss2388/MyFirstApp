package com.abdelouahad.mustapha.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class PremiereActivite extends AppCompatActivity implements View.OnClickListener {

    private EditText poids = null;
    private EditText taille = null;
    private RadioGroup group = null;
    private CheckBox megaFunc = null;
    private Button calcul = null;
    private Button raz = null;
    private TextView resultat = null;
    private String megaStrg = null;
    private String defaut = null;
    private String TAG = getClass().getSimpleName();
    private Boolean DISABLE = false;
    private Boolean ENABLE = true;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resultat.setText(defaut);
            if(!isEmpty(poids) && !isEmpty(taille))
                if(!isNegative(poids) && !isNegative(taille))
                    calcul.setEnabled(ENABLE);
                else
                    calcul.setEnabled(DISABLE);
            else
                calcul.setEnabled(DISABLE);

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premiere_activite);
        poids = (EditText) findViewById(R.id.editPoids);
        taille = (EditText) findViewById(R.id.editTaille);
        megaFunc = (CheckBox) findViewById(R.id.mega);
        group = (RadioGroup) findViewById(R.id.rdGroup);
        calcul =(Button) findViewById(R.id.calcul);
        raz = (Button) findViewById(R.id.raz);
        resultat = (TextView) findViewById(R.id.showResult);
        megaStrg = getResources().getString(R.string.megaStrg).toString();
        defaut= getResources().getString(R.string.défault).toString();
        Log.i(TAG,"ONCREATE ");

        calcul.setEnabled(false);
        calcul.setOnClickListener(this);
        raz.setOnClickListener(this);

        poids.addTextChangedListener(textWatcher);
        taille.addTextChangedListener(textWatcher);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calcul:
                Toast.makeText(v.getContext(),"CALCUL",Toast.LENGTH_SHORT).show();
                Float result = 0.0f;
                DecimalFormat df = new DecimalFormat("0.00");
                int checkBoxSelect= group.getCheckedRadioButtonId();

                float poidsKg = Float.parseFloat(poids.getText().toString());
                float tailleMetre = Float.parseFloat(taille.getText().toString());
                switch (checkBoxSelect){
                    case R.id.radio2:
                        tailleMetre /= 100;
                        tailleMetre = (float) Math.pow(tailleMetre,2);
                        result = poidsKg/tailleMetre;
                        break;
                    default:
                        tailleMetre = (float) Math.pow(tailleMetre,2);
                        result = poidsKg/tailleMetre;
                        break;
                }
                if(megaFunc.isChecked()) {
                    resultat.setText(df.format(result) + " " + megaStrg);
                }else {
                    resultat.setText(df.format(result));
                }
                break;

            case R.id.raz:
                resultat.setText(defaut);
                poids.getText().clear();
                taille.getText().clear();
                group.check(R.id.radio2);
                if(megaFunc.isChecked()){
                    megaFunc.setChecked(false);
                }
                Toast.makeText(this,"RAZ",Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this,"ERROR ",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean isEmpty(EditText  editText){
        if(editText.getText().length()>0){
            return false;
        }else{
            return true;
        }
    }

    private boolean isNegative(EditText editText) {
        Double value = Double.parseDouble(editText.getText().toString());
        if (value < 0.0d || value == 0.0d) {
            return true;
        } else {
            return false;
        }
    }
}
