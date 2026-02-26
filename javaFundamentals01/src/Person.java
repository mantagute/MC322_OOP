public class Person {

    private String name;
    private String occupation;

    public Person(String name, String occupation) {
        this.name = name;
        this.occupation = occupation;
    }

    public String introduce() {
        return "Hello, my name is " + name + "";
    }

    public String describeJob() {
        return "I work as a(n)" + occupation;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }
}
