## Entrega 5 - Actividad

En un esfuerzo por hacer al juego mas social los diseñadores del mismo han
decidido introducir un muro de actividades recientes, en el cuál un personaje
podrá ver la actividad que ha ocurrido cerca suyo recientemente..

Para eso se modelarán eventos. Por ahora se han identificado los siguientes eventos:

- **Arribo**: un personaje se mueve a un lugar. _Se debera guardar los lugares de origen y destino_
- **MisionAceptada**: El personaje acepta una mision. _Se debera guardar la mision (No hace falta guardar toda la mision)_
- **MisionCompletada**: Cuando un personaje completa una mision. _Igual que la anterior_
- **CompraItem**: Cuando un personaje compra un item. _Se debera guardar el item y el precio de compra_
- **VentaItem**: Cuando un personaje vende un item. _Se debera guardar el item y el precio de venta_
- **Ganador**: Cuando un personaje gana un combate a otro personaje (solamente contra otro personaje, no contra un monstruo). _Se debera guardar el contrincante_

Todos los eventos tienen el personaje, un lugar y una fecha (con hora).


## Se deberá implementar:
Un nuevo servicio llamado FeedService que implemente los siguientes mensajes:

1. El mensaje `feedPersonaje(String personaje)` que devolverá la lista de
eventos que involucren al personaje provisto. Dicha lista debe
contener primero a los eventos más recientes.

2. El mensaje `feedLugar(String lugar)` Devuelve todos los eventos que ocurrieron en el lugar mas 
 todos los eventos de los lugares que estén conectadas con el mismo. 
 Dicha lista debe contener primero a los eventos más recientes.

## Se pide:
- El objetivo de esta entrega es implementar los requerimientos utilizando una
base de datos documental.
- Creen test unitarios para cada unidad de código entregada que prueben todas las
funcionalidades pedidas, con casos favorables y desfavorables.

### Consejos utiles:
- Traten de no tocar los objetos de modelo previamente definidos, solo van a
necesitar agregar las llamadas para crear nuevos eventos en los servicios del
TP2.