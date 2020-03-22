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

  void update() {
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

  boolean check() {
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
