package gol.base.injection;

import java.util.Arrays;

/**
 * Created by Tino on 01.06.2016.
 */
public class Example {

    public static void main(String[] args) {

        final String[] values = new String[] { "a", "b", "c"} ;

        final BeanRepository repo = new BeanRepository.BeanRepositoryBuilder()
                .singleton(PrintService.class, PrintService::new)
                .singleton(MailService.class, MailService::new)
                .singleton(ServiceA.class, ServiceA::new)
                .singleton(ServiceB.class, ServiceB::new)
                .instance(values)
                .prototype(PrototypeServiceWithPostConstruct.class, PrototypeServiceWithPostConstruct::new)
                .singleton(SingletonServiceWithPostConstruct.class, SingletonServiceWithPostConstruct::new)
                .build();

        /*
         * Required Features:
         *  * no Reflection! Should run in sandbox Environment
         *  * Singletons
         *  * Prototypes
         *  * Instances
         *  * Possibility to execute Code after Construction of a Bean
         *
         * Not required:
         *  * resolve cyclic Dependencies
         *
         * Limitations:
         *  * a Bean needs a Default Constructor or a Constructor with the BeanRepository
         *  * a Bean can only be accessed by the Class
         *  * no cyclic References allowed
         *  * creation of singleton Beans is not threadsafe
         */

        // no cyclic reference allowed -> StackOverflow
        // createCyclicReference(repo);

        final MailService mailService = repo.get(MailService.class);
        mailService.sendMail("Donnie", "Hallo Donnie!");

        System.out.println(Arrays.toString(repo.get(String[].class)));

        // Singleton -> only one Instance will be created
        repo.get(SingletonServiceWithPostConstruct.class);
        repo.get(SingletonServiceWithPostConstruct.class);

        // Prototype -> every call to "get" creates a new Instance
        repo.get(PrototypeServiceWithPostConstruct.class);
        repo.get(PrototypeServiceWithPostConstruct.class);
    }

    private static void createCyclicReference(final BeanRepository repo) {
        final ServiceA a = repo.get(ServiceA.class);
        final ServiceB b = repo.get(ServiceB.class);

        a.print("1");
        b.print("2");
    }
}

class PrintService {

    public void print(final String value) {
        System.out.println(value);
    }
}

class MailService {

    private final PrintService printService;

    public MailService(final BeanRepository repo) {
        this.printService = repo.get(PrintService.class);
    }

    public void sendMail(final String to, final String text) {
        printService.print("Message for " + to + ": " + text);
    }
}

class PrototypeServiceWithPostConstruct implements PostConstructible {

    @Override public void onPostConstruct(final BeanRepository repository) {
        System.out.println("New Prototype Service was created");
    }
}

class SingletonServiceWithPostConstruct implements PostConstructible {

    @Override public void onPostConstruct(final BeanRepository repository) {
        System.out.println("New Singleton Service was created");
    }
}

class ServiceA {

    private final ServiceB serviceB;

    public ServiceA(final BeanRepository repo) {
        this.serviceB = repo.get(ServiceB.class);
    }

    public void print(final String value) {
        System.out.println("ServiceA: " + value);
    }
}

class ServiceB {

    private final ServiceA serviceA;

    public ServiceB(final BeanRepository repo) {
        this.serviceA = repo.get(ServiceA.class);
    }

    public void print(final String value) {
        System.out.println("ServiceB: " + value);
    }
}