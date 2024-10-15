package it.ingbs.ingegneria_software.gestioneAccesso;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
 
    
public class GestoreFileAccessoTest {
        
    @Test
    public void testSalvaMappaSuFile_ValidInput() throws IOException {
        // Arrange
        HashMap<String, String> testMappaUtenti = new HashMap<>();
        testMappaUtenti.put("user1", "password1");
        testMappaUtenti.put("user2", "password2");
        GestoreFileAccesso gestoreFileAccesso = new GestoreFileAccesso(testMappaUtenti);
        File tempFile = File.createTempFile("test", ".txt");
    
        // Act
        gestoreFileAccesso.salvaMappaSuFile(tempFile);
    
        // Assert
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        String line;
        int lineCount = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            assertTrue(testMappaUtenti.containsKey(parts[0]));
            assertEquals(testMappaUtenti.get(parts[0]), parts[1]);
            lineCount++;
        }
        reader.close();
        assertEquals(testMappaUtenti.size(), lineCount);
    
        // Clean up
        tempFile.delete();
    }
    @Test
    public void testSalvaMappaSuFile_CorrectFormat() throws IOException {
        // Arrange
        HashMap<String, String> testMappaUtenti = new HashMap<>();
        testMappaUtenti.put("user1", "password1");
        testMappaUtenti.put("user2", "password2");
        GestoreFileAccesso gestoreFileAccesso = new GestoreFileAccesso(testMappaUtenti);
        File tempFile = File.createTempFile("test", ".txt");
    
        // Act
        gestoreFileAccesso.salvaMappaSuFile(tempFile);
    
        // Assert
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            assertEquals(2, parts.length, "Each line should have exactly two parts separated by a space");
            assertTrue(testMappaUtenti.containsKey(parts[0]), "Username should be present in the original map");
            assertEquals(testMappaUtenti.get(parts[0]), parts[1], "Password should match the one in the original map");
        }
        reader.close();
    
        // Clean up
        tempFile.delete();
    }
}
    