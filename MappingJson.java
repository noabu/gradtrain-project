
import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.Vector;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

 

 

 

public class MappingJson {

 

	private final String PATH_FACEBOOK = "C:\\Users\\Public\\efi_grad\\data4.txt";
	private final String PATH_LINKLEN = "C:\\Users\\Public\\efi_grad\\Output.txt";

 

 

          private Vector<String> numOfUserFacebookAL;

          private Vector<String> numOfUserLinklenAL;

 

          public MappingJson(String nameOf) throws InterruptedException, IOException {

                    if(nameOf == "facebook") {

                              numOfUserFacebookAL = new Vector<>();

                              numOfUserFacebookAL = getTheNumberOfUserFacebook();

                    }

                    else if(nameOf == "linkedin") {

                              numOfUserLinklenAL = new Vector<>();

                              numOfUserLinklenAL = getTheNumberOfUserLinklen();

                    }

          }

 

          //func that get all the number facebook user from the file(by the func - 'getStringFromFile') and cut the '@' from them

          private Vector<String> getTheNumberOfUserFacebook() throws InterruptedException, IOException {

                    Matcher m = Pattern.compile("[0-9]+@").matcher(getStringFromFile(PATH_FACEBOOK));

                    while (m.find()) {

                              numOfUserFacebookAL.add(m.group().replace("@", "").toString());

                    }

                    return numOfUserFacebookAL;

          }

         

         

 

          private Vector<String> getTheNumberOfUserLinklen() throws InterruptedException, IOException {

                    Matcher m = Pattern.compile("\"[a-z|A-Z]+ [a-z|A-Z]+\"").matcher(getStringFromFile(PATH_LINKLEN));

                    while (m.find()) {

                              numOfUserLinklenAL.add(m.group().replace(" ", "-").substring(0, m.group().length()-1).toString().substring(1));

                    }

                    return numOfUserLinklenAL;

          }

 

          //func that read the data from the file and convert it to string

          private synchronized String getStringFromFile(String filePath) throws IOException {

                    System.out.println("Get string from file: '" + filePath + "'");

                    BufferedReader reader = new BufferedReader(new FileReader (filePath));

                    String         line = null;

                    StringBuilder  stringBuilder = new StringBuilder();

                    String         ls = System.getProperty("line.separator");

 

                    try {

                              while((line = reader.readLine()) != null) {

                                       stringBuilder.append(line);

                                       stringBuilder.append(ls);

                              }

                              return stringBuilder.toString();

                    } finally {

                              reader.close();

                    }

          }

 

          public String getNumberOfFacebookByIndex(int index) {

                    return numOfUserFacebookAL.get(index);

          }

 

          public String getNumberOfLinkedinByIndex(int index) {

                    return numOfUserLinklenAL.get(index);

          }

 

          public int getSizeOfFacebook() {

                    return numOfUserFacebookAL.size();

          }

 

          public int getSizeOfLinklen() {

                    return numOfUserLinklenAL.size();

          }

 

 

 

}

 

