import java.util.Random;

public class Passenger implements Runnable{
    private final TicketMachine ticketMachine;
    private final int ticketsToPrint;
    private final String name;

    public Passenger(TicketMachine ticketMachine, int ticketsToPrint, String name) {
        this.ticketMachine = ticketMachine;
        this.ticketsToPrint = ticketsToPrint;
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < ticketsToPrint; i++) {
            Ticket ticket = new Ticket(name);
            ticketMachine.printTicket(ticket);

            // Introduce random sleep interval before the next attempt
            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));  // Sleep for a random duration up to 1 second
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
