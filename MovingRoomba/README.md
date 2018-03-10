# Moving Roomba game
Basic idea: The self-design roomba has a small round "headlight" on its leading edge so we can see which way it's headed. The roomba does *not* wrap around edges, but rather runs into the edge of the screen. It will get stuck at an edge until we turn enough to be facing away from the edge.   
Use left arrow/right arrow to control self-design roomba behave correctly: The keys should *turn* the direction of the roomba, *not* move it in that direction. Holding down the "left" key should make the roomba circle to the left, *not* move left across the screen. There's no forward or backward key - the roomba just always moves in the direction it's facing.  
We kept track of the x and y coordinates of the roomba, but also which direction it's facing. The easiest way to handle the direction is to keep an angle which you add to or subtract from to turn. Our direction is dir_angle and your speed is speed so we can compute a velocity with:  
dx = speed * Math.cos(dir_angle);  
dy = speed * Math.sin(dir_angle);  
