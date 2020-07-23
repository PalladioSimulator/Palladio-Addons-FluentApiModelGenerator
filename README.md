# FluentAPICreation

Hi Philip,

die fluent API läuft komplett über die FluentAPIFactory. Die musst du einmal initialisieren und kannst dann damit Repository, Components oder eben SEFFs erstellen.
```java
FluentRepositoryFactory create = new FluentRepositoryFactory();
		
Repository repo = create.newRepository()
				.addToRepository(create.newOperationInterface().withName("IDatabase")
									.withOperationSignature()
										.withName("saveDatabaseEntry").now())
				.addToRepository(create.newBasicComponent().withName("Database")
									.withServiceEffectSpecification(create.newSeff()
											.onSignature(create.fetchOfSignature("saveDatabaseEntry"))
											.withSeffBehaviour().withStartAction()
												.followedBy().externalCallAction()
												// beliebig viele Actions
												.followedBy().stopAction().createBehaviourNow()))
				.createRepositoryNow();
```

Oder wenn du wirklich nur den SEFF willst:

```java
ServiceEffectSpecification seff = create.newSeff()
		.withSeffBehaviour().withStartAction()
			.followedBy().externalCallAction()
			// beliebig viele Actions
			.followedBy().stopAction().createBehaviourNow().build();
```

Ich hoffe, das hilft dir erst mal weiter. Wenn du Fehler findest oder Verbesserungsvorschläge hast, immer her damit.

Viele Grüße
Louisa



# Projekt erstellen
Java Plugin Projekt mit Palladio (org.palladiosimulator.pcm) als Abhängigkeit. 

# Recherche
* EMF
* Eclipse Plugin
* Fluent Interfaces
* Palladio
* diverse Tutorials, YouTube Videos, Wiki Seiten, andere Internet-Ressourcen

## Praktische Recherche
* EMF Modell erstellen
* PCM Komponentenmodell erstellen
* Fluent Interface Ansatz mit Builder Pattern ausprobieren
* PCM programmatisch erstellen: Teste Code von Wiki-Seite, erstelle ein einfaches PCM Modell programmatisch über RepositoryFactory; identifiziere Methoden zur Manipulation des Repository Objekts


# Aufbau einer Fluent API
* Analyse der Bauteile eines PCM Komponenten-Modells (über GUI, Baumstruktur, TextBasedModelGenerator-Projekt) -> Komponenten, Interfaces, SEFFs etc.etc.
* Implementierung eines Prototyps (Erstellen eines Repository mit Basic Components und Interfaces)
