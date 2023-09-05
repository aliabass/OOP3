package View;

import java.util.Scanner;

public class IOController implements IOoperation{
    private Scanner scanner = new Scanner(System.in);
    @Override
    public void Write(String news) {
        System.out.println(news);
    }

    @Override
    public String Read() {
        return scanner.next();
    }
}
