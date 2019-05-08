import com.hyh.ease.rent.controller.UserController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Base {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        UserController userController = (UserController) context.getBean("userController");
        //System.out.println(userController.getUserName(0));
    }
}