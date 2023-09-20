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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public int getAge(){
        /*
        Compares the time between the DOB and today's date, can also be used to get
        other forms of time
         */
        Period difference = Period.between(dateOfBirth, LocalDate.now());
        return difference.getYears();
    }
    public void setDataOfBirth(String dateOfBirth) throws Exception {
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
        }
    }
}
