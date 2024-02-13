package org.studyonline.base.utils;


import java.util.Random;

/**
 * Random string tool
 *
 *
 */
public class RandomStringUtil {
        /**
         * Get a random string of specified length
         *
         * @param length
         * @return
         */
        public static String getRandomString(int length) {
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int number = random.nextInt(3);
                long result = 0;
                switch (number) {
                    case 0:
                        result = Math.round(Math.random() * 25 + 65);
                        sb.append(String.valueOf((char) result));
                        break;
                    case 1:
                        result = Math.round(Math.random() * 25 + 97);
                        sb.append(String.valueOf((char) result));
                        break;
                    case 2:
                        sb.append(String.valueOf(new Random().nextInt(10)));
                        break;
                }
            }
            return sb.toString();
        }

        /**
         * Test verification
         *
         * @param args
         */
        //public static void main(String[] args) {
        //    System.out.println(RandomStringUtil.getRandomString(5));
        //    String  str2 = RandomStringUtils.random(12,"123456789");
        //    System.out.println(str2);
        //}


}
