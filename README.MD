One-sided battleship game.

This is a console version of a battleship game. 
Player vs computer generated ships that are randomly placed on a board. Game ends when player destorys all ships 

This is a simple project using test driven development - Java + Spock

Game:

1. Game starts with computer board (10x10) which has 3 randomly placed ships - 2xDestroyers (size 4) and 1xBattleship (size 5)

![Screenshot from 2022-04-27 13-23-09](https://user-images.githubusercontent.com/70399149/165510057-84ea0166-a9b9-411b-bed5-539c66ff4a26.png) ![Screenshot from 2022-04-27 13-22-58](https://user-images.githubusercontent.com/70399149/165510092-ee50ff16-f2ec-4d8f-b9fe-9b06645ec02c.png) ![Screenshot from 2022-04-27 13-22-46](https://user-images.githubusercontent.com/70399149/165510115-449bad44-e800-420d-bc3b-cd2be34ef048.png)

and empty player board (10x10)

Ships are placed within the rules of the real battleship game - they cannot overlap each other or cross the border of the grid.

2. In each iteration player is asked for coordinates (a1, A1, j10, J10) then coordinates are validated and player board is updated accordingly.
 
![Screenshot from 2022-04-27 13-41-48](https://user-images.githubusercontent.com/70399149/165510674-ae3dc73b-a775-4609-974a-b86fb1481cf7.png) ![Screenshot from 2022-04-27 13-42-17](https://user-images.githubusercontent.com/70399149/165510767-3a64410a-1f9c-4f81-92ef-072352cfabc2.png)

3. Game ends when all computer's ship are destoyed. 

![Screenshot from 2022-04-27 13-47-48](https://user-images.githubusercontent.com/70399149/165511684-54bbc855-e070-41b2-834f-145652e33928.png)
