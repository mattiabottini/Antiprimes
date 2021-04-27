package antiprimes;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent the sequence of antiprimes found so far.
 */
public class AntiPrimesSequence {

    /**
     * The numbers in the sequence.
     */
    private List<Number> antiPrimes = new ArrayList<>();
    private NumberProcessor processor;
    private List <Observer> observers = new ArrayList<>();
    /**
     * Create a new sequence containing only the first antiprime (the number '1').
     */
    public AntiPrimesSequence() {
        processor = new NumberProcessor(this);
        this.reset();
        processor.start(); //SI
    }


    public void addObserver(Observer observer) {
        observers.add(observer);//viene chiamato nella classe di Run ma compromette solo la stampa grafica
    }
    /**
     * Clear the sequence so that it contains only the first antiprime (the number '1').
     */
    public void reset() {
        antiPrimes.clear();
        antiPrimes.add(new Number(1, 1));
    }

    /**
     * Find a new antiprime and add it to the sequence.
     */
    synchronized public void addAntiPrime(Number number) {
        antiPrimes.add(number);
        for (Observer observer : observers)
            observer.update();
    }

    /**
     * Find a new antiprime and add it to the sequence.
     */
    public void computeNext() {
        try {
            processor.nextAntiPrime(getLast()); //processor è del tipo 'Number Processor'
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the last antiprime found.
     */
    public Number getLast() {
        int n = antiPrimes.size();
        return antiPrimes.get(n - 1);
    }

    /**
     * Return the last k antiprimes found.
     */
    public List<Number> getLastK(int k) {
        int n = antiPrimes.size();
        if (k > n)
            k = n;
        return antiPrimes.subList(n - k, n);
    }
}
