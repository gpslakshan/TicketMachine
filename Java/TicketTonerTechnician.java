import java.util.Random;

public class TicketTonerTechnician implements Runnable{
    private final TicketMachine ticketMachine;

    public TicketTonerTechnician(TicketMachine ticketMachine) {
        this.ticketMachine = ticketMachine;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {  // Attempt toner replacement three times

            // Refill toner logic
            ticketMachine.refillToner();

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
