package net.spikesync.pingerdaemonrabbitmqclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class RawICMPJNI {
    static {
        try {
            // Extract and load the native library
            String libName = "libicmp_native.so";
            InputStream in = RawICMPJNI.class.getResourceAsStream("/" + libName);
            if (in == null) {
                throw new RuntimeException("Native library not found in resources: " + libName);
            }
            
            // Create a temp file
            File tempFile = Files.createTempFile("libicmp_native", ".so").toFile();
            tempFile.deleteOnExit(); // Ensure cleanup
            
            // Write the library content to the temp file
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            
            // Load the extracted library
            System.load(tempFile.getAbsolutePath());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load native library", e);
        }
    }
  
    public native boolean sendICMPPing(String ipAddress);
}
 /*
static {
try {
    System.out.println("Loading native library: libicmp_native.so");
    System.loadLibrary("icmp_native");  // Ensure the correct name
    System.out.println("Native library loaded successfully.");
} catch (UnsatisfiedLinkError e) {
    System.err.println("Failed to load native library: " + e.getMessage());
    throw e;
}
}
*/