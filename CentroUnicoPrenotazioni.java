
public class CentroUnicoPrenotazioni
{
    static final int N_DISPENCER = 2;
    static final int N_SPORTELLI = 3;
    static final int MAX_PAZIENTI = 10;
    int pazientiTotali = 0;
    int pazientiAttuali = 0;
    Dispenser[] d;
    Sportello[] sportelli;
    Ticket[][] coda;
    int[] ultimoInCoda;
    int[] primoInCoda;
    
    Boolean aperto = true;
    Boolean chiuso = false;
    
    public CentroUnicoPrenotazioni(){

       inizializzaCode(); 
       
       avviaDispencer();
       avviaSportelli();
       
       joinSportelli();
       joinDispencer();
       
       System.out.println("CUP chiude dopo " + pazientiTotali + " Ticket");
    }
    
    public synchronized boolean chiuso(){
        
        return chiuso;
    }
    
    public synchronized boolean aperto(){
        
        return aperto;
    }
    
    public synchronized void inserisciPaziente(int dispencer){ 
       if(pazientiTotali < MAX_PAZIENTI){
           Ticket t = d[dispencer].getTicket();
           coda[t.priorita][ultimoInCoda[t.priorita]] = t;
           pazientiTotali++;
           pazientiAttuali++;
           System.out.println("Dispencer(" + dispencer + ") Emette Ticket(" + t + ")");
           ultimoInCoda[t.priorita]++;
           aperto=true;
        }else
           chiuso=true;
    }
    
    public synchronized void serviPaziente(int sportello){
        if(pazientiAttuali > 0){
            for(int i = 0; i < Dispenser.N_CATEGORIE; i++){
                
                if(primoInCoda[i] == MAX_PAZIENTI) continue;
                if(ultimoInCoda[i] == primoInCoda[i]) continue;
                if(coda[i][primoInCoda[i]] == null) continue;
                
                System.out.println("Sportello(" + sportello + ") Serve Ticket(" + coda[i][primoInCoda[i]] + ")");
                primoInCoda[i]++;
                pazientiAttuali--;
                break;
            }
        }else if(pazientiTotali == MAX_PAZIENTI){
            aperto = false;
        }
              
    }
    
    private void inizializzaCode(){
       coda = new Ticket[Dispenser.N_CATEGORIE][];
       ultimoInCoda =  new int[Dispenser.N_CATEGORIE];
       primoInCoda =  new int[Dispenser.N_CATEGORIE];
       
       for(int i = 0; i < Dispenser.N_CATEGORIE; i++){
           ultimoInCoda[i] = 0;
           primoInCoda[i] = 0;
           coda[i] = new Ticket[MAX_PAZIENTI];
       }
    }
    
    private void avviaSportelli(){
       sportelli = new Sportello[N_SPORTELLI];
       for (int i=0; i<N_SPORTELLI; i++){
           sportelli[i] = new Sportello(i,this);
           sportelli[i].start();
        }   
    }
    
    private void joinSportelli(){
        try{
           for (int i=0; i<N_SPORTELLI; i++){
               sportelli[i].join();
           }
       }catch(Exception e){
           throw new RuntimeException(e);
       }
    }
    
    private void avviaDispencer(){
       d = new Dispenser[N_DISPENCER];
       for(int i = 0; i < N_DISPENCER; i++){
           d[i] = new Dispenser(i, this);
           d[i].start();
       }
    }
    
    private void joinDispencer(){
       try{
           for(int i = 0; i < N_DISPENCER; i++)
               d[i].join();
       }catch(Exception e){
           throw new RuntimeException(e);
       }
    }
}
