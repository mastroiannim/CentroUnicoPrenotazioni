import java.util.*;

public class Sportello extends Thread
{
    private int identificativo;
    private CentroUnicoPrenotazioni cup;
    
    public Sportello(int identificativo, CentroUnicoPrenotazioni cup){
        this.cup = cup;
        this.identificativo = identificativo;
    }
    
    public void run(){
        try{
            while(cup.aperto()){
                sleep(new Random().nextInt(100));
                cup.serviPaziente(identificativo);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
