public class TicketPrintingSystem {
    public static void main(String[] args) {

        ThreadGroup passengers = new ThreadGroup("Passengers");
        ThreadGroup technicians = new ThreadGroup("Technicians");

        TicketMachine ticketMachine = new TicketMachine(passengers);

        // Creating Passenger Threads
        Thread passenger1 = new Thread(passengers, new Passenger(ticketMachine, 300, "Alex"));
        Thread passenger2 = new Thread(passengers, new Passenger(ticketMachine, 400, "Emily"));
        Thread passenger3 = new Thread(passengers, new Passenger(ticketMachine, 200, "Sophia"));
        Thread passenger4 = new Thread(passengers, new Passenger(ticketMachine, 200, "Liam"));

        // Creating Technicians Threads
        Thread paperTechnician = new Thread(technicians, new TicketPaperTechnician(ticketMachine));
        Thread tonerTechnician = new Thread(technicians, new TicketTonerTechnician(ticketMachine));

        // Starting Passengers & Technicians Threads
        passenger1.start();
        passenger2.start();
        passenger3.start();
        passenger4.start();
        paperTechnician.start();
        tonerTechnician.start();

        // Ensure all threads complete execution before printing the final Ticket Machine status
        try {
            passenger1.join();
            passenger2.join();
            passenger3.join();
            passenger4.join();
            paperTechnician.join();
            tonerTechnician.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Print the final Ticket Machine status
        ticketMachine.printTicketMachineStatus();
    }
}
