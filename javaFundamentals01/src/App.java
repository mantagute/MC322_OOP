public class App {
    public static void main(String[] args) throws Exception {
        Person p1 = new Person("Alice", "Engineer");
        System.out.println(p1.introduce());
        System.out.println(p1.describeJob());
        System.out.println();

        ComputerProgrammer p2 = new ComputerProgrammer("Bruno");
        System.out.println(p2.introduce());
        System.out.println(p2.describeJob());
        System.out.println();

        WebDeveloper p3 = new WebDeveloper("Carla");
        System.out.println(p3.introduce());
        System.out.println(p3.describeJob());
        System.out.println(p3.describeWebsite());
        System.out.println();

        System.out.println(p2 instanceof Person ? "Bruno is a ComputerProgrammer and a Person!" : "Bruno is a ComputerProgrammer but not a Person, ComputerProgrammer should extend Person");
        System.out.println(p3 instanceof ComputerProgrammer ? "Carla is a WebDeveloper and a ComputerProgrammer!" : "Carla is a WebDeveloper but not a ComputerProgrammer, WebDeveloper should extend ComputerProgrammer");
    }
}
