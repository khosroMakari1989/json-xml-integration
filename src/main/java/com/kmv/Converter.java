package com.kmv;

import com.kmv.domain.coah.Content;
import com.kmv.domain.coah.Hotel;
import com.kmv.domain.giata.Giata;
import com.kmv.util.file.FileType;
import com.kmv.util.file.FileUtil;
import java.net.URL;

/**
 *
 * This class converts and integreates json&xml files into one JSON file, and
 * will download the images via URLs which have been put inside the files.
 *
 * Coah and Giata are two type of data to be read from files and write them back
 * as one integrated json file
 *
 * @author khosro.makari@gmail.com
 */
public class Converter {

    public static final String BASE_PATH;

    //The path will be a "data" folder beside the project.
    static {
        URL url = Converter.class.getProtectionDomain().getCodeSource().getLocation();
        String path = url.getFile();
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        BASE_PATH = path.concat("/data/");
    }

    public static void main(String[] args) {
        System.out.println("Please wait...");
        convert();
        System.out.println("Done!");
    }

    /**
     * reads data from 6 files of XML and JSON which should be exist in the
     * folder 'data', then integrates them as one JSON file with the name
     * 'output.json'
     */
    public static void convert() {
        Content content = FileUtil.readXmlOrJsonFile(Content.class, "3956-coah.xml", FileType.XML);
        Content coah2 = FileUtil.readXmlOrJsonFile(Content.class, "162838-coah.xml", FileType.XML);
        Content coah3 = FileUtil.readXmlOrJsonFile(Content.class, "594608-coah.json", FileType.JSON);

        Giata giata = FileUtil.readXmlOrJsonFile(Giata.class, "3956-giata.xml", FileType.XML);
        Giata giata2 = FileUtil.readXmlOrJsonFile(Giata.class, "162838-giata.xml", FileType.XML);
        Giata giata3 = FileUtil.readXmlOrJsonFile(Giata.class, "411144-giata.xml", FileType.XML);

        content.getHotels().get(0).setGiata(giata.getGiataData());
        coah2.getHotels().get(0).setGiata(giata2.getGiataData());

        content.getHotels().add(coah2.getHotels().get(0));
        content.getHotels().add(coah3.getHotels().get(0));

        Hotel hotel = new Hotel();
        hotel.setGiata(giata3.getGiataData());
        content.getHotels().add(hotel);

        uploadContentImages(content);
        FileUtil.writeObjectToJsonFile(Content.class, content, "output.json");
    }

    /**
     * downloads images of content asynchronously, using Java 8 parallel streams
     *
     * @param content the object which contains data along with image URLs
     */
    private static void uploadContentImages(Content content) {
        content.getHotels().forEach(hotel
                -> {
            if (hotel.getImages() != null) {
                hotel.getImages().parallelStream().forEach(image -> {
                    FileUtil.downloadImage(image.getUrl(), image.getUrl().substring(
                            image.getUrl().lastIndexOf("=") + 1, image.getUrl().length() - 1), hotel.getGiata_id().toString().concat("-coah"));
                });
            }
            if (hotel.getGiata() != null && hotel.getGiata().getBildfiles() != null) {
                hotel.getGiata().getBildfiles().parallelStream().forEach(image -> {
                    FileUtil.downloadImage(image.getUrl(), image.getId(), hotel.getGiata().getGeoData().getGiataID().toString().concat("-giata"));
                });
            }
        });
    }
}
