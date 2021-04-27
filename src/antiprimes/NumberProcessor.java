package antiprimes;

import java.util.logging.Logger;

public class NumberProcessor extends Thread{

        private final static Logger LOGGER = Logger.getLogger(NumberProcessor.class.getName()); //???????????
        private Number request = null;   ///classe NUMBER ha il Value e i Divisori
        private AntiPrimesSequence sequence;

        public NumberProcessor(AntiPrimesSequence sequence) {
            this.sequence = sequence;
        }


        public void run() {
            LOGGER.info("Processor ready"); ////////////////////////credo che faccia la stampa
            acceptRequests();//metodo qui dentro che pone request a null e sveglia un thread
            // Process forever.  It would be better to design a mechanism to gracefully shutdown the thread.
            for ( ; ; ) {
                try {

                    Number n = getNextToProcess(); //questo è il numero che analizzo
                    //getNextToProcess() metodo qui presente che ritorna request s'è !null
                    System.out.println(n.getValue());
                    Number m = AntiPrimes.nextAntiPrimeAfter(n); //questo è il prossimo numero antiprimo
                    //che viene calcolato dalla classe AntiPrimes
                    System.out.println(m.getValue() + " with " + m.getDivisors() + " divisors");
                    sequence.addAntiPrime(m); //lo aggiungo alla sequenza
                    acceptRequests();//metodo qui dentro che pone request a null e sveglia un thread

                } catch (InterruptedException e) {
                    LOGGER.severe(e.getMessage());
                    break;
                }
            }
        }


        synchronized public void nextAntiPrime(Number n) throws InterruptedException {
            while (request != null) {
                if (request.getValue() == n.getValue())
                    return;
                wait();
            }
            request = n;
            notify();  //sveglia un thread
        }
        synchronized private Number getNextToProcess() throws InterruptedException {
            while (request == null)
                wait();
            return request;
        }

        synchronized private void acceptRequests() {
            request = null;
            notify();
        }
    }
