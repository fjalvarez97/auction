import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder,value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            Lot selectedLot = lots.get(lotNumber-1);
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                    selectedLot.getNumber() +
                    " was returned instead of " +
                    lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }

    
    /**
     * Imprime por pantalla toda la informacion de los lotes, si el lote tiene alguna puja tambien
     * imprime de cuando es, y la persona que la hizo.
     */
    public void close()
    {
        for (Lot lote : lots)
        {
            if (lote.getHighestBid()!=null)
            {
                System.out.println(lote + " Puja de: " + lote.getHighestBid() + " Pujado por: " + lote.getHighestBid().getBidder()) ;
            }
            else
            {
                System.out.print(lote);
            }
        }
    }

    /**
     * Devuelve los lotes de la coleccion que no han sido vendidos (guardados en otra coleccion)
     */
    public ArrayList<Lot> getUnsold()
    {
        ArrayList<Lot> copiaColeccion = (ArrayList<Lot>) lots.clone();
        for(int i = 0; i < copiaColeccion.size(); i++) 
        {  
            Lot lotActual = lots.get(i);
            if (lotActual.getHighestBid() != null)
            {
                copiaColeccion.remove(i);
                i--;
            }
                
        }
        return copiaColeccion;
    }
    
    /**
     * Eliminar el lote con el numero de 
     * lote especificado.
     * @param numer El numero del lote que hay que eliminar.
     * @return El lote con el numero dado o null si
     * no existe tal lote.
     */
    
    public Lot removeLot(int number)
    {
        boolean loteEncontrado = false;
        int i = 0;
        Lot loteBorrado = null;
        while (!loteEncontrado) 
        {  
            Lot loteActual = lots.get(i);
            if(loteActual.getNumber() == number)
            {
                lots.remove(loteActual);
                loteEncontrado = true;
                loteBorrado = loteActual;
            }
            i++;
        }
        return loteBorrado;
    }
}
