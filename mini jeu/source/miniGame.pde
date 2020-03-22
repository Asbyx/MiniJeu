Player j;
ArrayList p = new ArrayList(); //incrémentée et gérée dans la class Projectile
ArrayList v = new ArrayList(); //incrémentée et gérée dans la class Projectile
int i, timer;
String state;

void setup() {
  size(1000, 800);
  j = new Player();
  frameRate(60);
  state = "game";
}

void draw() {
  clear();
  background(127);
  switch(state) {
  case "game":
    timer ++;
    if (timer < 3000) {
      if (timer%100.0 == 0) addProj();
    } else {
      if (timer < 5000) {
        if (timer%50.0 == 0)addProj();
      } else {
        if (timer%10.0 == 0)addProj();
      }
    }

    for (i = 0; i < v.size(); i++) {
      Vie vie = (Vie) v.get(i);//on remove dans projectile car sinon ça laisse le temps à l'arraylist de s'actualiser, ce qui créer des bugs + on doit break car sinon tous les index sont décalés et ça crée des bugs
      vie.show();
    }

    j.show();
    pUpdate();
    break;

  case "gameover":
    rectMode(CENTER);
    fill(100);
    rect(250, 600, 150, 75);
    rect(750, 600, 150, 75);

    textSize(32);
    textAlign(BASELINE);
    fill(0);
    text("Game Over !\n\nScore: "+j.score*timer, 50, 50);   

    fill(255);
    textAlign(CENTER);
    text("Rejouer", 250, 600);
    text("Quitter", 750, 600);    
    break;
  }
}

void pUpdate() {
  for (i = 0; i < p.size(); i++) {
    Projectile proj = (Projectile) p.get(i);
    proj.update();
    if (proj.check()) break; //on remove dans projectile car sinon ça laisse le temps à l'arraylist de s'actualiser, ce qui créer des bugs + on doit break car sinon tous les index sont décalés et ça crée des bugs
  }
}

void addProj () {
  switch(Math.round(random(0.5, 4))) {
  case 1:
    p.add(new Projectile(-10, random(0, 800), new PVector(10, random(-5, 5)), ' '));
    break;

  case 2:
    p.add(new Projectile(1010, random(0, 800), new PVector(-10, random(-5, 5)), ' '));
    break;

  case 3:
    p.add(new Projectile(random(0, 1000), -10, new PVector(random(-5, 5), 10), ' '));
    break;  

  case 4:
    p.add(new Projectile(random(0, 1000), 810, new PVector(random(-5, 5), -10), ' '));
    break;
  }
}

void mousePressed() {
  switch(state) {
  case"game": 
    j.tire();
    break;
  case"gameover":
    if(mouseX > 250-75 && mouseX < 250+75 && mouseY > 600 - 75.0/2.0 && mouseY < 600+75.0/2.0) {j = new Player(); p.clear(); timer = 0; state = "game";}
    if(mouseX > 750-75 && mouseX < 750+75 && mouseY > 600 - 75.0/2.0 && mouseY < 600+75.0/2.0) exit();
    break;
  }
}

void keyPressed() {
  switch(key) {
  case 'd':
    j.r = true;
    break;

  case 'q':
    j.l = true;
    break;

  case 'z':
    j.u = true;
    break;

  case 's':
    j.d = true;
    break;

  case 'r':
  case 'e':
    j.reloading = true;
    j.timerReloading = timer;
    break;
  }
  if (keyCode == SHIFT) {
    j.reloading = true; 
    j.timerReloading = timer;
  }
}

void keyReleased() {
  switch(key) {
  case 'd':
    j.r = false;
    break;

  case 'q':
    j.l = false;
    break;

  case 'z':
    j.u = false;
    break;

  case 's':
    j.d = false;
    break;
  }
}
