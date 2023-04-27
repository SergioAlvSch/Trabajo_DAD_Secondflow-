package es.SecondFlow.ServicioInterno;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.SecondFlow.Entidades.Usuario;
import es.SecondFlow.Entidades.Producto;
@Service
public class ServicioInternoEmail {
    private static final String MAIL_SERVICE_URL = "http://10.100.139.126:80";
    public static boolean sendRegisterEmail(Usuario usuario) {
        try {
            Email email = new Email(usuario.getCorreo(), "Registro SecondFlow","¡Gracias "+usuario.getNombreUsuario()+" por registrarte en SecondFlow, disfruta de nuestra web!");
            HttpEntity<Email> httpEntity = new HttpEntity<>(email);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> res =restTemplate.postForEntity(MAIL_SERVICE_URL+"/email", httpEntity, Void.class);

            if(res.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Enviado correctamente");
                return true;
            }else{
                System.out.println("Error enviando:"+res.getStatusCode());
                return false;
            }
        }catch (Exception e) {
            System.out.println("Error enviando:"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendCompraVentaEmail(Producto producto) {
        try {
            Email email = new Email(producto.getVendedor().getCorreo(), "Confirmacion de venta","¡Felicidades "+producto.getVendedor().getNombreUsuario()+",usted a vendido correctamente el producto: "+producto.getNombre()+"! Gracias por confiar en nosotros");
            HttpEntity<Email> httpEntity = new HttpEntity<>(email);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> res =restTemplate.postForEntity(MAIL_SERVICE_URL+"/email", httpEntity, Void.class);

            if(res.getStatusCode() == HttpStatus.CREATED) {
                email = new Email(producto.getComprador().getCorreo(), "Confirmacion de compra","¡Felicidades "+producto.getComprador().getNombreUsuario()+",usted a comprado correctamente el producto: "+producto.getNombre()+"! Gracias por confiar en nosotros");
                httpEntity = new HttpEntity<>(email);
                res =restTemplate.postForEntity(MAIL_SERVICE_URL+"/email", httpEntity, Void.class);
                if(res.getStatusCode() == HttpStatus.CREATED) {
                    System.out.println("Los correos se enviaron correctamente");
                    return true;
                }else{
                    System.out.println("Error enviando:"+res.getStatusCode());
                    return false;
                }
            }else{
                System.out.println("Error enviando:"+res.getStatusCode());
                return false;
            }
        }catch (Exception e) {
            System.out.println("Error enviando:"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
