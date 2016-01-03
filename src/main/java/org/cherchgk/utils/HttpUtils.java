/*
 * Copyright 2012-2016 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.utils;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Утилитарные методы для работы с HTTP.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public final class HttpUtils {

    /**
     * Получение правильно оформленного значения заголовка Content-Disposition
     * в зависимости от используемого бараузера.
     *
     * @param fileName имя файла
     * @param request  обрабатываемый запрос
     * @return значение заголовка Content-Disposition
     */
    public static String getContentDisposition(String fileName, HttpServletRequest request) {
        String correctFileName = getCorrectFileName(fileName);
        try {
            String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            boolean isInternetExplorer = userAgent.contains("msie");
            if (isInternetExplorer) {
                return "attachment; filename=\"" + replaceToHex(correctFileName) + "\"";
            } else {
                return "attachment; filename=\"" + MimeUtility.encodeWord(correctFileName) + "\"";
            }
        } catch (UnsupportedEncodingException e) {
            return "attachment; filename=\"" + correctFileName + "\"";
        }
    }

    private static String getCorrectFileName(String fileName) {
        StringBuilder res = new StringBuilder();
        for (int i = 0, len = fileName.length(); i < len; i++) {
            char c = fileName.charAt(i);
            if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)
                    || !"<>/\\\"|?*~:".contains(String.valueOf(c))) {
                res.append(c);
            } else {
                res.append("_");
            }
        }
        return res.toString();
    }

    private static String replaceToHex(String str) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c <= 255) && !Character.isWhitespace(c)) {
                sb.append(c);
            } else {
                byte[] bytes = Character.toString(c).getBytes("UTF-8");
                for (byte aByte : bytes) {
                    int k = aByte;
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}
