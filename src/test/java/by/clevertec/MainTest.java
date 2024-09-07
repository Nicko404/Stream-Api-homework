package by.clevertec;


import by.clevertec.model.Animal;
import by.clevertec.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class MainTest {

    @Test
    public void task2Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 29, "Japanese", "Male"),
                new Animal(2, "Tern, arctic", 20, "Ndebele", "Male"),
                new Animal(2, "Huron", 23, "Japanese", "Female")
        );

        List<String> actual = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .peek(animal -> {
                    if (animal.getGender().equals("Female")) {
                        animal.setBread(animal.getBread().toUpperCase());
                    }
                })
                .map(Animal::getBread)
                .toList();

        List<String> expected = List.of("Antelope, roan", "HURON");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void task3Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Male"),
                new Animal(2, "Tern, arctic", 20, "Ndebele", "Male"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        List<String> actual = animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.charAt(0) == 'A')
                .distinct()
                .toList();

        List<String> expected = List.of("Azeri", "Afrikaans");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void task4Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Female"),
                new Animal(2, "Tern, arctic", 20, "Ndebele", "Male"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        long actual = animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count();

        long expected = 2;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void task5Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Female"),
                new Animal(2, "Tern, arctic", 20, "Hungarian", "Male"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        boolean actual = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));

        Assertions.assertTrue(actual);
    }

    @Test
    public void task6Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Female"),
                new Animal(2, "Tern, arctic", 20, "Hungarian", "None"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        boolean actual = animals.stream()
                .allMatch(animal -> animal.getGender().equals("Female") || animal.getGender().equals("Male"));

        Assertions.assertFalse(actual);
    }

    @Test
    public void task7Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Female"),
                new Animal(2, "Tern, arctic", 20, "Hungarian", "None"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        boolean actual = animals.stream()
                .anyMatch(animal -> animal.getOrigin().equals("Oceania"));

        Assertions.assertFalse(actual);
    }

    @Test
    public void task8Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Female"),
                new Animal(2, "Tern, arctic", 20, "Hungarian", "None"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        long actual = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .orElse(0);

        long expected = 39;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void task10Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 39, "Azeri", "Female"),
                new Animal(2, "Tern, arctic", 20, "Hungarian", "None"),
                new Animal(2, "Huron", 33, "Afrikaans", "Female")
        );

        long expected = 92;

        long actual = animals.stream()
                .map(Animal::getAge)
                .reduce(Integer::sum)
                .orElse(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void task11Test() {
        List<Animal> animals = List.of(
                new Animal(1, "Antelope, roan", 25, "Indonesian", "Female"),
                new Animal(2, "Tern, arctic", 20, "Hungarian", "None"),
                new Animal(2, "Huron", 33, "Indonesian", "Female")
        );

        double expected = 29;

        double actual = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void task16Test() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        List<Student> students = List.of(
                new Student(1, "Adams", 18, "Physics", "P-1"),
                new Student(2, "Smith", 17, "Chemistry", "C-3"),
                new Student(3, "Williams", 16, "ComputerScience", "C-3")
        );

        students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println(student.getSurname() + " " + student.getAge()));

        String expected = """
                Smith 17
                Williams 16
                """;

        Assertions.assertEquals(expected, outContent.toString());

        try {
            outContent.close();
            originalOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void task17Test() {
        List<Student> students = List.of(
                new Student(1, "Adams", 18, "Physics", "P-1"),
                new Student(1, "Adams", 18, "Physics", "C-3"),
                new Student(1, "Adams", 18, "Mathematics", "M-2"),
                new Student(1, "Adams", 18, "ComputerScience", "P-1"),
                new Student(2, "Smith", 17, "Chemistry", "C-3"),
                new Student(3, "Williams", 16, "ComputerScience", "P-4")
        );

        List<String> expected = List.of("P-1", "C-3", "M-2", "P-4");

        List<String> actual = students.stream()
                .map(Student::getGroup)
                .distinct()
                .toList();

        Assertions.assertEquals(expected, actual);
    }
}