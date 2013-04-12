package org.gss.adi;

import javax.swing.JPanel;

public abstract interface Toolable
{
  public abstract void removeDataPanel(JPanel paramJPanel);

  public abstract void setDataPanel(JPanel paramJPanel);

  public abstract void setPoint1(int paramInt1, int paramInt2);

  public abstract void setPoint2(int paramInt1, int paramInt2);

  public abstract void removePoints();

  public abstract void growth(String[] paramArrayOfString);
}

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.Toolable
 * JD-Core Version:    0.6.2
 */