package com.taller.Proyecto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Etl {

    public void executeEtlScript() throws IOException, InterruptedException {
       
        String pythonPath = "C:\\Users\\lorth\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        String scriptPath = "C:\\Users\\lorth\\AppData\\taller\\Proyecto\\src\\main\\java\\com\\taller\\Proyecto\\resources\\etl_script.py";

        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath);
        processBuilder.redirectErrorStream(true);


        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("El script de ETL falló con el código de salida: " + exitCode);
        }
    }
}

