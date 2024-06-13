package A_H_SP31;

import BankVariations.LockBank;
import BankVariations.SimpleBank;
import BankVariations.SynchroBank;
import Base.Bank;
import Base.Transfer;
import Chrono.Chronometer;
import Chrono.Clock;

public class Main {
    private static void SimpleBankTest() {

        final int ACCOUNTS = 100;  
        final int BALANCE = 1000;

        Bank bank = new SimpleBank(ACCOUNTS, BALANCE);

        for (int i = 0; i < ACCOUNTS; i++) {
            Transfer transfer = new Transfer(bank, i, BALANCE);
            Thread thread = new Thread(transfer);
            thread.start();
        }
    }

    private static void LockBankTest() {

        final int ACCOUNTS = 100; 
        final int BALANCE = 1000; 

        Bank bank = new LockBank(ACCOUNTS, BALANCE);

        for (int i = 0; i < ACCOUNTS; i++) {
            Transfer transfer = new Transfer(bank, i, BALANCE);
            Thread thread = new Thread(transfer);
            thread.start();
        }
    }

    private static void SynchroBankTest() {

        final int ACCOUNTS = 100;  
        final int BALANCE = 1000;

        Bank bank = new SynchroBank(ACCOUNTS, BALANCE);

        for (int i = 0; i < ACCOUNTS; i++) {
            Transfer transfer = new Transfer(bank, i, BALANCE);
            Thread thread = new Thread(transfer);
            thread.start();
        }
    }

    public static void ChronometerTest() {
        Chronometer chronometer = new Chronometer();
        chronometer.start();

        Thread clock5 = new Thread(new Clock(5));
        Thread clock10 = new Thread(new Clock(10));
        Thread clock15 = new Thread(new Clock(15));
        Thread clock20 = new Thread(new Clock(20));

        clock5.start();
        clock10.start();
        clock15.start();
        clock20.start();
    }

    public static void main(String[] args) {
        ChronometerTest();
        SimpleBankTest();
        LockBankTest();
        SynchroBankTest();
    }
}