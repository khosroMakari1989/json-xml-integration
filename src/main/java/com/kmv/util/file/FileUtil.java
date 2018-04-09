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
 *
 * @author khosro.makari@gmail.com
 */
public final class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());

    static {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
    }

    private FileUtil() {
        throw new IllegalArgumentException("Not Allowed to construct!");
    }

    public static <T> T readXmlOrJsonFile(Class<T> classType, String path, FileType fileType) {
        try {
            File file = new File(new StringBuilder(Converter.BASE_PATH).append(path).toString());
            Map<String, Object> properties = new HashMap<>(1);
            if (fileType == FileType.JSON) {
                properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            }
            JAXBContext jaxbc = JAXBContext.newInstance(new Class[]{classType}, properties);
            Unmarshaller unmarshaller = jaxbc.createUnmarshaller();
//            T type = classType.newInstance();
            try (FileReader fileReader = new FileReader(file);) {
                T type = (T) unmarshaller.unmarshal(fileReader);
                return type;
            }
        } catch (JAXBException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new RuntimeException("could not read the xml-json file!", ex);
        }

    }

    public static <T> void writeObjectToJsonFile(Class<T> classType, T object, String path) {
        try {
            File file = new File(new StringBuilder(Converter.BASE_PATH).append(path).toString());
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            properties.put(MarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");
            JAXBContext jaxbc = JAXBContext.newInstance(new Class[]{classType}, properties);
            Marshaller marshaller = jaxbc.createMarshaller();
//            T type = classType.newInstance();
            try (FileWriter fileWriter = new FileWriter(file);) {
                marshaller.marshal(object, fileWriter);
            }
        } catch (JAXBException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new RuntimeException("could not write the json file!", ex);
        }

    }

    public static void uploadImage(String src, String fileName, String path) {
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
