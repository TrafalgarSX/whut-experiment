package File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileCopy {
    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
