package org.studyonline.captcha.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.base.utils.EncryptUtil;
import org.studyonline.captcha.model.CaptchaParamsDto;
import org.studyonline.captcha.model.CaptchaResultDto;
import org.studyonline.captcha.service.AbstractCaptchaService;
import org.studyonline.captcha.service.CaptchaService;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description: Image verification code generator
 * @Author: Chengguang Li
 * @Date: 25/02/2024 3:01 pm
 */
@Service("PicCaptchaService")
@Slf4j
public class PicCaptchaServiceImpl extends AbstractCaptchaService implements CaptchaService {


    @Autowired
    private DefaultKaptcha kaptcha;

    @Resource(name="NumberLetterCaptchaGenerator")
    @Override
    public void setCheckCodeGenerator(CheckCodeGenerator checkCodeGenerator) {
        this.checkCodeGenerator = checkCodeGenerator;
    }

    @Resource(name="UUIDKeyGenerator")
    @Override
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }


    @Resource(name="RedisCaptchaStore")
    @Override
    public void setCheckCodeStore(CheckCodeStore checkCodeStore) {
        this.checkCodeStore = checkCodeStore;
    }


    @Override
    public CaptchaResultDto generate(CaptchaParamsDto captchaParamsDto) {
        GenerateResult generate = generate(captchaParamsDto, 4, "checkcode:", 300);
        String key = generate.getKey();
        String code = generate.getCode();
        String pic = createPic(code);
        CaptchaResultDto captchaResultDto = new CaptchaResultDto();
        captchaResultDto.setAliasing(pic);
        captchaResultDto.setKey(key);
        return captchaResultDto;

    }

    private String createPic(String code) {
        // Generate image verification code
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = kaptcha.createImage(code);

        outputStream = new ByteArrayOutputStream();
        String imgBase64Encoder = null;
        try {
            // Base64 encoding of byte array
            BASE64Encoder base64Encoder = new BASE64Encoder();
            ImageIO.write(image, "png", outputStream);
            imgBase64Encoder = "data:image/png;base64," + EncryptUtil.encodeBase64(outputStream.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
              log.error(e.getMessage());
            }
        }
        return imgBase64Encoder;
    }
}