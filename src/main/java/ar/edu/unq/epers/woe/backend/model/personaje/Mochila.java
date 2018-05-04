package ar.edu.unq.epers.woe.backend.model.personaje;
import ar.edu.unq.epers.woe.backend.model.item.Item;
import java.util.ArrayList;
import java.util.List;

public class Mochila {

    private List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void agregarItem(Item item) {
        if(this.items.size() >= 10) {
            throw new RuntimeException("Mochila llena.");
        } else {
            this.items.add(item);
        }
    }

    public Integer itemsEnMochila() {
        return this.items.size();
    }

	public void sacarItem(Item i) {
		items.remove(i);
	}

	public Boolean tieneElItem(Item item) {
		Boolean res = true;
        for(Item i : this.items ) {
            if( (i.getNombre() == item.getNombre())
               && (i.getUbicacion() == item.getUbicacion())
               && (i.getTipo() == item.getTipo())
               && (i.getClases() == item.getClases())
            // && (i.getRequerimiento() == item.getRequerimiento())
               && (i.getCostoDeCompra() == item.getCostoDeCompra())
               && (i.getCostoDeVenta() == item.getCostoDeVenta())
            // && (i.getAtributos() == item.getAtributos())
            ){
                res = false;
                break;
            }
        }
        return res;
		
	}
}
