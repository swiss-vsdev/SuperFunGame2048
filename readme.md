


## Super Fun Game 2048


Class et méthodes ::

Class Board
- [ ] var ArrayArray(Int) = Array.ofDim(4,4);

Méthodes
- [ ] addNewTile // génération automatique de UN 2 (80%) ou rarement UN 4 (20%) en position aléatoire (mais sur une case vide) à chaque clic

- [ ] mooveTiles(direction)={
// 0 = bas
// 1 = gauche
// 2 = haut
// 3 = droite
Calculer le nouvelles positions avec merge
Si Winner : appeller Winner
So Looser : appeller Looooooooser
Appeller addNewTiles
}

- [ ] score() // incrémenter le score ou faire ça dans mooveTiles

- [ ] show() // clear l’affichage et affiche le nouveau board + le score de manière graphique

- [ ] winner() // Afficher Winner

- [ ] loooooser() // Afficher Looser


Objet Playing
- [ ] new board + appel 2x new tile
- [ ] Ecouter les fleches directionnelles et Board.mooveTiles(direction)


Idées :
- Leaderboard dans fichier texte
- 1 VS 1
- Dino -> si loooser // ()


A faire : 
- [ ] board de 4x4 stocké dans un tableau 2D
- [ ] Algorithme de génération automatique de UN 2 ou rarement UN 4 en position aléatoire (mais sur une case vide) à chaque clic
- [ ] écouter le clavier pour trigger un mouvement au clic des flêches directionnelles
- [ ] afficher le jeu à l’aide de FunGraphics.py
- [ ] lors du mouvement calculer les nouvelles positions ainsi que les merging
    - [ ] Vérifier si toutes les cases sont occupées et si aucun merging possible : LOOOOOOOOSER
    - [ ] Si merging : vérifier valeur du merging et BRAVO si 2048
- [ ] Calcul du score à chaque clic
- [ ] Affichage du score à l’aide de FunGraphics.py
