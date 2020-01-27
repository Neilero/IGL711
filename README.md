# Rapport de TP1 - Equipe 5
- Léo Pécault
- Farah Rebiha
- Aurélien Vauthier

## Environnement

Ce programme a été développé et testé avec les 3 configurations suivantes :

Première configuration :
* Ubuntu 18.04 (Sous-système Linux de Windows)
* GCC / G++ version 9.0.1
* Boost version 1.72.0
* Zlib version 1.2.11
* GNU Make 4.1
* CMake version 3.16.2

## Commande `gitus init`

Cette commande permet d'initialiser un dépot Gitus dans le répertoire courant. 

L'initialisation d'un dépot se fait par la commande suivante :
```console
gitus init
> Initialized Git repository in <current path>
```

Pour afficher l'aide de cette commande, il suffit d'ajouter l'option `--help` ou `-h` en paramètre :
```console
gitus init --help
> usage: gitus init
gitus init -h
> usage: gitus init
```

## Commande `gitus add`

Cette commande permet d'ajouter des fichiers au *staging area* du dépot Gitus.

Pour ajouter un fichier, on utilise la commande suivante :
```console
gitus add <path>
> The file <path> has been added to the Gitus repository
```
Il est également possible d'ajouter plusieurs fichiers dans la même commande :
```console
gitus add <path> <not correct path> <path>
> The file <path> has been added to the Gitus repository
> The file <not correct path> doesn`t exist
> The file <path> has been added to the Gitus repository
```
Si l'option `--help` ou `-h` est précisée en paramètre, Gitus affichera l'aide liée à la commande `add` :
```console
gitus add --help
> usage: gitus add <pathspec>
gitus add -h
> usage: gitus add <pathspec>
```

## Commande `gitus commit`

Cette commande permet de créer une soumission dans le dépot Gitus.

La création d'une soumission se fait par la commande suivante :
```console
gitus commit <msg> <author> <email>
> Commit <id> created
```

Pour afficher l'aide de cette commande, il suffit d'ajouter l'option `--help` ou `-h` en paramètre :
```console
gitus commit --help
> usage: gitus commit <msg> <author> <email>

gitus commit -h
> usage: gitus commit <msg> <author> <email>
```