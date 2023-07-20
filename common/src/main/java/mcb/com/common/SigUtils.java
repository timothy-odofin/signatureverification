package mcb.com.common;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Arrays;
import java.util.Base64;

public class SigUtils {
    public static byte[] generateDigitalSignature(String name) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        Signature sign = Signature.getInstance("SHA256withDSA");
        sign.initSign(keyPair.getPrivate());
        sign.update(name.getBytes());
        return sign.sign();
    }
    public static String generateBase64PdfSignature(String name) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = binaryToImage(generateDigitalSignature(name));
            // Create a PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Write the image to the PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

// Get the height of the page
            float pageHeight = page.getMediaBox().getHeight();

// Assuming your image has a height of 'imageHeight', calculate the y-coordinate to place the image at the top
            float imageHeight = 300; //
            float yCoordinate = pageHeight - 100 - imageHeight; // Adjust the vertical position as needed

            contentStream.drawImage(LosslessFactory.createFromImage(document, image), 100, yCoordinate); // Adjust the position as needed
            contentStream.close();

            // Save the PDF to ByteArrayOutputStream
            document.save(baos);
            document.close();

            // Convert the PDF to a Base64 string
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Convert the binary digital signature to a graphical representation as an image
    public static BufferedImage binaryToImage(byte[] binaryData) {
        int width = 300; // Image width (pixels)
        int height = 300; //binaryData.length * 8 Image height (pixels)
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set the background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw the graphical representation based on the binary data
        int y = 0;
        for (byte b : binaryData) {
            for (int i = 7; i >= 0; i--) {
                int bit = (b >> i) & 1;
                g2d.setColor(bit == 1 ? Color.BLACK : Color.WHITE);
                g2d.fillRect(0, y, width, 1);
                y++;
            }
        }
        g2d.dispose();
        return image;
    }

    // Convert the image to a Base64-encoded string
    public static String generateBase64ImageSignature(String name) throws Exception {
        BufferedImage image =binaryToImage(generateDigitalSignature(name));
        java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", os);
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }
    public static String generateBase64ImageSignature(byte[] bytes) throws Exception {
        BufferedImage image =binaryToImage(bytes);
        java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", os);
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }
    public static SignatureValidationInternal compareSignatures(String existingRecord, String newRecord) {

        byte[] signature1 = new byte[0];
        byte[] signature2 = new byte[0];
        SignatureValidationInternal signatureValidationInternal;
        try {
            signature1 = generateDigitalSignature(existingRecord);
            signature2  = generateDigitalSignature(newRecord);
            signatureValidationInternal= new SignatureValidationInternal(generateBase64ImageSignature(signature2),Arrays.equals(signature1, signature2));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return signatureValidationInternal;
    }
}
