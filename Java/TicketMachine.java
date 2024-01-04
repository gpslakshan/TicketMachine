public class TicketMachine implements ServiceTicketMachine {
    private int paperLevel;
    private int tonerLevel;
    private int ticketCounter = 0;
    private final ThreadGroup passengersThreadGroup;
    private final int MAX_PAPER_LEVEL = 300;   // Assumption -> The ticket machine can store a maximum of 300 papers.
    private final int MAX_TONER_LEVEL = 400;   // Assumption -> The ticket machine can have a maximum of 1 toner cartridge, and one toner cartridge can print 400 tickets.

    public TicketMachine(ThreadGroup passengersThreadGroup) {
        this.paperLevel = MAX_PAPER_LEVEL;  // Initial paper level
        this.tonerLevel = MAX_TONER_LEVEL;  // Initial toner level
        this.passengersThreadGroup = passengersThreadGroup;
        System.out.println("Ticket Machine is starting to operate. " + "Paper remaining: " + paperLevel + ", Toner remaining: " + tonerLevel);
    }

    @Override
    public synchronized void printTicket(Ticket ticket) {
        while (paperLevel == 0 || tonerLevel == 0) {
            try {
                // Wait for paper and toner to be available
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Printing logic
        paperLevel--;
        tonerLevel--;
        System.out.println(ticket.getPassengerName() + " printed a ticket. Paper remaining: " + paperLevel + ", Toner remaining: " + tonerLevel);
        ticketCounter++;

        // Notify other threads that paper and toner are available
        notifyAll();
    }

    @Override
    public synchronized void refillPaper() {
        while (paperLevel > 0 && !arePassengerTasksCompleted()) {
            try {
                // Wait until paper is empty
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (arePassengerTasksCompleted()) {
            System.out.println("All passenger tasks are over. Paper technician is ending the refillPaper job.");
            return;
        }

        // Refill paper logic
        System.out.println("Paper technician is refilling paper...");
        paperLevel = MAX_PAPER_LEVEL;  // Refill to the maximum paper level
        System.out.println("Paper refilled. Paper level: " + paperLevel);

        // Notify other threads that paper is available
        notifyAll();
    }

    @Override
    public synchronized void refillToner() {
        while (tonerLevel > 0 && !arePassengerTasksCompleted()) {
            try {
                // Wait until toner is empty
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (arePassengerTasksCompleted()) {
            System.out.println("All passenger tasks are over. Toner technician is ending the refillToner job.");
            return;
        }

        // Refill toner logic
        System.out.println("Toner technician is replacing toner cartridge...");
        tonerLevel = MAX_TONER_LEVEL;  // Refill to the maximum toner level
        System.out.println("Toner refilled. Toner level: " + tonerLevel);

        // Notify other threads that toner is available
        notifyAll();
    }

    public void printTicketMachineStatus(){
        System.out.println("-------------------Final Ticket Machine Status---------------------");
        System.out.println("Paper Level: " + paperLevel);
        System.out.println("Toner Level: " + tonerLevel);
        System.out.println("Total tickets printed: " + ticketCounter);
    }

    /* Checks the completion status of the ticket printing tasks by passengers.
   If all the threads in the passengers thread group have terminated,
   the method returns true. This is crucial to prevent potential
   deadlocks that technicians threads may encounter while waiting
   indefinitely after all tickets are printed. Ensures proper
   termination of the program. */
    private boolean arePassengerTasksCompleted() {
        return this.passengersThreadGroup.activeCount() == 0;
    }
}
