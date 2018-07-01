## Entrega 6 - Cache

Se ha identificado que ciertas consultas en el sistema se realizan frecuentemente y consumen muchos recursos de los servidores de BBDD.

Se desea implementar una estrategia de cache, en donde ciertas consultas sean recuperadas previamente de una cache antes de acceder a la base de datos

Concretamente se quiere optimizar los siguientes servicios:

- `LeaderboardService.masFuerte():Personaje`. Se deberá consultar una Cache para recuperar el personaje mas fuerte.

- `FeedService.feedLugar(String lugar):List<Evento>`. Se deberá consutlar una Cache para recuperar los eventos que ocurrieron en el lugar..

## Se pide:
- El objetivo de esta entrega es implementar los requerimientos utilizando una base de datos clave/valor (Redis) como cache.
- Creen test unitarios para cada unidad de código entregada que prueben todas las funcionalidades pedidas, con casos favorables y desfavorables.

### Consejos utiles:
- Traten de no tocar los objetos de modelo previamente definidos, solo van a necesitar agregar llamadas a la cache.
- Tengan en cuenta que deben mantener la consistencia de la cache. Es decir:
   - Siempre que un personaje cambie su dańo, de debera actualizar la cache o invalidarla.
   - Siempre que se genere un evento en un lugar, actualizar o invalidar la cache de ese lugar.