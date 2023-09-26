package QuizGame.Kaidpods;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
    private Address address;

    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAddress() {
        return address.toString();
    }

    public int getAge() {
        /*
        Compares the time between the DOB and today's date, can also be used to get
        other forms of time
         */
        try {
            Period difference = Period.between(dateOfBirth, LocalDate.now());
            return difference.getYears();
        }catch (Exception e){
            System.err.println("There is no DOB so the start date and end date cant be compared");
            return 0;
        }
    }
    public void setDateOfBirth(String dateOfBirth) throws Exception {
        //check if a date of birth string was supplied before trying to split it
        if (!dateOfBirth.isEmpty()) {
            String[] data = dateOfBirth.split("-");
            //check that there were three entries
            if (data.length == 3) {
                this.dateOfBirth = LocalDate.of(Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]));
            } else {
                throw new Exception("Date of birth supplied in wrong format.");
            }
        } else {
            System.err.println("There is no date of birth");
        }
    }
    public String getFullName(){

        return (firstName + " " + surname);
    }
}
