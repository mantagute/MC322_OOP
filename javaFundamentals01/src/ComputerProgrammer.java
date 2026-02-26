public class ComputerProgrammer extends Person {

    public ComputerProgrammer(String name) {
        super(name, "Computer Programmer");
    }

    public String describeJob() {
        return "I work as a(n)" + super.getOccupation() + "I’m learning OOP and Java!";
    }

}

