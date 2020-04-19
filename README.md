# eq05

## Problème avec le modèle

Après intégration des différentes parties du TP ensemble, nous nous sommes rendu compte
d'une erreur d'implémentation. En effet, dans notre modèle actuel, il existe des
références croisées entre la classe Worker et les classes DockerImage et OpenPort. Un
worker possède ainsi un DockerImage et une liste d'OpenPort et les classe DockerImage /
OpenPort possède un worker. 

Ce faisant, nous rencontrons donc un problème lors de l'envoie des objects car ces
références croisées créer ainsi une boucle infinie lors de leur sérialisation.
Pour remédier à ce problème nous avons pensé à une nouvelle implémentation du modèle
que vous trouverez ci-dessous :

```java
public class Worker {

	private final UUID id;

	@Size( min = 8, max = 15, message = "IP should be valid")
	private String address;

	@Positive( message = "Port should be positive" )
	@Max( value = 65535, message = "Port should be valid")
	private int port;

	private Status status;

	private DockerImage runningImage;
}
```

```java
public class DockerImage {

	private UUID id;
	
	private String name;

	@NotNull
	private final List<OpenPort> openPorts;
}
```

```java
public class OpenPort {

	private final UUID id;

	@Positive( message = "Port should be positive" )
	@Max( value = 65535, message = "Port should be valid")
	private int port;
}
```