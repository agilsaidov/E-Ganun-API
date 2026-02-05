package com.project.e_ganun.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class JsonToSQLConverter {
    public static void main(String[] args) throws IOException, SQLException {
        File file = new File("file_path.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(file);

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_name", "username", "password");
        PreparedStatement ps = conn.prepareStatement("INSERT INTO ganun(ganun_no, ganun_text) VALUES (?, ?)");

        Iterator<Map.Entry<String, JsonNode>> fields = root.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey() + ".";
            String value = entry.getValue().asText();

            ps.setString(1, key);
            ps.setString(2, value);
            ps.executeUpdate();
        }
        ps.close();
        conn.close();

        System.out.println("Done");
    }
}