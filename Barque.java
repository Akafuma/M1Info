// -*- coding: utf-8 -*-

import java.util.ArrayList;
import java.util.Random;

public class Barque{
    public enum Berge { NORD, SUD }
    final static int nbNainsAuNord = 6;
    final static int nbHobbitsAuNord = 2;
    final static int nbNainsAuSud = 4;
    final static int nbHobbitsAuSud = 2;
    
    private volatile boolean charge;
    private volatile boolean decharge;
    
    private ArrayList<Nain> nainDansLaBarque = new ArrayList();;
    private ArrayList<Hobbit> hobbitDansLaBarque = new ArrayList();
    
    private int nbDansLaBarque()
    {
    	return nainDansLaBarque.size() + hobbitDansLaBarque.size();
    }
    
    public static void main(String[] args){
        Barque laBarque = new Barque();
        new Passeur(laBarque);
        for(int i=0; i<nbNainsAuNord; i++) {
            new Nain(laBarque,Berge.NORD).setName("NN-"+i);
        }
        for(int i=0; i<nbHobbitsAuNord; i++) {
            new Hobbit(laBarque,Berge.NORD).setName("HN-"+i);
        }
        for(int i=0; i<nbNainsAuSud; i++) {
            new Nain(laBarque,Berge.SUD).setName("NS-"+i);
        }
        for(int i=0; i<nbHobbitsAuSud; i++) {
            new Hobbit(laBarque,Berge.SUD).setName("HS-"+i);
        }
    }

    /* Code associé au passeur à compléter */
    public synchronized void accosterLaBerge(Berge berge)
    {
    	
    }
    
    public synchronized void charger()
    {
    	charge = true;
    	notifyAll();
    	while(nbDansLaBarque() != 4)
    	{
    		try
    		{
    			wait();
    		}
    		catch(InterruptedException e)
			{}
    	}
    	//Attendre le chargement complet
    	charge = false;
    }
    
    public synchronized void decharger()
    {
    	decharge = true;
    	notifyAll();
    	//Attendre le d�chargement complet
    	while(nbDansLaBarque() != 0)
    	{
    		try
    		{
    			wait();
    		}
    		catch(InterruptedException e)
			{}
    	}
    	decharge = false;
    }
    /* Code associé aux nains à compléter */
    public synchronized void embarquerUnNain(Berge origine)
    {
    	while(!charge)
    	{
    		try
    		{
    			wait();
    		}
    		catch(InterruptedException e)
    		{}
    	}
    }
    public synchronized void debarquerUnNain(Berge origine)
    {
    	while(!decharge)
    	{
    		try
    		{
    			wait();
    		}
    		catch(InterruptedException e)
    		{}
    	}
    }
    /* Code associé aux hobbits à compléter */
    public synchronized void embarquerUnHobbit(Berge origine)
    {
    	while(!charge)
    	{
    		try
    		{
    			wait();
    		}
    		catch(InterruptedException e)
    		{}
    	}
    }
    
    public synchronized void debarquerUnHobbit(Berge origine)
    {
    	while(!decharge)
    	{
    		try
    		{
    			wait();
    		}
    		catch(InterruptedException e)
    		{}
    	}
    }
}    


class Passeur extends Thread {
    public Barque laBarque;
    public Passeur(Barque b) {
        this.laBarque = b;
        start();
    }
    public void run() {
        laBarque.accosterLaBerge(Barque.Berge.NORD);
        // Indique que la barque, initialement vide, est au NORD.
        System.out.println("PASSEUR> La barque est au NORD: montez!");
        while(true){
            laBarque.charger();
            System.out.println("PASSEUR> La barque est correctement chargée.");
            System.out.println("PASSEUR> Je rame vers le SUD.");
            try { sleep(1000); }
            catch (InterruptedException e) {e.printStackTrace();}
            laBarque.accosterLaBerge(Barque.Berge.SUD);
            System.out.println("PASSEUR> La barque est au SUD: descendez!");
            laBarque.decharger();
            System.out.println("PASSEUR> La barque est vide: montez!");
            laBarque.charger();
            System.out.println("PASSEUR> La barque est correctement chargée.");
            System.out.println("PASSEUR> Je rame vers le NORD.");
            try { sleep(1000); }
            catch (InterruptedException e) {e.printStackTrace();}
            laBarque.accosterLaBerge(Barque.Berge.NORD);
            System.out.println("PASSEUR> La barque est au NORD: descendez!");
            laBarque.decharger();
            System.out.println("PASSEUR> La barque est vide: montez!");
        }
    }
}



class Nain extends Thread {
    public Barque laBarque;
    public Barque.Berge origine;
    public Nain(Barque b, Barque.Berge l) {
        this.laBarque = b;
        this.origine = l;
        start();
    }
    public void run() {
        Random alea = new Random();
        try {
            sleep(500+alea.nextInt(100)*50);
        } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(Thread.currentThread().getName()+
                           "> Je souhaite traverser.");
        laBarque.embarquerUnNain(origine);
        laBarque.debarquerUnNain(origine);
    }
}	

class Hobbit extends Thread {
    public Barque laBarque;
    public Barque.Berge origine;
    public Hobbit(Barque b, Barque.Berge l){
        this.laBarque = b;
        this.origine = l;
        start();
    }
    public void run() {
        Random alea = new Random();
        try {sleep(500+alea.nextInt(100)*50);}
        catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(Thread.currentThread().getName()+
                           "> Je souhaite traverser.");
        laBarque.embarquerUnHobbit(origine);
        laBarque.debarquerUnHobbit(origine);
    }
}	

