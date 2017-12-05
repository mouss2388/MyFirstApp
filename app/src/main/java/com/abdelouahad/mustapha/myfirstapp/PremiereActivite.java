package com.abdelouahad.mustapha.myfirstapp;

import android.annotation.SuppressLint;
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

    /**
     * Widgets from user Interface
     */
    private EditText poids = null;
    private EditText taille = null;
    private RadioGroup group = null;
    private CheckBox megaFunc = null;
    private Button calcul = null;

    private TextView resultat = null;
    private String megaStrg = null;
    private String defaut = null;
    private String TAG = getClass().getSimpleName();

    Boolean DISABLE = false;
    Boolean ENABLE = true;


    /**
     * Linked WIDGETS and Interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_premiere_activite);

        poids =  findViewById(R.id.editPoids);
        taille = findViewById(R.id.editTaille);
        megaFunc = findViewById(R.id.mega);
        group = findViewById(R.id.rdGroup);
        calcul =findViewById(R.id.calcul);
        Button raz = findViewById(R.id.raz);
        resultat =  findViewById(R.id.showResult);
        megaStrg = getResources().getString(R.string.megaStrg);
        defaut= getResources().getString(R.string.défault);
        Log.i(TAG,"ONCREATE ");

        calcul.setEnabled(false);
        calcul.setOnClickListener(this);
        raz.setOnClickListener(this);

        poids.addTextChangedListener(textWatcher);
        taille.addTextChangedListener(textWatcher);


    }

    /**
     * TextWatcher for detect operation about Text (Changed ...)
     */
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
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        /*
        Detected Button Clicked
         */
        switch (v.getId()){
            case R.id.calcul:
                Toast.makeText(v.getContext(),"CALCUL",Toast.LENGTH_SHORT).show();
                Float result;

                DecimalFormat df = new DecimalFormat("0.00");
                /* Get values Fields
                    -checkBox
                    -EditText Poids
                    -EditText Taille
                 */
                int checkBoxSelect= group.getCheckedRadioButtonId();
                float poidsKg = Float.parseFloat(poids.getText().toString());
                float tailleMetre = Float.parseFloat(taille.getText().toString());

                switch (checkBoxSelect){
                    /*
                    Cacul IMC
                    -IF Radio2 selected Then convert centimeters to meters for calculate User's IMC
                    -neither just calcultate without conversion
                     */
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
                /*
                Change the resultat's sentence by megaFunc CheckBox is checked or not
                 */
                if(megaFunc.isChecked()) {
                    String resultRounded = df.format(result);
                    resultat.setText(resultRounded + " " + megaStrg);
                }else {
                    resultat.setText(df.format(result));
                }
                break;

            case R.id.raz:
                /*
                *Reinitialize ALl Fields
                */
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
        return editText.getText().length() <= 0;
    }

    private boolean isNegative(EditText editText) {
        Double value = Double.parseDouble(editText.getText().toString());
        return value < 0.0d || value == 0.0d;
    }
}
