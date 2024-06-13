package Base;
public class Transfer implements Runnable {

    private static final double DELAY = 10;

    private Bank bank;
    private int fromAccount;
    private int maxAmount;

    /**
     * Конструктор
     * @param bank    - банк, між рахунками якого виконується переказ
     */
    public Transfer(Bank bank, int from, int maxAmount) {
        this.bank = bank;
        this.fromAccount = from;
        this.maxAmount = maxAmount;
    }

    /**
     * Реалізація інтерфейсу Runnable
     */
    public void run() {
        try {
            while (true) {
                int toAccount = (int) (Math.random() * bank.getSize());
                double amount = (double) (Math.random() * maxAmount);
                bank.transfer(fromAccount, toAccount, amount);
                Thread.sleep((long) (Math.random() * DELAY));
            }
        } catch (InterruptedException ex) {
            System.out.println("Помилка при роботіз потоками!");
            ex.printStackTrace();
        }
    }
}