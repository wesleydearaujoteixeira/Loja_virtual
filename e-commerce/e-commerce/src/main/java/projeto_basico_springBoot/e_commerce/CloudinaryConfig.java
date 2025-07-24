package projeto_basico_springBoot.e_commerce;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dvs2knbji",
                "api_key", "749613696662391",
                "api_secret", "WRasHm-F-u1WXS0XuaD5AXV_v9E"
        );
        return new Cloudinary(config);
    }
}
