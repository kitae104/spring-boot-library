package kitae.spring.library.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String extraction){
        token.replace("Bearer ", "");   // 토큰에서 "Bearer " 제거

        String[] chunks = token.split("\\."); // 토큰을 "."으로 나누어 배열에 저장(0: header, 1: payload, 2: signature)
        Base64.Decoder decoder = Base64.getUrlDecoder(); // Base64 디코더 생성 - 읽을 수 있는 형태로 변경

        String payload = new String(decoder.decode(chunks[1])); // payload 디코딩
        System.out.println("payload: " + payload);
        String[] entries = payload.split(","); // payload를 ","로 나누어 배열에 저장
        Map<String, String> map = new HashMap<>();

        for(String entry : entries){
            String[] keyValue = entry.split(":"); // entry를 ":"로 나누어 배열에 저장

            if(keyValue[0].equals(extraction)){
               int remove = 1;
                if (keyValue[1].endsWith("}")){
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }
        }
        if(map.containsKey(extraction)){
            return map.get(extraction);    // sub에 해당하는 사용자 이메일 반환
        }
        return null;
    }
}
