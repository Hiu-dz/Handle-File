import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanModel {
    private String name;
    private int age;
    private String address;
    private final Path path = Paths.get(".\\HumanData.txt");

    public HumanModel() {
    }

    public HumanModel(String name, int age, String address) {
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

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name + "," + age + "," + address;
    }

    /**
     * Method used to get data from list to file.
     *
     * @Method getPath(): property path of file.
     * @List nameList: input list name follow List<HumanModel>.
     * <p>
     * Idea: Use for loop to get the elements from the data row of the nameList to write to the file
     */
    private void writeHumanFile(List<HumanModel> nameList) {
        try (BufferedWriter bw = Files.newBufferedWriter(getPath())) {
            for (HumanModel t : nameList) {
                bw.write(t.getName() + "," + t.getAge() + "," + t.getAddress() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for get data from file to list.
     *
     * @return collection list humanModel.
     * **********
     * Idea:
     * - Use while loop:
     * + Add each line of data from the file to stringList.
     * + Use words to get the elements of data after separating the string by ",".
     * + Get elements from words to add a new row data for humanModelList.
     * @Method getPath(): property path of file.
     * @List stringList: save row of data separated by "," from file.
     * @Array words: array to save the elements of stringList.
     * @List humanModelList: collection for list of HumanModel.
     */
    private List<HumanModel> humanList() {
        List<String> stringList = new ArrayList<>();
        List<HumanModel> humanModelList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getPath().toUri()))) {
            int i = 0;
            while (scanner.hasNext()) {
                stringList.add(scanner.nextLine());
                String[] words = stringList.get(i++).split(",");
                humanModelList.add(new HumanModel(words[0], Integer.parseInt(words[1]), words[2]));
            }
        } catch (Exception e) {
            System.out.println("error");
        }

        return humanModelList;
    }

    /**
     * Method used for find data by name.
     *
     * @param name: name want to find in nameList
     * @return true if the name is in the nameList, else is false
     * **********
     * Idea: Lambda for each to add the name of humanList to nameList.
     * @List nameList: collection for name retrieved from list
     * @Method humanList(): return list of HumanModel class.
     */
    private boolean isHumanExist(String name) {
        List<String> nameList = new ArrayList<>();
        humanList().forEach(h -> nameList.add(h.getName()));

        return nameList.contains(name);
    }

    /**
     * Method used for show data from list humanList.
     *
     * @Method humanList(): return list of HumanModel class.
     * **********
     * Idea: Use for each to print rows data of humanList().
     */
    public void showHumanList() {
        humanList().forEach(System.out::println);
    }

    /**
     * Method used for add human to file HumanData.txt
     *
     * @Variable nameHuman: input for name of human.
     * @Variable ageHuman: input for age of human.
     * @Variable addressHuman: input for address of human.
     * @Method isHumanExist(): return true if nameHuman is in list human, else is false.
     * **********
     * Idea:
     * - Create input for information of human.
     * - Use isHumanExist() to check name exists in the list human.
     * - Get output of human and append to file.
     */
    public void addHuman() {
        Scanner scannerName = new Scanner(System.in);
        Scanner scannerAge = new Scanner(System.in);
        Scanner scannerAddress = new Scanner(System.in);

        System.out.print("Enter the name: ");
        String nameHuman = scannerName.nextLine();

        if (!isHumanExist(nameHuman)) {
            System.out.print("Enter the age: ");
            int ageHuman = scannerAge.nextInt();
            System.out.print("Enter the address: ");
            String addressHuman = scannerAddress.nextLine();

            try (FileWriter ff = new FileWriter(getPath().toFile(), true);
                 BufferedWriter bb = new BufferedWriter(ff);
                 PrintWriter p = new PrintWriter(bb)) {
                p.println(nameHuman + "," + ageHuman + "," + addressHuman);
                System.out.println("ALERT: ADD SUCCESSFULLY");
            } catch (IOException io) {
                io.printStackTrace();
            }
        } else {
            System.out.println("ERROR: NAME IS ALREADY EXIST. PLEASE TRY AGAIN");
            this.addHuman();
        }
    }

    /**
     * Method used for delete row data of file HumanData.txt
     *
     * @Variable nameHuman: input for name of human.
     * @Method isHumanExist(): return true if nameHuman is in list human, else is false.
     * @Method humanList(): return list of HumanModel class.
     * @Method createHumanFile(): write for file HumanData.txt.
     * @List humanModelList: taken from humanList().
     * **********
     * Idea:
     * - Create input for name of human.
     * - Create humanModelList taken data of humanList().
     * - Use isHumanExist() to check name exists in the list human.
     * - Use removeIf to delete row data of humanModelList by nameHuman.
     * - Use createHumanFile() to rewrite file HumanData.txt.
     */
    public void deleteHuman() {
        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String nameHuman = scannerName.nextLine();

        if (isHumanExist(nameHuman)) {
            List<HumanModel> humanModelList = this.humanList();
            humanModelList.removeIf(r -> r.getName().equals(nameHuman));
            writeHumanFile(humanModelList);
        } else {
            System.out.println("ERROR: NAME DOSE NOT EXIST. PLEASE TRY AGAIN.");
            this.deleteHuman();
        }
    }

    /**
     * Method used for update row data of file HumanData.txt.
     *
     * @Variable nameHuman: input for name of human.
     * @Variable nameUpdate: input for update name of human.
     * @Variable ageUpdate: input for update age of human.
     * @Variable addressUpdate: input for update address of human.
     * @Method isHumanExist(): return true if nameHuman is in list human, else is false.
     * @Method createHumanFile(): write for file HumanData.txt.
     * @List humanModelList: taken from humanList().
     * <p>
     * Idea:
     * - Create input for name of human to get name want update.
     * - Use isHumanExist() to check name exists in the list human.
     * + If isHumanExist() is true -> next step.
     * + If isHumanExist() is false -> reuse this method.
     * - Create input for update information of human.
     * - Use isHumanExist() to check update name exists in the list human.
     * + If isHumanExist() is true -> reuse this method.
     * + If isHumanExist() is false -> perform update human.
     * - Create humanModelList taken data of humanList().
     * - Use for each to find name want update and update information of human.
     * - Use createHumanFile() to get data from humanModelList set file HumanData.txt
     */
    public void updateHuman() {
        Scanner scannerName = new Scanner(System.in);
        System.out.print("Enter the name for update: ");
        String nameHuman = scannerName.nextLine();

        if (isHumanExist(nameHuman)) {
            Scanner scannerUpdateName = new Scanner(System.in);
            Scanner scannerUpdateAge = new Scanner(System.in);
            Scanner scannerUpdateAddress = new Scanner(System.in);

            System.out.print("Enter update the name: ");
            String nameUpdate = scannerUpdateName.nextLine();
            System.out.print("Enter update the age: ");
            int ageUpdate = scannerUpdateAge.nextInt();
            System.out.print("Enter update the address: ");
            String addressUpdate = scannerUpdateAddress.nextLine();

            if (!isHumanExist(nameUpdate)) {
                List<HumanModel> humanModelList = this.humanList();
                humanModelList.forEach(t -> {
                    if (nameHuman.equals(t.getName())) {
                        t.setName(nameUpdate);
                        t.setAge(ageUpdate);
                        t.setAddress(addressUpdate);
                        System.out.println("ALERT: UPDATE SUCCESSFULLY");
                    }
                });
                writeHumanFile(humanModelList);
            } else {
                System.out.println("ERROR: NAME ALREADY EXIST. PLEASE TRY AGAIN.");
                this.updateHuman();
            }
        } else {
            System.out.println("ERROR: NAME DOSE NOT EXIST. PLEASE TRY AGAIN.");
            this.updateHuman();
        }
    }

    /**
     * Method used for delete content in file HumanData.txt
     * <p>
     * Idea: Rewrite file to empty.
     */
    public void clearHumanFile() {
        try {
            PrintWriter writer = new PrintWriter(getPath().toFile());
            writer.print("");
            writer.close();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    /**
     * Method used for check exist of file.
     *
     * @param fileName: name of file
     * @return true if file exists, else false.
     *
     * Idea: check file in project.
     */
    private boolean isFileExist(String fileName) {
        File myObj = new File(fileName);
        return myObj.exists();
    }

    /**
     * Method used for create file.
     *
     * @Variable fileName: input of file name.
     * @Method isFileExist(): check exist of file.
     * **********
     * Idea:
     *  - Check exist of file.
     *   + If file does not exist -> create new file.
     *   + If file exist -> input again.
     */
    public void createFile() {
        Scanner scannerFile = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = scannerFile.nextLine() + ".txt";

        if (!isFileExist(fileName)) {
            try {
                Files.createFile(Paths.get(fileName));
                System.out.println("ALERT: FILE HAS BEEN CREATED.");
            } catch (IOException io) {
                io.printStackTrace();
            }
        } else {
            System.out.println("ERROR: FILE ARE ALREADY EXIST. PLEASE TRY AGAIN.");
            this.deleteFile();
        }
    }

    /**
     * Method used for delete file.
     *
     * @Variable fileName: input of file name.
     * @Method isFileExist(): check exist of file.
     * **********
     * Idea:
     *  - Check exist of file.
     *   + If file exist -> delete file.
     *   + If file does not exist -> input again.
     */
    public void deleteFile() {
        Scanner scannerFile = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = scannerFile.nextLine() + ".txt";

        if (isFileExist(fileName)) {
            try {
                Files.delete(Paths.get(fileName));
                System.out.println("ALERT: FILE HAS BEEN DELETED.");
            } catch (IOException io) {
                io.printStackTrace();
            }
        } else {
            System.out.println("ERROR: FILE DOES NOT EXIST. PLEASE TRY AGAIN.");
            this.deleteFile();
        }
    }
}
