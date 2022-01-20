package Test.Persistence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import Domain.Interfaces.IBusiness;
import Persistence.FileReader;


public class FileReaderTest {
    @Test
    void testReadLines() throws IOException {
        FileReader<IBusiness> fr = new FileReader<>("/home/smarqito/grupo43/users.csv");
        List<String> lines = fr.readLines();
        FileOutputStream fos = new FileOutputStream("/home/smarqito/grupo43/file.TEXT");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(fr);
        oos.close();
    }

    @Test
    void testReadLinesConverted() {

    }
}
