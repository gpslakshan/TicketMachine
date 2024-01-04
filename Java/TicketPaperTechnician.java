import java.util.Random;

public class TicketPaperTechnician implements Runnable{
    private final TicketMachine ticketMachine;

    public TicketPaperTechnician(TicketMachine ticketMachine) {
        this.ticketMachine = ticketMachine;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {  // Attempt to refill paper three times

            // Refill paper logic
            ticketMachine.refillPaper();

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
