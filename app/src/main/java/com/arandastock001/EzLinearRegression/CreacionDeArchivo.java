package com.arandastock001.EzLinearRegression;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arandastock001.EzLinearRegression.Modelo.CalculadoraDeValores;
import com.arandastock001.EzLinearRegression.Modelo.ControladorDeColores;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreacionDeArchivo extends AppCompatActivity {

    private Button btnCrearExcel, btnCrearPDF, btnCrearTxt, btnVolverDesdeCreacionDeArchivo, btnVerArchivos, btnProyectar;
    private ConstraintLayout creacionDeArchivo;
    private ControladorDeColores controladorDeColores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_de_archivo);

        btnCrearExcel = (Button) findViewById(R.id.btnCrearExcel);
        btnCrearPDF = (Button) findViewById(R.id.btnCrearPDF);
        btnCrearTxt = (Button) findViewById(R.id.btnCrearTxt);
        btnVolverDesdeCreacionDeArchivo = (Button) findViewById(R.id.btnVolverDesdeCreacionDeArchivo);
        btnVerArchivos = (Button) findViewById(R.id.btnVerArchivos);
        btnProyectar = (Button) findViewById(R.id.btnProyectar);

        btnVerArchivos.setVisibility(View.INVISIBLE);

        creacionDeArchivo = (ConstraintLayout) findViewById(R.id.layoutCreacionDeArchivos);


        ControladorDeColores controladorDeColores = ControladorDeColores.getInstance();
        controladorDeColores.setObjetoConstraint(creacionDeArchivo);
        controladorDeColores.cambiarColor();


        btnCrearExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                CalculadoraDeValores cr = (CalculadoraDeValores) i.getSerializableExtra("calculosRealizados");


                Date momentoActual = Calendar.getInstance().getTime();


                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
                String fecha = df.format(momentoActual);


                String nombreArchivo = "Archivo de Excel.xls";


                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet PrimeraHoja = hssfWorkbook.createSheet("Detalles en Excel");

                HSSFRow filaActual = PrimeraHoja.createRow(0);

                HSSFCell celdaColumnaX = filaActual.createCell(0);
                HSSFCell celdaColumnaY = filaActual.createCell(1);
                celdaColumnaX.setCellValue("Columna X");
                celdaColumnaY.setCellValue("Columna Y");

                HSSFCell celdaEspaciadora = filaActual.createCell(2);
                celdaEspaciadora.setCellValue("");

                HSSFCell celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valores");


                Integer contador = 0;

                for (int j = 0; j < cr.getColumnaX().size(); j++) {
                    contador++;
                    filaActual = PrimeraHoja.createRow(contador);
                    celdaColumnaX = filaActual.createCell(0);
                    celdaColumnaX.setCellValue(cr.getColumnaX().get(j));

                    celdaColumnaY = filaActual.createCell(1);
                    celdaColumnaY.setCellValue(cr.getColumnaY().get(j));

                }



                filaActual.setRowNum(1);//set row number se usa para cambiar la fila en la que se esta trabajando.
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Suma de todas las x: " + cr.calcularSumaDeTodasLasX().toString());


                filaActual.setRowNum(2);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Suma de todas las y: " + cr.calcularSumaDeTodasLasY().toString());


                filaActual.setRowNum(3);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Suma de todas las x 2: " + cr.calcularSumaDeTodasLasX().toString());

                filaActual.setRowNum(4);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Suma de todas las y 2: " + cr.calcularSumaDeTodasLasY().toString());


                filaActual.setRowNum(5);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Suma de todas las xy: " + cr.calcularSumaDeTodasLasXY().toString());


                filaActual.setRowNum(6);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valor de pendiente: " + cr.calcularPendiente().toString());


                filaActual.setRowNum(7);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valor de  interseccion: " + cr.calcularInterseccion().toString());


                filaActual.setRowNum(8);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valor de r: " + cr.calcularR().toString());


                filaActual.setRowNum(9);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valor de r 2: " + cr.calcularR2().toString());


                filaActual.setRowNum(10);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valor de la desviación estándar de la columna X: " + cr.calcularDesviacionEstandarColumnaX().toString());


                filaActual.setRowNum(11);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Valor de la desviación estándar de la columna Y: " + cr.calcularDesviacionEstandarColumnaY().toString());


                filaActual.setRowNum(12);
                celdaDeDetalles = filaActual.createCell(3);
                celdaDeDetalles.setCellValue("Ecuación de la recta: y = "+cr.calcularPendiente().toString()+" * x "+" + "+cr.calcularInterseccion().toString());


                File filePath = new File(getExternalFilesDir(null), nombreArchivo);//getApplicationContext().getExternalFilesDir(null);

                try {
                    if (!filePath.exists()) {
                        filePath.createNewFile();
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                    hssfWorkbook.write(fileOutputStream);

                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Toast toast = Toast.makeText(getApplicationContext(),
                        "Archivo de Excel creado con exito",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);

                toast.show();


            }


        });


        btnCrearPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                CalculadoraDeValores cr = (CalculadoraDeValores) i.getSerializableExtra("calculosRealizados");


                Date momentoActual = Calendar.getInstance().getTime();


                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
                String fecha = df.format(momentoActual);


                String nombreArchivo = "Archivo PDF.pdf";//"PDF creado el " + fecha + ".pdf";


                String texto = cr.mostrarPasoAPaso();
                String[] lineasParaElPdf = texto.split("\n");


                File path = getApplicationContext().getExternalFilesDir(null);

                final File file = new File(path, nombreArchivo);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                PdfDocument document = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1000, 1000, 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);


                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();
                int valorX = 0;
                int valorY = 0;

                for (int j = 0; j < lineasParaElPdf.length; j++) {
                    //valorX=valorX+10;
                    valorY = valorY + 12;
                    canvas.drawText(lineasParaElPdf[j], valorX, valorY, paint);
                }


                document.finishPage(page);

                try {
                    document.writeTo(fOut);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                document.close();


                Toast toast = Toast.makeText(getApplicationContext(),
                        "Archivo pdf creado exitosamente",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);

                toast.show();


            }
        });

        btnCrearTxt.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               Intent i = getIntent();
                                               CalculadoraDeValores cr = (CalculadoraDeValores) i.getSerializableExtra("calculosRealizados");


                                               Date momentoActual = Calendar.getInstance().getTime();

                                               DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
                                               String fecha = df.format(momentoActual);


                                               String nombreArchivo = "Archivo txt.txt";//"txt creado el " + fecha + ".txt";

                                               String texto = cr.mostrarPasoAPaso();


                                               // no usar?
                                               File path = getApplicationContext().getExternalFilesDir(null);


                                               //deprecado?
                                               File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                                               File file = new File(path, nombreArchivo);


                                               FileOutputStream stream = null;
                                               try {
                                                   stream = new FileOutputStream(file);

                                                   Toast toast = Toast.makeText(getApplicationContext(),
                                                           "Archivo txt creado exitosamente",
                                                           Toast.LENGTH_SHORT);
                                                   toast.setGravity(Gravity.CENTER, 0, 0);

                                                   toast.show();


                                               } catch (FileNotFoundException e) {
                                                   e.printStackTrace();
                                               }
                                               try {
                                                   try {


                                                       stream.write(texto.getBytes());
                                                   } catch (IOException e) {
                                                       e.printStackTrace();
                                                   }
                                               } finally {
                                                   try {
                                                       stream.close();
                                                   } catch (IOException e) {
                                                       e.printStackTrace();
                                                   }
                                               }


                                               //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                               //Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                                               //+  File.separator + "ezregre" + File.separator);
                                               //intent.setDataAndType(uri, "text/csv");
                                               //startActivity(Intent.createChooser(intent, "Abrir carpeta"));


                                               //Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");

                                               //startActivity(intent);


                                           }
                                       }

        );

        btnVerArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirigirADirecotrioDeArchivosDeLaApp();
                //abrirCarpetaDeArchivos();
            }
        });

        btnVolverDesdeCreacionDeArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                CalculadoraDeValores calculosRealizados = (CalculadoraDeValores) i.getSerializableExtra("calculosRealizados");

                startActivity(new Intent(CreacionDeArchivo.this, ResumenDeResultados.class).putExtra("calculosRealizados", (Serializable) calculosRealizados));
                finish();


            }
        });

        btnProyectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                CalculadoraDeValores calculosRealizados = (CalculadoraDeValores) i.getSerializableExtra("calculosRealizados");

                startActivity(new Intent(CreacionDeArchivo.this, UsarEcuacionDeLaRecta.class).putExtra("calculosRealizados", (Serializable) calculosRealizados));
                finish();
            }
        });


    }


    public void redirigirADirecotrioDeArchivosDeLaApp() {

        //no es necesario este bloque, pero lo dejo para referencia
/*
                if(Build.VERSION.SDK_INT>=24){
                    try{
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
*/

        //La siguiente linea permite  abrir el directorio especifico en donde se guardan los archivos
        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Android/data/com.arandastock001.ezregre/files/");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(intent);
            finish();
        } else {
            // se necesita app de explorarcion de archivos
        }

    }




}
