package pl.java.scalatech.metrics;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.google.common.collect.Lists;
@Slf4j
public class JoinTest {
    private List<String> dbs = Lists.newArrayList("dictionary", "security", "user");
   
    @Test
    public void shouldJoin() throws IOException {
      log.info("{}",dbs.stream().collect(Collectors.joining(" , ")));  
     
      for (Path root : FileSystems.getDefault().getRootDirectories())
      {
     
          
         

          try
          {
              FileStore store = Files.getFileStore(root);
          
              System.out.println("available=" + readableFileSize(store.getUsableSpace()) + ", total=" + readableFileSize(store.getTotalSpace()));
          }
          catch (FileSystemException e)
          {
              System.out.println("error querying space: " + e.toString());
          }
      }
    }
    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        
       
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
