package cl.capstone.ms_registro_asistencia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cambiar la ruta para acceder a las imágenes guardadas en el sistema de
        // archivos local
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/worker-images/");
    }
}
