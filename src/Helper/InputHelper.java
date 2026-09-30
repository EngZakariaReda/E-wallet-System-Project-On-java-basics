package Helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    public Scanner input ;

    public InputHelper(Scanner sc){
        this.input = sc;
    }

    public String readString(String prompt){
        System.out.println(prompt);
        return input.nextLine().trim();
    };

    public Double readDouble(String prompt){
        while (true){
            System.out.println(prompt);
            try {
                double value = input.nextDouble();
                input.nextLine();
                return value;
            }catch (InputMismatchException e){
                System.out.println("pls enter a valid number");
                input.nextLine();
            }
        }
    };

    public Float readFloat(String prompt){
        while (true){
            System.out.println(prompt);
            try {
                Float value = input.nextFloat();
                input.nextLine();
                return value;
            }catch (InputMismatchException e){
                System.out.println("pls enter a valid number");
                input.nextLine();
            }
        }
    };

    public int readInteger(){
        while (true){
            try {
                int value = input.nextInt();
                input.nextLine();
                return value;
            }catch (InputMismatchException e){
                System.out.println("pls enter a valid number");
                input.nextLine();
            }
        }
    };
}
