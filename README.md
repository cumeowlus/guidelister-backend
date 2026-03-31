# guidelister-backend
REST API server for guidelister, a trip activty listing app

## Prerequis
- Java 17+ (ou la version requise par votre projet)
- Maven (ou utilisez le wrapper `./mvnw` fourni)

## Build & run
1. Build avec Maven (wrapper si présent) :
    - Unix / macOS :
      ```
      ./mvnw clean package
      ```
      ou si vous utilisez Maven installé :
      ```
      mvn clean package
      ```
    - Windows (PowerShell) :
      ```
      .\mvnw.cmd clean package
      ```

2. Lancer l’application :
    - Depuis le jar généré :
      ```
      java -jar target/guidelister-backend-<version>.jar
      ```
    - Ou directement avec Maven (dev) :
      ```
      ./mvnw spring-boot:run
      ```

3. Par défaut l’application écoute sur le port configuré dans `application.properties` (par défaut Spring Boot = 8080). Pour changer le port au lancement :

## API

Toutes les requêtes envoient/renvoient JSON (Content-Type: `application/json`) sauf mention contraire.

1) Auth (AuthController) — base `/api/auth`

- POST `/api/auth/login`
- Corps: `{ "email": "user@example.com", "password": "secret" }`


- POST `/api/auth/logout`
- Usage: invalide la session côté serveur


- GET `/api/auth/me`
- Usage: retourne l’identifiant et si admin (depuis session)


- POST `/api/auth/registerUser`
- Corps: `{ "email":"...", "password":"..." }`
- Crée un compte non-admin, stocke la session (le code connecte automatiquement)


- POST `/api/auth/registerAdmin`
- Corps: `{ "email":"...", "password":"..." }`
- Crée un compte admin (isAdmin = true)


2) Guides (GuideController) — base `/api/guides`

- GET `/api/guides`
- Retourne la liste de tous les guides


- GET `/api/guides/{id}`
- Retourne le guide identifié par {id}


- GET `/api/guides/user/{id}`
- Retourne les guides associés à un utilisateur (userId)


- POST `/api/guides/new`
- Crée un nouveau guide
- Corps (exemple de structure attendue — adaptez selon votre modèle):
  ```
  {
    "titre": "Excursion X",
    "description": "Une sortie ...",
    "mobilite": "Faible",
    "nbJour": 2,
    "publicCible": "Famille",
    "saison": "Été",
    "activites": [ ... ],     // optionnel
    "users": [ ... ]          // optionnel
  }
  ```


- PUT `/api/guides/{id}`
- Met à jour un guide existant
- Corps: JSON partiel/total du guide (meme corps que `/api/guides/new`)


- DELETE `/api/guides/{id}`
- Supprime le guide

- POST `/api/guides/{guideId}/activities`
- Ajoute une activité au guide
- Corps (exemple, gardez les champs tels qu’ils sont dans votre modèle) :
  ```
  {
    "titre": "Visite du musée",
    "ordreVisite": 1,
    "description": "...",
    "adresse": "...",
    "categorie": "...",
    "horaires": "...",
    "nbJours": 1,
    "siteWeb": "...",
    "telephone": "..."
  }
  ```

- DELETE `/api/guides/{guideId}/activities/{activityId}`
- Supprime une activité d’un guide
- 

- POST `/api/guides/{guideId}/users/{userId}`
- Associe un utilisateur existant au guide (ajout à la liste users)


3) Users (UserController) — base `/api/users`

- GET `/api/users`
- Retourne la liste des utilisateurs
- 

- DELETE `/api/users/{id}`
- Supprime l’utilisateur