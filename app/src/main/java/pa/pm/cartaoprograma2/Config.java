package pa.pm.cartaoprograma2;

//

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;



//

import android.graphics.Color;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;



public class Config extends Activity {
	public static String colorCaminhoPercorrido;
	public static String colorLinhaDaRota;
	public static String colorAreaDeAtuacao;
	public static String colorMancha;	
	public static String colorLocalizacaoAtual;
	//String colorCaminhoPercorrido1;
	String colorLinhaDaRota1;
	String colorAreaDeAtuacao1;
	String colorMancha1;	
	String colorLocalizacaoAtual1;
	@Override
	public void onCreate(Bundle icicle){   
		super.onCreate(icicle);        
		colorLocalizacaoAtual1=colorLocalizacaoAtual;
		colorLinhaDaRota1=colorLinhaDaRota;
		colorAreaDeAtuacao1=colorAreaDeAtuacao;
		colorMancha1=colorMancha;
		setContentView(R.layout.config);
		addItemsOnLocalizaçãoatual();
		addListenerOnButton();
		addItemsOnLinhadarota();
		addListenerOnButton2();
		addItemsOnAreadeatuacao();
		addListenerOnButton3();
		addItemsOnManchacriminal();
		addListenerOnButton4();

	}
	Spinner spinner1;
	Spinner spinner2;
	Spinner spinner3;
	Spinner spinner4;
	String azul="AZUL";
	public void addItemsOnLocalizaçãoatual() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		String color=colorLocalizacaoAtual;
		list.add(Color2(color));
		if(Color2(color).length()=="AZUL".length()){

		}else{

			list.add("AZUL");	
		}
		if(Color2(color).length()=="VERDE".length()){

		}else{

			list.add("VERDE");	
		}
		if(Color2(color).length()=="VERMELHO".length()){
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
		}else{
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
			list.add("VERMELHO");	
		}
		if(Color2(color).length()=="AMARELO".length()){

		}else{

			list.add("AMARELO");	
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
		spinner1.setBackgroundColor(Color.GRAY);
		
	}
	
	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String color=spinner1.getSelectedItem().toString();
				colorLocalizacaoAtual=Color(color);
				System.out.println("Color now"+colorLocalizacaoAtual);
				Log.e("Selected item : ",colorLocalizacaoAtual);

			}
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});


	}
	public void addItemsOnLinhadarota() {

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list = new ArrayList<String>();
		String color=colorLinhaDaRota;
		list.add(Color2(color));
		if(Color2(color).length()=="AZUL".length()){

		}else{

			list.add("AZUL");	
		}
		if(Color2(color).length()=="VERDE".length()){

		}else{

			list.add("VERDE");	
		}
		if(Color2(color).length()=="VERMELHO".length()){
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
		}else{
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
			list.add("VERMELHO");	
		}
		if(Color2(color).length()=="AMARELO".length()){

		}else{

			list.add("AMARELO");	
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
		spinner2.setBackgroundColor(Color.GRAY);
		
	}
	
	public void addListenerOnButton2() {

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String color=spinner2.getSelectedItem().toString();
				colorLinhaDaRota=Color(color);
				Log.e("Selected item : ",colorLinhaDaRota);

			}
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});


	}
	public void addItemsOnAreadeatuacao() {

		spinner3 = (Spinner) findViewById(R.id.spinner3);
		List<String> list = new ArrayList<String>();
		String color=colorAreaDeAtuacao;
		list.add(Color2(color));
		if(Color2(color).length()=="AZUL".length()){

		}else{

			list.add("AZUL");	
		}
		if(Color2(color).length()=="VERDE".length()){

		}else{

			list.add("VERDE");	
		}
		if(Color2(color).length()=="VERMELHO".length()){
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
		}else{
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
			list.add("VERMELHO");	
		}
		if(Color2(color).length()=="AMARELO".length()){

		}else{

			list.add("AMARELO");	
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(dataAdapter);
		spinner3.setBackgroundColor(Color.GRAY);
	
	}
	
	public void addListenerOnButton3() {

		spinner3 = (Spinner) findViewById(R.id.spinner3);
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String color=spinner3.getSelectedItem().toString();
				colorAreaDeAtuacao=Color(color);
				Log.e("Selected item : ",colorAreaDeAtuacao);

			}
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});


	}
	public void addItemsOnManchacriminal() {

		spinner4 = (Spinner) findViewById(R.id.spinner4);
		List<String> list = new ArrayList<String>();
		String color=colorMancha;
		list.add(Color2(color));
		if(Color2(color).length()=="AZUL".length()){

		}else{

			list.add("AZUL");	
		}
		if(Color2(color).length()=="VERDE".length()){

		}else{

			list.add("VERDE");	
		}
		if(Color2(color).length()=="VERMELHO".length()){
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
		}else{
			System.out.println(Color(color).length());
			System.out.println("VERMELHO".length());
			list.add("VERMELHO");	
		}
		if(Color2(color).length()=="AMARELO".length()){

		}else{

			list.add("AMARELO");	
			
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner4.setAdapter(dataAdapter);
		spinner4.setBackgroundColor(Color.GRAY);
		
	}
	
	public void addListenerOnButton4() {

		spinner4 = (Spinner) findViewById(R.id.spinner4);
		spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String color=spinner4.getSelectedItem().toString();
				colorMancha=Color(color);
				Log.e("Selected item : ",colorMancha);

			}
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});


	}
	private String Color (String color){
		if(color.equals("AZUL")){color="BLUE";}
		if(color.equals("VERDE")){color="GREEN";}
		if(color.equals("VERMELHO")){color="RED";}
		if(color.equals("AMARELO")){color="YELLOW";}
		
	return color;
	}
	private String Color2 (String color){
		if(color.equals("BLUE")){color="AZUL";}
		if(color.equals("GREEN")){color="VERDE";}
		if(color.equals("RED")){color="VERMELHO";}
		if(color.equals("YELLOW")){color="AMARELO";}
		
	return color;
	}
	public void Salvar(View v){

		Polylines.addColor(colorLinhaDaRota);

		finish();
	}
	@Override
	public void onBackPressed() {
		colorLocalizacaoAtual=colorLocalizacaoAtual1;//ok
		colorLinhaDaRota=colorLinhaDaRota1;
		colorAreaDeAtuacao=colorAreaDeAtuacao1;
		colorMancha=colorMancha1;//ok
		finish();
	}
	public void Voltar(View V) {
		colorLocalizacaoAtual=colorLocalizacaoAtual1;//ok
		colorLinhaDaRota=colorLinhaDaRota1;
		colorAreaDeAtuacao=colorAreaDeAtuacao1;
		colorMancha=colorMancha1;//ok
		finish();
	}
	}
