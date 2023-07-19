package mcb.com.api.utils;


import java.util.Base64;
import java.util.regex.Pattern;

public class TestUtil {
    public static boolean isBase64Image(String input) {
        // Regular expression pattern for Base64 encoded image
        Pattern pattern = Pattern.compile("^data:image/\\w+;base64,.*$");

        // Check if the input matches the pattern
        if (pattern.matcher(input).matches()) {
            try {
                // Decode the Base64 string to bytes
                byte[] decodedBytes = Base64.getDecoder().decode(input.split(",")[1]);

                // Validate that the decoded bytes represent a valid image
                // For example, you can use libraries like ImageIO to validate the image format
                // For simplicity, we'll just check if the decoded bytes are not empty
                return decodedBytes.length > 0;
            } catch (IllegalArgumentException e) {
                // Invalid Base64 string
                return false;
            }
        }

        return false;
    }

    public static boolean isValidBase64PDF(String base64String) {
        try {
            // Decode the Base64 string
            byte[] decodedData = Base64.getDecoder().decode(base64String);

            // Check if the decoded data starts with the PDF file signature
            return startsWithPDFSignature(decodedData);
        } catch (IllegalArgumentException e) {
            // If decoding fails, it's not a valid Base64 string
            return false;
        }
    }

    private static boolean startsWithPDFSignature(byte[] data) {
        if (data.length >= 4) {
            // Check the first four bytes for the PDF signature "%PDF"
            return (data[0] == 0x25 && data[1] == 0x50 && data[2] == 0x44 && data[3] == 0x46);
        }
        return false;
    }
}
