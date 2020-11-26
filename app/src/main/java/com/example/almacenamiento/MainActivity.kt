package com.example.almacenamiento

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    val nombreArchivo = "mi_archivo.text"

    var btnGuardar:Button?=null
    var etInfo:EditText?=null
    var btnLeer:Button?=null
    var tvMostrar:TextView?=null

    var btnLeerP:Button?=null
    var btnGuardarP:Button?=null

    var atClave:EditText?=null
    var etValor:EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        btnGuardar!!.setOnClickListener {
            //Validar que existe texto en info
            val datos = etInfo!!.text.toString()
            escribirDatos(datos)

        }

        btnLeer!!.setOnClickListener {
            tvMostrar!!.text = leerArchivo(nombreArchivo)
        }

        btnLeerP!!.setOnClickListener {
            val claveABuscar = atClave!!.text.toString()
            val datoEncontrado=getDato(claveABuscar)

            etValor!!.setText(datoEncontrado)

        }

        btnGuardarP!!.setOnClickListener {
            val claveAGuardar = atClave!!.text.toString()
            val valorAGuardar = etValor!!.text.toString()
            setDato(claveAGuardar, valorAGuardar)
        }


    }

    fun escribirDatos(texto:String){
        val fos:FileOutputStream

        try {
            fos = openFileOutput(nombreArchivo, Context.MODE_PRIVATE)
            fos.write(texto.toByteArray())
            fos.close()
            Log.wtf("Archivos", texto)
        }catch (e:Exception){
            Log.wtf("Archivos", "Algo falló ${e.message}")
            e.printStackTrace()
        }

    }

    fun leerArchivo(nombreArchivo:String):String{
        val inputStream:InputStream = openFileInput(nombreArchivo)

        val stringResultado = inputStream.bufferedReader().use { it.readLine() }
        return  stringResultado
    }
    /*Métdos para sharedPreferences*/
    fun setDato(clave:String, valor:String){
        val prefs = getPreferences(MODE_PRIVATE)
        prefs.edit().putString(clave,valor).apply()
    }

    fun getDato(clave:String):String{
        val prefs = getPreferences(MODE_PRIVATE)
        val dato = prefs.getString(clave,"No se encontró ${clave}")
        return dato.toString()
    }
    fun initUI(){
        btnGuardar = findViewById(R.id.btnGuardar)
        etInfo = findViewById(R.id.etInfo)
        btnLeer = findViewById(R.id.btnLeer)
        tvMostrar = findViewById(R.id.tvMostrar)
        atClave = findViewById(R.id.atClave)
        etValor = findViewById(R.id.etValor)
        btnGuardarP = findViewById(R.id.btnGuardarPrefs)
        btnLeerP = findViewById(R.id.btnLeerPrefs)
    }
}