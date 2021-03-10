import java.util.*;

public class Dispenser extends Thread
{
    static final int N_CATEGORIE = 6;
    static int progressivo = 0;
    CentroUnicoPrenotazioni cup;
    int identificativo;
    
    public Dispenser(int identificativo, CentroUnicoPrenotazioni cup){
        this.identificativo = identificativo;
        this.cup = cup;
    }
    
    public Ticket getTicket(){
        return new Ticket();
    }

    public void run(){
        try{
            while(!cup.chiuso()){
                sleep(new Random().nextInt(100));
                cup.inserisciPaziente(identificativo);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
}

class Ticket 
{
    int numeroBiglietto;
    int priorita;
    
    public Ticket (){
         numeroBiglietto = Dispenser.progressivo++;
         priorita = new Random().nextInt(Dispenser.N_CATEGORIE);
    }
    
    public String toString(){
        String priorita = "" + (char)(((int)('A'))+this.priorita);
        return "#[" + numeroBiglietto + "|" + priorita + "]";
    }
}
