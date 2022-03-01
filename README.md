# SkyWars-Core

Grazie a questo plugin è possibile creare SkyWars sul proprio server 100% customizzabili.
Sarà possibile creare arene Singole, in Team, Private e molto altro ancora!

### Installazione

- Scarica **SkyWars-Core.jar**
- Inserisci **SkyWars-Core.jar** nella cartella _plugins/_
- Riavvia il tuo server

## Comandi e Permessi
##### Comando, Descrizione, (Permesso), [Alias]

- **/skywars** _Mostra la lista di tutti i comandi_ (skywars.help) [sw, sw help]
- **/sw create <arena> <Single|Team>** _Crea una nuova arena_ (skywars.create)
- **/sw remove <arena** _Rimuovi un arena_ (skywars.remove)
- **/sw setminplayers <arena> <quantità>** _Imposta il minimo di giocatori di un Arena_ (skywars.edit)
- **/sw setmaxplayers <arena> <quantità>** _Imposta il massimo di giocatori di un Arena_ (skywars.edit)
- **/sw setmaxteamplayers <arena> <colore> <quantità>** _Imposta il massimo di giocatori di un Team di un Arena_ (skywars.edit)
- **/sw settimestart <arena> <quantità>** _Imposta il tempo di un Arena_ (skywars.edit)
- **/sw setlobby <arena>** _Imposta la lobby di un Arena_ (skywars.edit)
- **/sw setspec <arena>** _Imposta il punto spettatori di un Arena_ (skywars.edit)
- **/sw addspawn <arena> <id>** _Aggiungi uno spawn di un Arena_ (skywars.edit)
- **/sw removespawn <arena> <id>** _Rimuovi uno spawn di un Arena_ (skywars.edit)
- **/sw addteam <arena> <color>** _Aggiungi un team di un Arena_ (skywars.edit)
- **/sw removeteam <arena> <color>** _Rimuovi un team di un Arena_ (skywars.edit)
- **/sw list** _Visualizza una lista contenente tutte le Arene_ (skywars.list)
- **/sw info <arena>** _Visualizza le informazioni di un Arena_ (skywars.info)
- **/sw join <arena>** _Entra in un Arena_ (skywars.join)
- **/sw createtype <arena>** _Crea un nuovo tipo di Arena_ (skywars.edit)
- **/sw removetype <arena>** _Rimuovi un tipo di Arena_ (skywars.edit)
- **/sw settype <arena> <tipo>** _Imposta il tipo di un Arena_ (skywars.edit)
- **/sw edittypes** _Modifica i tipi di Arena disponibili_ (skywars.edit)
- **/sw chestadmin <arena>** _Abilita la modalità ChestAdmin per un arena_ (skywars.edit)
- **/sw corner1 <arena>** _Imposta il primo angolo di un Arena_ (skywars.edit)
- **/sw corner2 <arena>** _Imposta il secondo angolo di un Arena_ (skywars.edit)

## Configurazione di un Arena
_Segui queste indicazioni per configurare al meglio un'Arena!_

- Crea una nuova Arena
- Imposta il numero massimo e minimo di giocatori;
- Imposta il numero di partecipanti massimi per team se l'arena è in modalità Team;
- Imposta il tempo di durata del gioco;
- Aggiungi i Team se l'arena è in modalità Team;
- Aggiungi tutti gli spawn. Assegna un numero come Id;
- Imposta un tipo all'arena. Creane uno nuovo se quelli attuali non soddisfano la richiesta;
- Entra in modalità ChestAdmin e piazza le varie Chest nella mappa facendo attenzione al livello della Chest;
- Imposta gli Angoli dell'arena.


### Prossime novità
- [] Aggiungere /sw leave
- [] Creare un algoritmo per migliorare la selezione dei Team
- [] Creare un algoritmo di scelta mappa
- [] Creare un sistema di statistiche
- [] Aggiungere la compatibilità con SQL per l'archiviazione dei dati