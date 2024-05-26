import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

class Account{
    Scanner s = new Scanner(System.in);
    String path = "transaction.txt";

    public static String Account_no;
    public static String Name;
    private String password;
    public static String Dob;
    public static double balance = currentbal();//fatch current balance from database

    //date and time for entry in passbook
    LocalDateTime t1 =LocalDateTime.now();
    DateTimeFormatter d = DateTimeFormatter.ofPattern("dd-MMM-yyyy\t\tHH:mm:ss\t");
    String dt = t1.format(d);

     //check transaction file exists or not if not then create new file
    public void crpass(){
        File f =new File(path);
        if(f.exists()){}
        else{
            try {
                f.createNewFile();
            } catch (Exception e) {
                System.out.println("error!! (in creating file transaction)");
            }
        
        try (FileWriter fw = new FileWriter(path); // Overwrite mode
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)){
                pw.println("***** State Bank Of India *****");
                pw.println("Account no : 220470116054");
                pw.println("Account Holder Name : Harsh Lunagariya");
                pw.println();
                pw.println("Date            Time                                Ammount     Balance");
                pw.println();
            }
        catch(Exception e){

        }
        }   
    }

    //check balance file exists or not if not then create new file
    public void crbal(){    
        File f =new File("bal.txt");
        if(f.exists()){}
        else{
            try {
                f.createNewFile();
            } catch (Exception e) {
                System.out.println("error!! (in creating file bal)");
            }
        
        try (FileWriter fw = new FileWriter("bal.txt"); // Overwrite mode
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw)){
            pw.print("0");
            
        }
        catch (Exception e) {
            
        }
        }
    }

    //for deposit ammount and print entry in passbook
    public void deposit(double ammount){
        balance += ammount;
        getbalance();
        try(FileWriter fw = new FileWriter(path,true);BufferedWriter bw = new BufferedWriter(fw);PrintWriter pw = new PrintWriter(bw)){
            pw.println(dt+"DEBIT\t"+"      \t\t\t"+ammount+"\t\t"+balance);
            System.out.println("Transaction Sucessfull!!");
        }
        catch(IOException e){
            System.out.println("Transaction Fail!!!");
        }
        writebal(balance);
    }

    //for withdraw ammount and print entry in passbook
    public void withdraw(double ammount){
        balance -= ammount;
        getbalance();//see the balance

        try(FileWriter fw = new FileWriter(path,true);BufferedWriter bw = new BufferedWriter(fw);PrintWriter pw = new PrintWriter(bw))
        {
            pw.println(dt+"     \t"+"CREDIT\t\t\t"+ammount+"\t\t"+balance);
            System.out.println("Transaction Sucessfull!!");
        }
        catch(IOException e){
            System.out.println("Transaction Fail!!!");
        }
        writebal(balance);
    }

    //see balance
    public void getbalance(){
        System.out.println("Available Balance : "+balance);
    }

    //see information of account holder
    public void getInfo(){
        System.out.println("Account no : "+Account_no);
        System.out.println("Account Holder Name : "+Name);
    }

    //change password (step 1 : enter old password if correct then goto step 2)
    public void changePassword(){
        System.out.print("\nEnter password : ");
        String pass = s.nextLine();
        //check password is qual or not
        if(pass.equals(this.password)){
            changepwd(pass);
        }
        else{
            System.out.println("Enter Correct password!!!");
            changePassword();
        }
    }

    //change password (step 2 : enter new password )
    public void changepwd(String pass){
            System.out.print("\nEnter new password : ");
            String p1 = s.nextLine();
            System.out.print("Confirm new password : ");
            String p2 = s.nextLine();

            //check confirm password is same or not
            if(p1.equals(p2)){
                try{
                    this.password = p1;
                    System.out.println("Password is changed !!!");
                }
                catch(Exception e){
                    System.out.println("somthing wrong!!!\nplz try again");
                    changePassword();
                }
            }//check password is new or old
            else if(pass.equals(p1)){
                System.out.println("Enter new password, You enter old password");
                changepwd(pass);
            }
            else{
                System.out.println("New password is incorrect!!!");
                changepwd(pass);
            }
    }

    //see password(enter correct DOB)
    public void seePwd(){
        System.out.println("Enter Date of Birth (dd/mm/yyy) : ");
        String dateofb = s.nextLine();
        if(dateofb.equals(Dob)){
            System.out.println("\n\n\nYour password : "+password);
        }
        else{
            System.out.println("Wrong!!!");
        }
    }

    //see passbook entry
    public void passbook_entry(){
        File f= new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st;
            while((st=br.readLine())!=null){
                System.out.println(st);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //fatch current balance from database(if not found then it gives 0 balance)
    public static double currentbal(){
        try {
            File myObj = new File("bal.txt");
            
            Scanner myReader = new Scanner(myObj);
            return myReader.nextDouble();
            
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        return 0;//if balance not found then return 0
          
    }
 
    //write balance in database if any transaction perform
    public static void writebal(double a) {
        try (FileWriter fw = new FileWriter("bal.txt"); // Overwrite mode
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            // Write the double value to the file
            pw.println(a);
            System.out.println("successfully written to the file.");

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }

    }

    Account(String Account_no,String Name,String password,String Dob){
        this.Account_no = Account_no;
        this.Name = Name;
        this.password = password;
        this.Dob = Dob;
        crpass();
        crbal();
    }
    
}

public class Bank {
    
    //to clear terminal screen
    public static void clscr(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    //entry consol for design
    public static void consol(){
        System.out.println("\n\n\n\n***** State Bank Of India *****\n\n\n\n\n");
        System.out.println("Press Enter To continue... ");
        
        try{
            System.in.read();
        }
        catch(IOException e){
            e.printStackTrace();

        }

        clscr();
    }

    public static void main(String[] args) {
        char c; //this variable for continue tasking
        Scanner s = new Scanner(System.in);
        
        clscr();
        consol();
        
        Account a1 = new Account("220470116054", "Harsh", "0123456","04/11/2004");
        
        do{
            clscr();

            System.out.println("\nSelect your choise :\n\n1. Get Information\n2. Check balance\n3. Deposit\n4. Withdraw\n5. Change Password\n6. See Password\n7. See PassBook Entry\n\n");
            System.out.print("Enter your choise : ");

            int choise = s.nextInt();

            switch (choise) {
                case 1:
                    a1.getInfo();
                    a1.getbalance();
                break;

                case 2:
                    a1.getbalance();
                break;

                case 3:
                    System.out.printf("Enter Ammount : ");
                    int ammount = s.nextInt();
                    a1.deposit(ammount);
                break;

                case 4:
                    System.out.printf("Enter Ammount : ");
                    int cash = s.nextInt();
                    a1.withdraw(cash);
                break;

                case 5:
                    a1.changePassword();
                break;

                case 6:
                    a1.seePwd();
                break;

                case 7:
                    clscr();
                    a1.passbook_entry();
                break;

                default:
                    System.out.println("Enter valid choise !!");
                    break;
            }

            System.out.println("\n\nCan you continue (y or n) : ");
            c= s.next().charAt(0);

        }while(c =='y'||c =='Y');
    }   
}