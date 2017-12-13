package Api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.*;
import java.util.*;

@RestController
@EnableAutoConfiguration
public class Api{
  
  @RequestMapping("/")
  String home(){
    return "/ngrams /getFile /setToken";
    
  }
  
  @RequestMapping
  String nGrams(){
    return "ngrams"
  }
  
  //from crawler
  @RequestMapping(value="/getToken", method=RequestMethod.GET)
  public ResponseEntity<InputStreamResource> getFile() throws IOException(@RequestBody String input) {
    File file = new File(input);
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

    return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + file.getName())
          .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
          .body(resource);  
  }
  
  //get indexing stop words
 @RequestMapping(value="/getToken", method=RequestMethod.GET, consumes="text/plain")
  public void getToken(@RequestBody String input) { 
    System.out.println(input);
  }
  
  //set indexing ngrams
   @RequestMapping(value="/getToken", method=RequestMethod.GET, consumes="text/plain")
   public String getToken(@RequestBody String input){
   		uniDoc.create_json();

      return IOUtils.toString( JsonParsing.class.getResourceAsStream( uniDoc.getName()) );
              
   }

}

