package com.kmv.util.file;

import com.kmv.Converter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * This class is a utility for files which reads, writes, and downloads files
 *
 * @author khosro.makari@gmail.com
 */
public final class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());

    // this property should be set to determine the JAXB factory implementation, which is eclipse in this case
    static {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
    }

    private FileUtil() {
        throw new IllegalArgumentException("Is Not Allowed to construct!");
    }

    /**
     * reads xml of json file from the given path, and converts it to an object
     * of the given class
     *
     * @param <T> The type of Object that data should be converted
     * @param classType The type of class for data converting
     * @param path path of file for reading
     * @param fileType type of file as Enum
     * @return the data which is read and converted to classtype
     */
    public static <T> T readXmlOrJsonFile(Class<T> classType, String path, FileType fileType) {
        try {
            File file = new File(new StringBuilder(Converter.BASE_PATH).append(path).toString());
            Map<String, Object> properties = new HashMap<>(1);
            if (fileType == FileType.JSON) {
                properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            }
            JAXBContext jaxbc = JAXBContext.newInstance(new Class[]{classType}, properties);
            Unmarshaller unmarshaller = jaxbc.createUnmarshaller();
            try (FileReader fileReader = new FileReader(file);) {
                T type = (T) unmarshaller.unmarshal(fileReader);
                return type;
            }
        } catch (JAXBException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new RuntimeException("could not read the xml-json file!", ex);
        }

    }

    /**
     *
     * @param <T> The type of Object that data should be marshalled
     * @param classType The type of class for data marshalling
     * @param object Type of object for marshalling
     * @param path path for writing output
     */
    public static <T> void writeObjectToJsonFile(Class<T> classType, T object, String path) {
        try {
            File file = new File(new StringBuilder(Converter.BASE_PATH).append(path).toString());
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            properties.put(MarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");
            JAXBContext jaxbc = JAXBContext.newInstance(new Class[]{classType}, properties);
            Marshaller marshaller = jaxbc.createMarshaller();
            try (FileWriter fileWriter = new FileWriter(file);) {
                marshaller.marshal(object, fileWriter);
            }
        } catch (JAXBException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new RuntimeException("could not write the json file!", ex);
        }

    }

    /**
     *
     * @param src the image source of url to be downloaded
     * @param fileName name of file as output
     * @param path relative path that image should be stored there
     */
    public static void downloadImage(String src, String fileName, String path) {
        try {
            File dir = new File(new StringBuilder(Converter.BASE_PATH).append("images").append(File.separator).append(path).toString());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir.getAbsolutePath().concat(File.separator).concat(fileName));
            URL url = new URL(src);
            try (InputStream in = url.openStream(); OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                for (int b; (b = in.read()) != -1;) {
                    out.write(b);
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new RuntimeException("could not upload the image file!", ex);
        }

    }

}
