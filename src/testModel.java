import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class testModel {
    private String name;
    private int age;
    private String address;

    public testModel() {
    }

    public testModel(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + "," + age + "," + address;
    }

    public void showTest() {
        List<String> testModelList = new ArrayList<>();
        List<testModel> modelList = new ArrayList<>();
        File f = new File("E://java//test//test.txt");
        try (Scanner scanner = new Scanner(f)) {
            int i = 0;
            while (scanner.hasNext()) {
                testModelList.add(scanner.nextLine());
                String[] words = testModelList.get(i++).split(",");
                modelList.add(new testModel(words[0], Integer.parseInt(words[1]), words[2]));
            }
            modelList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("error");
        }

        /*String file = "E:\\java\\test\\test.txt";
        try (Stream<String> stream = Files.lines(Paths.get(file))) {

            stream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void addTest() {
        List<String> testModelList = new ArrayList<>();
        File f = new File("E:\\java\\test\\test.txt");
        try (Scanner scanner = new Scanner(f);
             FileWriter ff = new FileWriter("E:\\java\\test\\test.txt", true);
             BufferedWriter bb = new BufferedWriter(ff);
             PrintWriter p = new PrintWriter(bb)) {
            Scanner scannerName = new Scanner(System.in);
            Scanner scannerAge = new Scanner(System.in);
            Scanner scannerAddress = new Scanner(System.in);

            System.out.print("Enter the name: ");
            String nameTest = scannerName.nextLine();
            List<String> nameList = new ArrayList<>();
            int i = 0;
            while (scanner.hasNext()) {
                testModelList.add(scanner.nextLine());
                String[] words = testModelList.get(i++).split(",");
                nameList.add(words[0]);
            }
            boolean bError = true;
            while ((nameList.contains(nameTest)) && bError) {
                System.out.println("Name is already exits.");
                System.out.print("Enter the name: ");
                nameTest = scannerName.nextLine();

                if (!nameList.contains(nameTest)) {
                    bError = false;
                }
            }
            System.out.print("Enter the age: ");
            int ageTest = scannerAge.nextInt();
            System.out.print("Enter the address: ");
            String addressTest = scannerAddress.nextLine();
            p.println(nameTest + "," + ageTest + "," + addressTest);
        } catch (Exception fn) {
            fn.printStackTrace();
        }
    }

    public void deleteTest() {
        String buffer = "";
        try {
            Scanner scan = new Scanner(new File("filename.txt"));
            while (scan.hasNext()) {
                buffer = scan.nextLine();

                String[] splittedLine = buffer.split(" ");
                if (splittedLine[0].equals(s)) {
                    buffer = "";
                } else {
                    //print some message that tells you that the string not found
                }
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occured while searching in file!");
        }

    }

    public void clearTest() {
        try {
            PrintWriter writer = new PrintWriter("E:\\java\\test\\test.txt");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }
}
