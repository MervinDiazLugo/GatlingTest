
### La guía completa para entender este framework esta en:
https://medium.com/@waynemervin/pruebas-de-carga-con-gatling-la-gu%C3%ADa-completa-027e60c88be1


Puedes correr las pruebas puedes utilizar los siguientes comandos:

### Pet Store
- **Maven**  - `mvn clean gatling:test -DclassName='petStore.GetPetStoreTest'`
- **Maven**  - `mvn clean gatling:test -DclassName='petStore.GetPetStoreLoopForeverTest'`
- **Maven**  - `mvn clean gatling:test -DclassName='petStore.GetPetStoreWithPropertiesTest'`
- **Maven**  - `mvn clean gatling:test -DclassName='petStore.PostNewPetWithCustomFeeder'`
- **Maven**  - `mvn clean gatling:test -DclassName='petStore.GetPetStoreCheckResponseBodyAndExtract'`



- Para correr las pruebas de en videoGames debes correr la api https://github.com/MervinDiazLugo/UdemyVideoGameAPI
- Más detalles en la guía colocada al principio de este tutorial.


### Video Games
- **Maven**  - `mvn clean gatling:test -DclassName='videoGames.GetVideoGamesTest'`
- **Maven**  - `mvn clean gatling:test -DclassName='videoGames.GetVideoGamesLoopForeverTest'`
- **Maven**  - `mvn clean gatling:test -DclassName='videoGames.GetVideoGamesWithPropertiesTest'`
- **Maven**  - `mvn clean gatling:test -DclassName='videoGames.PostVideoGamesWithCustomFeeder'`
- **Maven**  - `mvn clean gatling:test -DclassName='videoGames.GetVideoGamesCheckResponseBodyAndExtract'`


**Para conservar los reportes anteriores puedes quitar el comando 'clean'**
