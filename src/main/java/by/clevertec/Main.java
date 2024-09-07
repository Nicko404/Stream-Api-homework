package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
//        task16();
//        task17();
//        task18();
//        task19();
//        task20();
//        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().
                filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .peek(animal -> {
                    if (animal.getGender().equals("Female")) {
                        animal.setBread(animal.getBread().toUpperCase());
                    }
                })
                .map(Animal::getBread)
                .forEach(System.out::println);

    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.charAt(0) == 'A')
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println("Female = " + animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count());
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian")));
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(animal -> animal.getGender().equals("Female") || animal.getGender().equals("Male")));
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .anyMatch(animal -> animal.getOrigin().equals("Oceania")));

    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .ifPresent(System.out::println);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparingInt(a -> a.length))
                .ifPresent(System.out::println);
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getAge)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .ifPresent(System.out::println);

    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(Main::personAcceptTask12)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static boolean personAcceptTask12(Person person) {
        int age = LocalDate.now().minusYears(person.getDateOfBirth().getYear()).getYear();
        int recruitmentGroup = person.getRecruitmentGroup();
        return person.getGender().equals("Male") &&
                (age >= 18 && age <= 27) &&
                (recruitmentGroup >= 1 && recruitmentGroup <= 3);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        houses.stream()
                .flatMap(house -> {
                    if (house.getBuildingType().equals("Hospital")) return house.getPersonList().stream();
                    return house.getPersonList().stream()
                            .filter(person ->
                                    LocalDate.now().minusYears(person.getDateOfBirth().getYear()).getYear() < 18 ||
                                            LocalDate.now().minusYears(person.getDateOfBirth().getYear()).getYear() > 55)
                            .toList().stream();
                }).limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();

        Predicate<Car> turkPredicate = car ->
                car.getColor().equals("White") && car.getCarMake().equals("Jaguar");

        Predicate<Car> uzbekPredicate = car ->
                (car.getCarMake().equals("BMW") ||
                        car.getCarMake().equals("Lexus") ||
                        car.getCarMake().equals("Chrysler") ||
                        car.getCarMake().equals("Toyota")) && car.getMass() < 1500;

        Predicate<Car> kazahPredicate = car ->
                car.getColor().equals("Black") &&
                        car.getMass() > 4000 &&
                        (car.getCarMake().equals("GMC") || car.getCarMake().equals("Dodge"));

        Predicate<Car> kirgizPredicate = car ->
                car.getReleaseYear() < 1982 &&
                        (car.getCarModel().equals("Civic") || car.getCarModel().equals("Cherokee"));

        Predicate<Car> russPredicate = car ->
                !(car.getColor().equals("Yellow") ||
                        car.getColor().equals("Red") ||
                        car.getColor().equals("Green") ||
                        car.getColor().equals("Blue")) ||
                        car.getPrice() > 40000;

        Predicate<Car> mongolPredicate = car ->
                car.getVin().contains("59");

        cars.stream()
                .filter(turkPredicate)
                .map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Туркменистан: " + val));

        cars.stream()
                .filter(uzbekPredicate)
                .map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Узбекистан: " + val));

        cars.stream()
                .filter(kazahPredicate)
                .map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Казахстан: " + val));

        cars.stream()
                .filter(kirgizPredicate)
                .map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Кыргызстан: " + val));

        cars.stream()
                .filter(russPredicate)
                .map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Россия: " + val));

        cars.stream()
                .filter(mongolPredicate)
                .map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Монголия: " + val));

        cars.stream()
                .filter(turkPredicate
                        .or(uzbekPredicate)
                        .or(kazahPredicate)
                        .or(kirgizPredicate)
                        .or(russPredicate)
                        .or(mongolPredicate)
                ).map(Car::getMass)
                .map(Double::valueOf)
                .map(val -> val * 7.14)
                .reduce(Double::sum)
                .ifPresent(val -> System.out.println("Общая выручка: " + val));
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();

        Comparator<Flower> countryComparator = Comparator.comparing(Flower::getOrigin);
        Comparator<Flower> priceComparator = Comparator.comparing(Flower::getPrice);
        Comparator<Flower> waterConsumptionComparator = Comparator.comparingDouble(Flower::getWaterConsumptionPerDay);

        flowers.stream()
                .sorted(countryComparator.thenComparing(Flower::getOrigin)
                        .thenComparing(priceComparator)
                        .thenComparing(waterConsumptionComparator.reversed()))
                .filter(flower -> flower.getCommonName().charAt(0) <= 'S' && flower.getCommonName().charAt(0) >= 'C')
                .filter(flower -> flower.isShadePreferred() &&
                        (flower.getFlowerVaseMaterial().contains("Steel") ||
                                flower.getFlowerVaseMaterial().contains("Glass") ||
                                flower.getFlowerVaseMaterial().contains("Aluminum")
                        ))
                .map(flower -> flower.getPrice() + ((1.39 / 1000) * flower.getWaterConsumptionPerDay() * (365 * 5)))
                .reduce(Double::sum)
                .ifPresent(System.out::println);
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println(student.getSurname() + " " + student.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty))
                .forEach((faculty, students1) ->
                        students1.stream()
                                .mapToInt(Student::getAge)
                                .average().ifPresent(studentAgeAvr -> System.out.println(faculty + " " + studentAgeAvr))
                );
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        students.stream()
                .filter(student -> examinations.stream()
                        .filter(exam -> exam.getExam3() > 4)
                        .anyMatch(exam -> exam.getStudentId() == student.getId()))
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty))
                .entrySet().stream()
                .flatMap(entry -> {
                    Map<String, Double> result = new HashMap<>();
                    OptionalDouble average = entry.getValue().stream()
                            .map(student -> examinations.stream()
                                    .filter(exam -> exam.getStudentId() == student.getId())
                                    .mapToDouble(Examination::getExam1)
                                    .average()
                                    .orElse(0))
                            .filter(value -> value > 0)
                            .mapToDouble(Double::doubleValue)
                            .average();
                    result.put(entry.getKey(), average.orElse(0.0));
                    return result.entrySet().stream();
                }).max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println);
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getGroup))
                .forEach((group, students1) -> System.out.println(group + " " + students1.size()));
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty))
                .forEach((faculty, students1) ->
                        students1.stream()
                                .mapToInt(Student::getAge)
                                .min().ifPresent(studentMinAge -> System.out.println(faculty + " " + studentMinAge))
                );
    }
}
