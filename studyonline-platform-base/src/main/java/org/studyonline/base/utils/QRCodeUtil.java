package org.studyonline.base.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Description: Generate QR code
 * @Author: Chengguang Li
 * @Date: 03/01/2024 3:09 pm
 */
public class QRCodeUtil {
    /**
     *  Generate QR code
     * @param content URL corresponding to the QR code
     * @param width   QR code image width
     * @param height  QR code image height
     * @return
     */
    public String createQRCode(String content, int width, int height) throws IOException {
        String resultImage = "";
        //In addition to size, the incoming content cannot be empty
        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            //QR code parameters
            @SuppressWarnings("rawtypes")
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            //Specify character encoding as "utf-8"
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //L M Q H four error correction levels from low to high, the error correction level of the designated QR code is M
            //The higher the error correction level, the more errors that can be corrected, the number of error correction codes required also increases, and the corresponding 2D data that can be stored will be reduced.
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            //Set image margins
            hints.put(EncodeHintType.MARGIN, 1);

            try {
                //zxing generates QR code core class
                QRCodeWriter writer = new QRCodeWriter();
                //Convert input text into two dimensions according to specified rules?
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                //Generate QR code image stream
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                //output stream
                ImageIO.write(bufferedImage, "png", os);
                /**
                 * There are no fields such as data:image/png;base64 in front of the native transcoding.
                 * The fields returned to the front end cannot be parsed, so the prefix is added.
                 */
                resultImage = new String("data:image/png;base64," + EncryptUtil.encodeBase64(os.toByteArray()));
                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error generating QR code");
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        //Get the QR code image Base64
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        System.out.println(qrCodeUtil.createQRCode("http://192.168.101.1:63030/orders/alipaytest", 200, 200));
    }
}