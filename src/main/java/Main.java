import models.User;
import org.springframework.web.client.RestTemplate;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        UserService userService = new UserService(restTemplate, "http://94.198.50.185:7081/api");

        // Получаем sessionId
        userService.getSessionId();

        // Создание пользователя
        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 25);
        String codePart1 = userService.createUser(newUser);

        // Изменение пользователя
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        String codePart2 = userService.updateUser(newUser);

        // Удаление пользователя
        String codePart3 = userService.deleteUser(newUser.getId());

        // Итоговый код
        String finalCode = codePart1 + codePart2 + codePart3;
        System.out.println("Итоговый код: " + finalCode);
    }
}
