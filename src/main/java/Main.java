import config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static ApplicationContext ctx = null;

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        System.out.println("eeeee");
    }
}
