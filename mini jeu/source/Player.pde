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

  void show() {
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
  
  void tire() {
    if (amo > 0 && !reloading) {
      p.add(new Projectile(j.x, j.y, new PVector(mouseX - j.x, mouseY - j.y), 'j'));
      amo --;  
    } else {
      needAmo = true;
      timerAmo = timer;
    }
    
    
  }

}
