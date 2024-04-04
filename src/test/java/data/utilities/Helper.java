package data.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Helper {

    public static Properties accessPropertiesFile(String propertyfile)
    {
        Properties prop = new Properties();
        // try retrieve data from file
        try
        {
            prop.load(new FileInputStream(propertyfile));
        }
        // catch exception in case properties file does not exist
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return prop;
    }

    public static String getPropertyValue(String propertyFile, String propertyName)
    {
        Properties prop = accessPropertiesFile(propertyFile);
        String variable = prop.getProperty(propertyName);
        return variable;
    }
}
