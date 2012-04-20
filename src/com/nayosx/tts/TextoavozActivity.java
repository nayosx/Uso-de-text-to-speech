package com.nayosx.tts;

import java.io.*;
import java.util.Locale;

import android.app.*;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class TextoavozActivity extends Activity implements TextToSpeech.OnInitListener, OnClickListener{
    /** Called when the activity is first created. */
	private TextView mostrar;
	private Button iniciar, parar;
	private TextToSpeech miLector;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inicializar();
        mostrarTexto();
    }
    
    private void inicializar()
    {
    	mostrar = (TextView) findViewById(R.id.mostrar);
		iniciar = (Button) findViewById(R.id.bIniciar);
		parar = (Button) findViewById(R.id.bParar);
		
		miLector = new TextToSpeech(this, this);
		miLector.isLanguageAvailable(new Locale("spa"));
		
		iniciar.setOnClickListener(this);
		parar.setOnClickListener(this);
    }
    
    
    private void mostrarTexto() {
		// TODO Auto-generated method stub
    	try {
	    	int id = R.raw.linux;
			InputStreamReader insr = new InputStreamReader(this.getResources().openRawResource(id ));
			BufferedReader bf = new BufferedReader(insr);
			String linea;
			StringBuilder texto = new StringBuilder();
			while((linea = bf.readLine()) != null)
			{
					texto.append(linea);
					
					//la siguiente linea es por si son varias lineas de texto
					//texto.append("\n");
					
			}
			insr.close();
			bf.close();
			mostrar.setText(texto.toString());
	    } catch(IOException e)
	    {
	    	Toast.makeText(getApplication(), "No se pudo leer el archivo deseado", Toast.LENGTH_LONG).show();
	    }
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
	    {
	    	case R.id.bIniciar:
	    		iniciarLectura();
	    		Toast.makeText(getApplication(), "Leyendo archivo", Toast.LENGTH_SHORT).show();
	    		break;
	    	case R.id.bParar:
	    		miLector.stop();
	    		Toast.makeText(getApplication(), "Deteniendo lectura", Toast.LENGTH_SHORT).show();
	    		break;	
	    }
	}
	private void iniciarLectura() {
		// TODO Auto-generated method stub
		miLector.speak(mostrar.getText().toString(), TextToSpeech.QUEUE_ADD, null);
	}
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
	    if(status == TextToSpeech.SUCCESS)
	    {
	    	int result = TextToSpeech.LANG_COUNTRY_AVAILABLE;
	    	if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
	    	{
	    		Toast.makeText(getApplication(), "Lenguaje no soportado", Toast.LENGTH_LONG).show();
	    		iniciar.setEnabled(false);
	    	}
	    	else { 
	    		iniciar.setEnabled(true); 
	    		}
	    	
	    }
	}
}