class Vie {
float x, y;
  
  Vie(){
    x = random(100, 900);
    y = random(100, 700);
  }

  void show(){
    fill(0, 0, 255);
    rectMode(CENTER);
    rect(x, y, 20, 20);
    
    if(dist(x, y, j.x, j.y) < 50) {v.remove(i); j.pv ++;}
  }
}
