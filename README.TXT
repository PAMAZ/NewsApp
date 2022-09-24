Progetto News
Il progetto consiste nella creazione di una semplice app per la consultazione di notizie dal web.

Consegne
Il linguaggio da utilizzare per il progetto è Kotlin.

recuperare la lista di news da una API autenticata con api key.
mostrare la lista recuperata a schermo.
Dare la possibilità di selezionare una notizia e portare ad una pagina di dettaglio.
recuperare e mostrare il dettaglio della news da una seconda API autenticata nello stesso modo della precedente.


Requisiti tecnici

Non è richiesta, ma è gradita l'implementazione della UI in Jetpack Compose
L'app deve supportare la rotazione dello schermo
L'app deve essere sviluppata seguendo le best practice di una app di produzione
È gradita la presenza di test automatici


Specifiche API
La base url del servizio è il seguente

https://aewvdpjnii.execute-api.eu-central-1.amazonaws.com/dev



Api key
L'api key è da aggiungere come campo x-api-key tra gli header della request.
Il valore è il seguente:

B1Ryz3vdoW5E2VvbLSCKo7MhWoGXJmgY2TrPikRe



Lista News
La lista delle news è recuperabile con una GET al path /news.
Questa Api prevede l'utilizzo di 2 parametri in query string per gestire la paginazione del dato restituito.

limit: number
offset: number

Il payload atteso è nella forma:

{
    "message": {
        "results": [
            {
                "id": 0,        // integer
                "title": "",    // string
                "subtitle": "", // string
                "timestamp": 0  // timestamp in ms
            },
            ...
        ],
        "size": 0,              // number
        "parameters": {
            "limit": 0,         //number
            "offset": 0,        //number
        }
    }
}


NB: Questa Api è stata volutamente rallentata in proporzione al numero di dati richiesti.

Dettaglio News
Il dettaglio della news è recuperabile con una GET al path /news/{id}.
Il payload atteso è nella forma:

{
    "message": {
        "id": 0,        // integer
        "title": "",    // string
        "subtitle": "", // string
        "body": "",     // string
        "timestamp": 0  // timestamp in ms
    }
}