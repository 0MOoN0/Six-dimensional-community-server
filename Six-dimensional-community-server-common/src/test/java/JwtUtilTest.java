import io.jsonwebtoken.Claims;
import org.junit.Test;
import utils.JwtUtil;

/**
 * @author Leon
 */
public class JwtUtilTest {

    @Test
    public void testDecode(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM1MDQ2MzU4NDg3MDExMzI4Iiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1ODMyOTc0MDgsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE1ODM5MDIyMDh9.-AVEtZebSS-ge5pSil0M8_WsZl5c8e92ZZII2t1s9AU";
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setKey("sdcommunity");
        Claims claims = jwtUtil.parseJWT(token);
        String roles = (String) claims.get("roles");
        String name = claims.getSubject();
        System.out.println("name: "+name+" roles: "+ roles);
    }

}
