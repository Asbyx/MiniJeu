import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class miniGame extends PApplet {

Player j;
ArrayList p = new ArrayList(); //incrémentée et gérée dans la class Projectile
ArrayList v = new ArrayList(); //incrémentée et gérée dans la class Projectile
int i, timer;
String state;

public void setup() {
  
  j = new Player();
  frameRate(60);
  state = "game";
}

public void draw() {
  clear();
  background(127);
  switch(state) {
  case "game":
    timer ++;
    if (timer < 3000) {
      if (timer%100.0f == 0) addProj();
    } else {
      if (timer < 5000) {
        if (timer%50.0f == 0)addProj();
      } else {
        if (timer%10.0f == 0)addProj();
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

public void pUpdate() {
  for (i = 0; i < p.size(); i++) {
    Projectile proj = (Projectile) p.get(i);
    proj.update();
    if (proj.check()) break; //on remove dans projectile car sinon ça laisse le temps à l'arraylist de s'actualiser, ce qui créer des bugs + on doit break car sinon tous les index sont décalés et ça crée des bugs
  }
}

public void addProj () {
  switch(Math.round(random(0.5f, 4))) {
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

public void mousePressed() {
  switch(state) {
  case"game": 
    j.tire();
    break;
  case"gameover":
    if(mouseX > 250-75 && mouseX < 250+75 && mouseY > 600 - 75.0f/2.0f && mouseY < 600+75.0f/2.0f) {j = new Player(); p.clear(); timer = 0; state = "game";}
    if(mouseX > 750-75 && mouseX < 750+75 && mouseY > 600 - 75.0f/2.0f && mouseY < 600+75.0f/2.0f) exit();
    break;
  }
}

public void keyPressed() {
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

public void keyReleased() {
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

class Player {
  float x, y;
  int score, pv, amo, timerAmo, timerReloading;
  boolean reloading, needAmo, r, u, l, d = false;


  Player() {
    x = 500;
    y = 400;
    amo = 5;
    score = pv = 0;
  }

  public void show() {
    if(pv < 0) state = "gameover";
    
    rectMode(CENTER);
    fill(0);
    pushMatrix();
      float a = atan2(mouseY-y, mouseX-x);
      translate(x, y);
      rotate(a);
      rect(0, 0, 50, 50);
      rect(20, 0, 35, 8);
    popMatrix();
    
    for (i = 0; i < amo; i++){
      fill(0, 255, 0);
      ellipse(980, 780 - 25*i, 20, 20);
    }
    
    for (i = 0; i < pv; i++){
      fill(0, 0, 255);
      rectMode(CENTER);
      rect(950, 780- 25*i, 20, 20);
    }
    
    if(score%25 == 0){v.add(new Vie()); score++;}
    
    if (r) x+=4;
    if (l) x-=4;
    if (d) y+=4;
    if (u) y-=4;
    
    if(needAmo) {
      textSize(32);
      textAlign(CENTER);
      fill(255);
      text("Need Reload !", x, y+20);
      if(timerAmo == timer - 50) needAmo = false;
    }
    
    if(reloading) {
      textSize(16);
      textAlign(CENTER);
      fill(0);
      text("Reloading...", x, y+50);
      if(timerReloading == timer - 25) {needAmo = false; amo = 5; reloading = false;}
    }
  }
  
  public void tire() {
    if (amo > 0 && !reloading) {
      p.add(new Projectile(j.x, j.y, new PVector(mouseX - j.x, mouseY - j.y), 'j'));
      amo --;  
    } else {
      needAmo = true;
      timerAmo = timer;
    }
    
    
  }

}
class Projectile {
  float x, y;
  PVector v;
  char f;

  Projectile(float xa, float ya, PVector vec, char from) {
    x = xa;
    y = ya;
    v = vec;
    f = from;
  }

  public void update() {
    ellipseMode(CENTER);
    if (f == 'j') {
      fill(0, 255, 0);
      if (v.mag() != 6)v.setMag(6);
    } else if (f == ' ') {
      fill(255, 0, 0);
      if(Math.round(random(1, 150)) == 2){v = new PVector(j.x - x, j.y - y); f = 'm';} //m pour méchant
      if (v.mag() != 4)v.setMag(4);
    } else {
      fill(255, 0, 255);
      if (v.mag() != 8)v.setMag(8);
    }
    x += v.x;
    y += v.y;
    ellipse(x, y, 20, 20);
  }

  public boolean check() {
    if(x > 1010 || x < -10 || y > 810 || y < -10) {p.remove(i); return true;}
    if(dist(x, y, j.x, j.y) < 45 && f != 'j') {p.remove(i); j.pv --; return true;}
    
    for (int k = 0; k < p.size(); k++) {
      if (i!=k) {
        Projectile proj = (Projectile) p.get(k);
        try {
          if (dist(x, y, proj.x, proj.y) < 20) if (f == 'j' && proj.f != 'j') {
            p.remove(i); 
            p.remove(k); 
            j.score ++; 
            return true;
          };
        }
        catch (IndexOutOfBoundsException e) {
        }
      }
    }
    return false;
  }
}
class Vie {
float x, y;
  
  Vie(){
    x = random(100, 900);
    y = random(100, 700);
  }

  public void show(){
    fill(0, 0, 255);
    rectMode(CENTER);
    rect(x, y, 20, 20);
    
    if(dist(x, y, j.x, j.y) < 50) {v.remove(i); j.pv ++;}
  }
}
  public void settings() {  size(1000, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "miniGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
