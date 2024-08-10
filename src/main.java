import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


//!----------------------Car class-----------------------//

class Car {

    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    // !------------constructor
    public Car(String carId, String brand, String model, double basePricePerDay) {

        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;

    }

    // !---------------Getters
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentaldays) {
        return basePricePerDay * rentaldays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

}

// !----------------------Customer class-----------------------//

class Customer {

    private String name;
    private String customerId;
    private int age;

    public Customer(String customerId, String name, int age) {

        this.name = name;
        this.customerId = customerId;
        this.age = age;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getname() {
        return name;
    }

    public int getage() {
        return age;
    }
}

// !----------------------Rental class-----------------------//

class Rental {

    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getdays() {
        return days;
    }
}

// !--------------------CarRentalsystem class---------------------//

class CarRentalSystem {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> Rentals;

    public CarRentalSystem() {

        cars = new ArrayList<>();
        customers = new ArrayList<>();
        Rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addcustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            Rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car) {

        car.returnCar();
        Rental rentaltToRemove = null;
        for (Rental rental : Rentals) {
            if (rental.getCar() == car) {
                rentaltToRemove = rental;
                break;
            }

        }
        if (rentaltToRemove != null) {
            Rentals.remove(rentaltToRemove);
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~ Welcome to Car Rental System ~~~~~~~~~~~~~~~~~~~~");
            System.out.println();
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choise : ");
            System.out.println("__________________________________");

            int choise = sc.nextInt();
            sc.nextLine();

            if (choise == 1) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~ Rent a car ~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Enter your name : ");
                String customerName = sc.next();

                System.out.println("Enter your age : ");
                int age = sc.nextInt();

                System.out.println("~~~~~~~~~~( Available cars )~~~~~~~~~~");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.println("Enter the car Id you want to rental : ");
                String carId = sc.next();

                System.out.print("Enter the number of days for rental: ");
                int rentaldays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName, rentaldays);
                addcustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentaldays);
                    System.out.println("~~~~~~~~~~ Rental Information ~~~~~~~~~~");
                    System.out.println();
                    System.out.println("Customer Name: " + newCustomer.getname());
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Age: " + age);
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentaldays);
                    System.out.println("___________________________________________");
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    System.out.println("___________________________________________");

                    System.out.print(" Confirm rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentaldays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choise == 2) {
                System.out.println("~~~~~~~~~~ Return a Car ~~~~~~~~~~");
                System.out.print("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : Rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getname());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choise == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }

        }
        System.out.println("\nThank you for using the Car Rental System!");

    }
}

// !----------------------Main class-----------------------//

public class main {

    public static void main(String[] args) {

        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 8000.0);
        Car car2 = new Car("C002", "BMW", "BMW X5", 17000.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 9500.0);
        Car car4 = new Car("C004", "Toyota", "Fortuner", 14000.0);
        Car car5 = new Car("C005", "Lemborginni", "Urus", 18400.0);
        Car car6 = new Car("C006", "Audi", "Audi Q7", 16500.0);
        System.out.println("__________________________________________________");

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);

        rentalSystem.menu();
    }
}
