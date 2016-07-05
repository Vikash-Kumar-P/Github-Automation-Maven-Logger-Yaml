package GithubAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;



public class YamlClass {

	
	public static String getvalue(String s) 
	{
       
     
		final String fileName = "Credentials.yaml";
      
      Yaml yaml = new Yaml();
      Object obj;
      Map result = null;
      try {
         InputStream ios = new FileInputStream(new File(fileName));
         
          obj =yaml.load(ios);
          result=(Map)obj;
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      String str = result.get(s).toString();
	
	return str;
   }
}