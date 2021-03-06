import java.io.FileNotFoundException;

public class PoetryMain {
    public static void main(String[] args) throws FileNotFoundException {

        WritePoetry poem = new WritePoetry();
        try {
            System.out.println(poem.WritePoem("data/green.txt", "sam", "i", 20, true));
            System.out.println();
            System.out.println(poem.WritePoem("data/Lester.txt", "lester", "was", 30, true));
            System.out.println();
            System.out.println(poem.WritePoem("data/HowMany.txt", "how", "many", 30, false));
            System.out.println();
            System.out.println(poem.WritePoem("data/Zebra.txt", "are","you", 50, true));


        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

}