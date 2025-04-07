package com.example.farmacia0304.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {


    public static List<String[]> lerCSV(String caminho) throws IOException {
        List<String[]> linhas = new ArrayList<>();
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            arquivo.createNewFile(); // cria o arquivo se ele ainda não existir
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha.split(";"));
            }
        }
        return linhas;
    }


    public static void escreverCSV(String caminho, List<String> linhas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminho), StandardCharsets.UTF_8))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        }
    }
}
