# PalladioFluentAPI

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

## Project Description
This project provides a fluent API to create a PCM Repository Model.

### Palladio Component Model (PCM)
The Palladio Component Model (PCM) is one of the core assets of the Palladio approach. It is designed to enable early performance, reliability, maintainability, and cost predictions for software architectures and is aligned with a component-based software development process. The PCM is implemented using the Eclipse Modeling Framework (EMF). Visit the [Homepage](https://www.palladio-simulator.com)

#### PCM Repository Model



## Project Setup

For using the fluent API, three dependencies are required:
1. Palladio-Core-PCM (org.palladiosimulator.pcm)
2. Palladio-Core-PCM Resources (org.palladiosimulator.pcm.resources)
3. Palladio FluentAPI (org.palladiosimulator.fluentapi)

It is recommended to work with a PCM installation. Therefor, install the PCM Nightly as described at [PCM_Installation#PCM_Nightly](https://sdqweb.ipd.kit.edu/wiki/PCM_Installation#PCM_Nightly).
Create your own Plug-in Project and add the three dependencies in the MANIFEST.MF file.
You are now ready to use the `FluentRepositoryFactory` to create a Repository.




## Recherche
* EMF
* Eclipse Plugin
* Fluent Interfaces
* Palladio
* diverse Tutorials, YouTube Videos, Wiki Seiten, andere Internet-Ressourcen

### Praktische Recherche
* EMF Modell erstellen
* PCM Komponentenmodell erstellen
* Fluent Interface Ansatz mit Builder Pattern ausprobieren
* PCM programmatisch erstellen: Teste Code von Wiki-Seite, erstelle ein einfaches PCM Modell programmatisch über RepositoryFactory; identifiziere Methoden zur Manipulation des Repository Objekts


## Aufbau einer Fluent API
* Analyse der Bauteile eines PCM Komponenten-Modells (über GUI, Baumstruktur, TextBasedModelGenerator-Projekt) -> Komponenten, Interfaces, SEFFs etc.etc.
* Implementierung eines Prototyps (Erstellen eines Repository mit Basic Components und Interfaces)
