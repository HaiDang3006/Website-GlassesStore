package com.example.tranhaidang_pc08348_java_4_asm.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonIO {

    public static String read(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("utf-8");
        BufferedReader reader = request.getReader();
        String line;
        StringBuffer buffer = new StringBuffer();
        while (( line = reader.readLine())!=null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static void write(HttpServletResponse response, String json) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

}
