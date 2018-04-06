package ar.edu.unq.epers.woe.frontend.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unq.epers.woe.backend.model.personaje.Personaje;
import ar.edu.unq.epers.woe.backend.model.raza.Raza;
import ar.edu.unq.epers.woe.backend.model.raza.Clase;
import ar.edu.unq.epers.woe.backend.model.raza.RazaBuilder;
import ar.edu.unq.epers.woe.backend.service.raza.RazaService;

/**
 * Esta es una implementacion mock de {@link RazaService}
 * <p>
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 */
public class RazaServiceMock implements RazaService {

    private static Map<Integer, Raza> data = new HashMap<>();

    public RazaServiceMock() {
        if (data.isEmpty()) {
            crearRaza(new RazaBuilder("Orco")
                    .conUrlFoto("http://bnetcmsus-a.akamaihd.net/cms/gallery/TV6CW7CF1WGD1456963822032.jpg")
                    .conClases(Clase.CABALLERO, Clase.MONJE, Clase.CAZADOR, Clase.MAGO, Clase.BRUJO, Clase.GUERRERO)
                    .conAltura(160)
                    .conPeso(120)
                    .conEnergiaInicial(90)
                    .build());


            crearRaza(new RazaBuilder("Humano")
                    .conUrlFoto("http://bnetcmsus-a.akamaihd.net/cms/gallery/2PX28FWG0LBU1456963821279.jpg")
                    .conClases(Clase.CABALLERO, Clase.PICARO, Clase.CAZADOR, Clase.MAGO, Clase.MONJE, Clase.GUERRERO, Clase.SACERDOTE)
                    .conAltura(180)
                    .conPeso(100)
                    .conEnergiaInicial(100)
                    .build());

            crearRaza(new RazaBuilder("Enano")
                    .conUrlFoto("http://bnetcmsus-a.akamaihd.net/cms/gallery/eb/EBJI1K5KYJSZ1456961090313.jpg")
                    .conClases(Clase.CABALLERO, Clase.PICARO, Clase.CAZADOR, Clase.MAGO, Clase.MONJE, Clase.BRUJO, Clase.SACERDOTE, Clase.PALADIN)
                    .conAltura(140)
                    .conPeso(80)
                    .conEnergiaInicial(80)
                    .build());

            crearRaza(new RazaBuilder("No-muerto")
                    .conUrlFoto("http://bnetcmsus-a.akamaihd.net/cms/gallery/8s/8S6C2RLW881Q1456780184804.jpg")
                    .conClases(Clase.CABALLERO, Clase.CAZADOR, Clase.MAGO, Clase.MONJE, Clase.BRUJO, Clase.SACERDOTE, Clase.PICARO, Clase.GUERRERO)
                    .conAltura(160)
                    .conPeso(60)
                    .conEnergiaInicial(60)
                    .build());

            crearRaza(new RazaBuilder("Elfo de la noche")
                    .conUrlFoto("http://bnetcmsus-a.akamaihd.net/cms/gallery/V2TWXFDFNNSY1456963821872.jpg")
                    .conClases(Clase.CABALLERO, Clase.DRUIDA, Clase.PICARO, Clase.CAZADOR, Clase.MONJE, Clase.MAGO, Clase.SACERDOTE, Clase.GUERRERO)
                    .conAltura(170)
                    .conPeso(60)
                    .conEnergiaInicial(60)
                    .build());

            crearRaza(new RazaBuilder("Elfo de sangre")
                    .conUrlFoto("http://bnetcmsus-a.akamaihd.net/cms/gallery/pw/PW46FJ3L5MNY1456960851704.jpg")
                    .conClases(Clase.CABALLERO, Clase.PICARO, Clase.CAZADOR, Clase.MAGO, Clase.MONJE, Clase.BRUJO, Clase.SACERDOTE, Clase.PALADIN)
                    .conAltura(160)
                    .conPeso(60)
                    .conEnergiaInicial(60)
                    .build());
        }

    }

    @Override
    public void crearRaza(Raza raza) {
        raza.setId(data.size() + 1);
        data.put(raza.getId(), raza);
    }

    @Override
    public Raza getRaza(Integer id) {
        return data.get(id);
    }

    @Override
    public List<Raza> getAllRazas() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Personaje crearPersonaje(Integer razaId, String nombrePersonaje, Clase clasePersonaje) {
        Raza raza = data.get(razaId);
        return raza.crearPersonaje(nombrePersonaje, clasePersonaje);
    }

}
